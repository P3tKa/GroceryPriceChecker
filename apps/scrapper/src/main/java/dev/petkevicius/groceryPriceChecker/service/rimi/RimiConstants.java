package dev.petkevicius.groceryPriceChecker.service.rimi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.petkevicius.groceryPriceChecker.domain.groceries.VendorName;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RimiConstants {
    public static final VendorName VENDOR_NAME = VendorName.RIMI;
    public static final String COUNTRY_OF_ORIGIN = "LT";
    public static final int PAGE_SIZE = 80;
    public static final String RIMI_URI = "https://www.rimi.lt/e-parduotuve/lt/produktai/c/";
    public static final HttpHeaders HTTP_HEADERS;

    static {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add("accept", "application/json");
        headers.add("accept-language", "en-US,en;q=0.9");
        headers.add("cache-control", "no-cache");
        headers.add("pragma", "no-cache");
        headers.add("priority", "u=1, i");
        headers.add("referer", "https://www.rimi.lt/e-parduotuve/lt/produktai/alkoholiniai-ir-nealkoholiniai-gerimai/c/SH-1");
        headers.add("sec-fetch-dest", "empty");
        headers.add("sec-fetch-mode", "cors");
        headers.add("sec-fetch-site", "same-origin");
        headers.add("Cookie", "CookieConsent={stamp:%27PgklmrZeCh9mSYFsgn6u59MHsl+jQelpDIvknzXHhKCWOiYd5bscvg==%27%2Cnecessary:true%2Cpreferences:true%2Cstatistics:true%2Cmarketing:true%2Cmethod:%27explicit%27%2Cver:8%2Cutc:1742040985191%2Cregion:%27lt%27}; _gcl_au=1.1.1816604924.1742040985; _ga=GA1.1.898883268.1742040985; FPID=FPID2.2.tUjbqKc8pPSqP1ozKpYKQZIkFAckN%2F7E9mI%2BzuE008s%3D.1742040985; XSRF-TOKEN=eyJpdiI6IlAvNWVDaWp1cC9zZXgvYWNWaGoyTXc9PSIsInZhbHVlIjoiN3drMkNGSitSL3Vwd05NU2c3WjZjY2YwSUZLN2lYMDZvQk1tN2h0ODVtZXlzMkZDNjV1dDBtOWFDREtmWERoV3pacnUwck1VdHIvMExoVVJjTm4vNlRaWlRTZEM3c2JuZ0llcC85bUphcVAxUEVUTDdrL1lscXhscEcyc2JUdFEiLCJtYWMiOiJiMmFiYjgwNzg2ZTMxZTY5ODljYWJiZGYyYTY3ZmUzMTgwMjc2YTYwMDNlNjAwNTMwNTk0NzdjOTZkZWFmNzM3IiwidGFnIjoiIn0%3D; laravel_session=eyJpdiI6ImtBUFRKeW5PMXJXWEtxQWRibXRtRXc9PSIsInZhbHVlIjoiUnZkMDJOcXZYRkQrR1hYYWdaOU9KZ0ltQUtrM05WSWVPV2xQMmNaVG5uZTV6UE5IYklJbFlMaVpQZTB6NkVYTGE4VWNxRjkvc0dlTlZOeUlVMWRMVGhpVWZsMGhlR2dha1l3ZGRsby9nV2h2Wk9sQmpndnJhaUJGOEVtUFhNR0kiLCJtYWMiOiI3YWYxY2RjYTdmYmUyZWNkNzE2MDUyZWY1ZDVmMTk1MWZmOTI5MDFiMzlhNWY4YmRkNmMwZjY2ODQ0YjY0MTNjIiwidGFnIjoiIn0%3D; FPLC=cX5G3lTkBD8OOakZaDw4J5%2FcitLP5EqRaLO6FQEdyRTUvM5VsOPwCppxE0gTo0AkVkIxLOjWPKH7eKCMIG3ahotR5DMbVfVX8tw3XdFzzQkGeEvGjzi%2FzVRSZ6RXDw%3D%3D; TS01ad85f5=014a560c74e6547abadf3f247714a26fc0077b2692ff780d01747f91ccfdf9f4066968dd19419482d660250b653268180f712665b698e08bea2c2a83412a0edb42042c10984b4f6b12528f8ad9e802b7cd0b21d1ce; hide_side_cart=1; XSRF-TOKEN=eyJpdiI6IlNwdDNrZktTUVRESm5nMXIwS3U0clE9PSIsInZhbHVlIjoiNjROcVcyUkEvZy9sTUhLMXBMaEM4VFZNSm4vekM5aDQyT2xEOUI4SlhIQjFZOFVkZmVFYllIZmY5WjY1Y1BMY2pkenQvN3cxTTU5dDhBUExPSkZxS1c2Slg5SlVvS3JxK2xpT3lUOTU2RmhUZmlOVVJ3Qko5ZlQ5SEdXaXVmYVIiLCJtYWMiOiJjNjJkNzM0NWQ2ZDc2ZDFlZjY2YmRlYWUyYjljMTE2M2YwMjA1NjY5OGM0MjQ5ZTk1ZDNmNGYwNjY2YzU5YzQwIiwidGFnIjoiIn0%3D; rimi_storefront_session=eyJpdiI6Ikp1ZlVOaU9QYlJvVVM5MW1TdnJRK2c9PSIsInZhbHVlIjoiNksyUDZ6Wkp5VW9IQmdnWUNpMExWK2JxZExWYm5iWHBLZkUvdDJ4RGdDTTRoWTBxcTFEVlVSNGkvS1VKWHBBa2w2VjZsM1hITUpYSWRoMWNvUTlRWmZqSmdOQUg4ZEc2Zi83VmJDY3N0YUZXZ3M5RzBwOXVsMlZIR0lBUFRLZy8iLCJtYWMiOiI5MDc2NDhkNzczZmViYzYxOTc4ZjkyNTIzMmM3MzdiNTM0ZjkzM2E2OGUxZGRlOWJhNTQzYWM0ODYzMWIyYWE0IiwidGFnIjoiIn0%3D; TS018480a0=014a560c745d7673f77c06c6dfbfed13c4f1b1d01571c79a4a88a2eebc3dbbc01d54f7a87b9adc70a5c738272b0744f42f6f7e6ee8459da536cca1726f02bf987d82a3c349811faee117e17484bba42006a2c23913; _ga_PJH0J79Y3N=GS1.1.1742135925.6.1.1742136384.0.0.2069912220");
        headers.add("user-agent", "Mozilla/5.0 (iPad; CPU OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1");
        headers.add("x-requested-with", "XMLHttpRequest");

        HTTP_HEADERS = new HttpHeaders(headers);
    }

    public static final Map<CategoryType, List<String>> CATEGORY_IDS = new HashMap<>() {
        {
            put(Category.SubCategory.FRUITS_AND_BERRIES, List.of("SH-15-3"));
            put(Category.SubCategory.VEGETABLES_AND_MUSHROOMS, List.of("SH-15-1"));
            put(Category.SubSubCategory.LONG_LIFE_MILK, List.of("SH-11-8-15"));
            put(Category.SubSubCategory.PASTEURIZED_MILK, List.of("SH-11-8-17"));
            put(Category.SubSubCategory.MILK_DRINKS, List.of("SH-11-8-18"));
            put(Category.SubSubCategory.CONDENSED_MILK, List.of("SH-11-8-16"));
            put(Category.SubSubCategory.CHICKEN_EGGS, List.of(
                "SH-11-6-14", // Eco
                "SH-11-6-16", // Free-range
                "SH-11-6-13" // Kept on the litter
            ));
            put(Category.SubSubCategory.QUAIL_EGGS, List.of("SH-11-6-12") /*Other eggs*/ );
        }
    };
}