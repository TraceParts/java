package org.example.search.checkAvailabilityWith;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class partNumberTest {
    private Boolean removeChar = false;
    private String partNumberValue = "your_part_number";
    private String catalogLabel = "your_catalog_label";

    @Test
    void givenCorrectParameters_whenPartNumber_thenCorrect() {
        HttpResponse<String> response = partNumber.getPartNumber(removeChar, RootApiUrlTest.ROOT_API_URL, partNumberValue, catalogLabel, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenPartNumber_thenIncorrect() {
        HttpResponse<String> response = partNumber.getPartNumber(removeChar, RootApiUrlTest.ROOT_API_URL, "incorrectYourPartNumber", "incorrectCatalogLabel", TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}