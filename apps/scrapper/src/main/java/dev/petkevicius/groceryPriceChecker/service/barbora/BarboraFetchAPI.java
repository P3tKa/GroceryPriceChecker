package dev.petkevicius.groceryPriceChecker.service.barbora;

import static dev.petkevicius.groceryPriceChecker.service.barbora.BarboraConstants.BARBORA_URI;
import static dev.petkevicius.groceryPriceChecker.service.barbora.BarboraConstants.CATEGORY_IDS;
import static dev.petkevicius.groceryPriceChecker.service.barbora.BarboraConstants.VENDOR_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Response;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.mapper.barbora.BarboraGroceryMapper;
import dev.petkevicius.groceryPriceChecker.service.common.GroceryService;
import dev.petkevicius.groceryPriceChecker.service.iki.IkiFetchAPI;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class BarboraFetchAPI {

    private static final Logger logger = LoggerFactory.getLogger(IkiFetchAPI.class);

    private final BarboraGroceryMapper groceryMapper;
    private final GroceryService groceryService;
    private final ObjectMapper objectMapper;

    public BarboraFetchAPI(
        BarboraGroceryMapper groceryMapper,
        GroceryService groceryService,
        ObjectMapper objectMapper
    ) {
        this.groceryMapper = groceryMapper;
        this.groceryService = groceryService;
        this.objectMapper = objectMapper;
    }

    //    @Scheduled(cron = "0 0 */6 * * *") // Run every 6 hours starting from midnight
    @Scheduled(cron = "*/5 * * * * *") // Run every 45 seconds
    public List<Grocery> fetchAllCategoryProducts() {
        logger.info("Fetching all products from vendor {}", VENDOR_NAME.name());
        List<Grocery> allGroceries = new ArrayList<>();

        try (Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(true));

            CATEGORY_IDS.forEach((categoryType, categoryId) -> {
                List<Grocery> groceries = fetchCategoryProducts(browser, categoryType, categoryId);
                allGroceries.addAll(groceries);
            });

            browser.close();
        }

        logger.info("Fetched {} groceries", allGroceries.size());
        groceryService.saveOrUpdateGroceries(allGroceries);

        logger.info("Finished fetching products for vendor {}", VENDOR_NAME.name());
        return null;
    }

    private List<Grocery> fetchCategoryProducts(Browser browser, CategoryType categoryType, String categoryId) {
        List<Grocery> groceries = new ArrayList<>();
        boolean hasMoreProducts;
        int pageNumber = 1;

        do {
            try {
                List<BarboraProductResponse> responseBody = fetch(browser, categoryId, pageNumber);

                groceries.addAll(groceryMapper.mapToGrocery(responseBody, categoryType));

                pageNumber++;
                hasMoreProducts = !responseBody.isEmpty();
            } catch (RestClientException | JsonProcessingException e) {
                hasMoreProducts = false; // TODO: implement error handling, for now breaking to ensure no infinite loop
            }
        } while (hasMoreProducts);

        return groceries;
    }

    private List<BarboraProductResponse> fetch(Browser browser, String category, int pageNumber) throws JsonProcessingException {
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
            .setViewportSize(1920, 1080)
            .setJavaScriptEnabled(true)
        );

        Page page = context.newPage();

        String uri = String.format("%s%s?page=%d", BARBORA_URI, category, pageNumber);

        Response response = page.navigate(uri);

        if (response != null) {
            int statusCode = response.status();

            if (statusCode != HttpStatus.OK.value()) {
                throw new RestClientException("Failed to fetch products for vendor %s. HTTP status code: %s, response message: %s"
                    .formatted(VENDOR_NAME.name(), statusCode, page.content()));
            }

            Document document = Jsoup.parse(page.content());

            return Optional.ofNullable(document.selectFirst("div#category-page-results-placeholder"))
                .map(grid -> grid.select("li"))
                .map(liElements -> liElements.stream()
                    .map(li -> li.selectFirst("div[data-b-for-cart]"))
                    .filter(Objects::nonNull)
                    .map(div -> div.attr("data-b-for-cart"))
                    .map(json -> {
                        try {
                            return objectMapper.readValue(json, BarboraProductResponse.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
                ).orElse(List.of());
        } else {
            throw new RestClientException("Failed to get resposne for vendor %s"
                .formatted(VENDOR_NAME.name()));
        }
    }
}
