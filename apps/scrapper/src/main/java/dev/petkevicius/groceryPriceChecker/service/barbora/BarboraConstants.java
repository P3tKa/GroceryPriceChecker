package dev.petkevicius.groceryPriceChecker.service.barbora;

import java.util.HashMap;
import java.util.Map;

import dev.petkevicius.groceryPriceChecker.domain.groceries.VendorName;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class BarboraConstants {
    public static final VendorName VENDOR_NAME = VendorName.BARBORA;
    public static final String COUNTRY_OF_ORIGIN = "LT";
    public static final String BARBORA_URI = "https://barbora.lt/";
    public static final HttpHeaders HTTP_HEADERS;

    static {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        headers.add("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.add("accept-language", "en-US,en;q=0.9");
        headers.add("cache-control", "no-cache");
        headers.add("pragma", "no-cache");
        headers.add("priority", "u=0, i");
        headers.add("referer", "https://www.rimi.lt/e-parduotuve/lt/produktai/alkoholiniai-ir-nealkoholiniai-gerimai/c/SH-1");
        headers.add("sec-fetch-dest", "document");
        headers.add("sec-fetch-mode", "navigate");
        headers.add("sec-fetch-site", "none");
        headers.add("Cookie", "_fbp=fb.1.1742046975395.211283061806556083; CookieConsent={stamp:%27uz/Ed2itCy3ZrTZeZrnIVvcjbi1wzzX2IpY9A4+r0gcZWHAKKUGCfQ==%27%2Cnecessary:true%2Cpreferences:true%2Cstatistics:true%2Cmarketing:true%2Cmethod:%27explicit%27%2Cver:1%2Cutc:1742046977050%2Cregion:%27lt%27}; _gcl_au=1.1.1574628685.1742046977; _hjSessionUser_1873641=eyJpZCI6ImFhZTM0NjZlLWQ3MmEtNTU0ZC05ODgzLWFiODUwZWE1YjUwNyIsImNyZWF0ZWQiOjE3NDIwNDY5Nzc0MzMsImV4aXN0aW5nIjp0cnVlfQ==; _gid=GA1.2.313258603.1742295140; soundestID=20250318105220-gC2CXIJ8gyAnVkzNmB3IcReV1EMMhxTBKrXL0QzISQ5D9S5BP; omnisendSessionID=55LhiBAtSccuBU-20250318105220; _hjSession_1873641=eyJpZCI6ImNlMDA2YjkyLTViYjEtNGU3My04ODdjLWYyYjAxMWMxZWNkMCIsImMiOjE3NDIyOTUxNDAyMDcsInMiOjAsInIiOjAsInNiIjowLCJzciI6MCwic2UiOjAsImZzIjowLCJzcCI6MH0=; cf_clearance=FU6tMX8.iqgyduBat8wEzQ_wI_0daaMYm3HuLBZuVeQ-1742295365-1.2.1.1-tOidO.iRGNxZ9tFqtBD8TziCJEZ9vjSUeFTWi1VCGbq48YmvHZ_cO.w5r5s0i6lDyQ0AMY4tTftRZjj_1qRRgx_c_tUHRrECiYrPSDiga1qNb2U9d9BSGnD1QmuA4T5R5bkIqn7EcGp6NmfwmgcH4PO7Y9nshYWLZTLtoS6SNCieRsg0WXQRr.CDi2mihsTROF2n4xgK8ipWcB8xvsYLMMcLX2GriQem8PX7l1BhkPJHr3fZT9i.czUImobep6lg9B5yNgu8QxuOopkrUSlpyssfJxRHZlxeoTUfxTTRh2xQY1AqH9h4h6Z7cVjCIqB58ffbWpMc_uZQj8EjKcJ2xzNQPlIvzEECD6ENTp50DOY; AWSALB=zevFaBydTQsI3VkdeEixlZ1/SVszb4DXqCfgO4HN3rwuai3HiWdpjFvCHpSAj750ovPcRgwjzLMwAPHy897LrCAnq8+talSJZTmeTUKc3dSVYlbEgfi/Y2fceI+0; AWSALBCORS=zevFaBydTQsI3VkdeEixlZ1/SVszb4DXqCfgO4HN3rwuai3HiWdpjFvCHpSAj750ovPcRgwjzLMwAPHy897LrCAnq8+talSJZTmeTUKc3dSVYlbEgfi/Y2fceI+0; _ga=GA1.2.2073947872.1742046976; _ga_WJJCPC2H4H=GS1.1.1742295137.5.1.1742295954.0.0.2033507901");
        headers.add("user-agent", "Mozilla/5.0 (iPad; CPU OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1");
        headers.add("x-requested-with", "XMLHttpRequest");

        HTTP_HEADERS = new HttpHeaders(headers);
    }

    public static final Map<CategoryType, String> CATEGORY_IDS = new HashMap<>() {
        {
            put(Category.SubCategory.FRUITS_AND_BERRIES, "darzoves-ir-vaisiai/vaisiai-ir-uogos");
            put(Category.SubCategory.VEGETABLES_AND_MUSHROOMS, "darzoves-ir-vaisiai/darzoves-ir-grybai");
            put(Category.SubSubCategory.LONG_LIFE_MILK, "pieno-gaminiai-ir-kiausiniai/pienas/ilgo-galiojimo-pienas");
            put(Category.SubSubCategory.PASTEURIZED_MILK, "pieno-gaminiai-ir-kiausiniai/pienas/pasterizuotas-pienas");
            put(Category.SubSubCategory.MILK_DRINKS, "pieno-gaminiai-ir-kiausiniai/pienas/pieno-gerimai");
            put(Category.SubSubCategory.CONDENSED_MILK, "pieno-gaminiai-ir-kiausiniai/pienas/sutirstintas-pienas");
            put(Category.SubSubCategory.CHICKEN_EGGS, "pieno-gaminiai-ir-kiausiniai/kiausiniai/vistu-kiausiniai");
            put(Category.SubSubCategory.QUAIL_EGGS, "pieno-gaminiai-ir-kiausiniai/kiausiniai/putpeliu-kiausiniai");
        }
    };
}
