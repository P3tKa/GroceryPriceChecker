package dev.petkevicius.groceryPriceChecker.service.iki;

import static dev.petkevicius.groceryPriceChecker.service.iki.IkiConstants.CATEGORY_IDS;
import static dev.petkevicius.groceryPriceChecker.service.iki.IkiConstants.IKI_URI;
import static dev.petkevicius.groceryPriceChecker.service.iki.IkiConstants.VENDOR_NAME;

import java.util.ArrayList;
import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.mapper.iki.IkiGroceryMapper;
import dev.petkevicius.groceryPriceChecker.service.common.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class IkiFetchAPI {

    private static final Logger logger = LoggerFactory.getLogger(IkiFetchAPI.class);

    private final IkiGroceryMapper groceryMapper;
    private final GroceryService groceryService;

    public IkiFetchAPI(
        IkiGroceryMapper groceryMapper,
        GroceryService groceryService
    ) {
        this.groceryMapper = groceryMapper;
        this.groceryService = groceryService;
    }

    //    @Scheduled(cron = "0 0 */6 * * *") // Run every 6 hours starting from midnight
//    @Scheduled(cron = "*/5 * * * * *") // Run every 45 seconds
    public List<Grocery> fetchAllCategoryProducts() {
        logger.info("Fetching all products from vendor {}", VENDOR_NAME.name());

        List<Grocery> allGroceries = new ArrayList<>();

        CATEGORY_IDS.forEach((categoryType, categoryIds) -> categoryIds.forEach(categoryId -> {
            List<Grocery> groceries = fetchCategoryProducts(categoryType, categoryId);
            allGroceries.addAll(groceries);
        }));

        logger.info("Fetched {} groceries", allGroceries.size());
        groceryService.saveOrUpdateGroceries(allGroceries);

        logger.info("Finished fetching products for vendor {}", VENDOR_NAME.name());
        return allGroceries;
    }

    private List<Grocery> fetchCategoryProducts(CategoryType categoryType, String categoryId) {
        List<Grocery> groceries = new ArrayList<>();
        IkiRequestBody requestBody = new IkiRequestBody();
        requestBody.setCategoryId(categoryId);
        requestBody.setFromIndex(0);

        boolean hasMoreProducts;

        do {
            try {
                final IkiResponseBody responseBody = fetch(requestBody);
                groceries.addAll(groceryMapper.mapToGrocery(responseBody, categoryType));
                requestBody.setFromIndex(requestBody.getFromIndex() + 1);
                hasMoreProducts = responseBody.getCount() == requestBody.getLimit();
            } catch (RestClientException e) {
                hasMoreProducts = false; // TODO: implement error handling, for now breaking to ensure no infinite loop
            }
        } while (hasMoreProducts);

        return groceries;
    }

    private IkiResponseBody fetch(IkiRequestBody requestBody) {
        RestClient restClient = RestClient.builder()
            .build();

        return restClient.post()
            .uri(IKI_URI)
            .accept(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (request, response) -> {
                throw new RestClientException("Failed to fetch products for vendor %s. HTTP status code: %s, response message: %s"
                    .formatted(VENDOR_NAME.name(), response.getStatusCode(), response.getStatusText()));
            })
            .body(IkiResponseBody.class);
    }
}
