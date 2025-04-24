package dev.petkevicius.groceryPriceChecker.service.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class GroceryNameService {

    private static final String UNIT_SUFFIX_REGEX = ",\\s*\\d+\\s*(vnt\\.|kg|g|ml)";
    private static final String STERIL_REGEX = "\\bSteril.\\b";
    private static final Map<String, String> shorthandMap = new HashMap<>(
        Map.of(
            "braš.", "braškinis.",
            "šok.", "šokoladinis",
            "ban.", "bananinis",
            "ml", "ml"
        )
    );



    public String stripSuffixes(String productName) {
        String result = productName.replaceAll(UNIT_SUFFIX_REGEX, "")
            .replaceAll(STERIL_REGEX, "")
            .trim();
        return replaceShorthand(result);
    }

    private String replaceShorthand(String productName) {
        for (Map.Entry<String, String> entry : shorthandMap.entrySet()) {
            productName = productName.replace(entry.getKey(), entry.getValue());
        }
        return productName;
    }
}
