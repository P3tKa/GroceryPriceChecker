package dev.petkevicius.groceryPriceChecker.service.rimi;

import static dev.petkevicius.groceryPriceChecker.service.rimi.RimiConstants.CATEGORY_IDS;
import static dev.petkevicius.groceryPriceChecker.service.rimi.RimiConstants.HTTP_HEADERS;
import static dev.petkevicius.groceryPriceChecker.service.rimi.RimiConstants.PAGE_SIZE;
import static dev.petkevicius.groceryPriceChecker.service.rimi.RimiConstants.VENDOR_NAME;
import static dev.petkevicius.groceryPriceChecker.service.rimi.RimiConstants.RIMI_URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.mapper.rimi.RimiGroceryMapper;
import dev.petkevicius.groceryPriceChecker.service.common.GroceryService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class RimiFetchAPI {

    private static final Logger logger = LoggerFactory.getLogger(RimiFetchAPI.class);

    private final RimiGroceryMapper groceryMapper;
    private final GroceryService groceryService;
    private final ObjectMapper objectMapper;

    public RimiFetchAPI(
        RimiGroceryMapper groceryMapper,
        GroceryService groceryService,
        ObjectMapper objectMapper
    ) {
        this.groceryMapper = groceryMapper;
        this.groceryService = groceryService;
        this.objectMapper = objectMapper;
    }

    //    @Scheduled(cron = "0 0 */6 * * *") // Run every 6 hours starting from midnight
//    @Scheduled(cron = "*/5 * * * * *") // Run every 45 seconds
    public List<Grocery> fetchAllCategoryProducts() {
        logger.info("Fetching all products from vendor {}", VENDOR_NAME.name());

        List<Grocery> allGroceries = new ArrayList<>();

        CATEGORY_IDS.forEach((categoryType, categoryId) -> {
            List<Grocery> groceries = fetchCategoryProducts(categoryType, categoryId);
            allGroceries.addAll(groceries);
        });

        logger.info("Fetched {} groceries", allGroceries.size());
        groceryService.saveOrUpdateGroceries(allGroceries);

        logger.info("Finished fetching products for vendor {}", VENDOR_NAME.name());
        return null;
    }

    private List<Grocery> fetchCategoryProducts(CategoryType categoryType, String categoryId) {
        List<Grocery> groceries = new ArrayList<>();
        boolean hasMoreProducts;
        int page = 1;

        do {
            try {
                Elements responseBody = fetch(categoryId, page).orElseThrow();

                groceries.addAll(groceryMapper.mapToGrocery(responseBody, categoryType));

                page++;
                hasMoreProducts = responseBody.size() == PAGE_SIZE;
            } catch (RestClientException | JsonProcessingException e) {
                hasMoreProducts = false; // TODO: implement error handling, for now breaking to ensure no infinite loop
            }
        } while (hasMoreProducts);

        return groceries;
    }

    private Optional<Elements> fetch(String category, int page) throws JsonProcessingException {
        RestClient restClient = RestClient.builder()
            .build();

        String uri = String.format("%s%s?currentPage=%d&pageSize=%d", RIMI_URI, category, page, PAGE_SIZE);

        String html = restClient.get()
            .uri(uri)
            .headers(httpHeaders -> httpHeaders.addAll(HTTP_HEADERS))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (request, response) -> {
                throw new RestClientException("Failed to fetch products for vendor %s. HTTP status code: %s, response message: %s"
                    .formatted(VENDOR_NAME.name(), response.getStatusCode(), response.getStatusText()));
            })
            .body(String.class);

        Document document = Jsoup.parse(objectMapper.readTree(html).get("products").asText());

        return Optional.ofNullable(document.selectFirst("ul.product-grid"))
            .map(grid -> grid.select("li"));
    }
}
