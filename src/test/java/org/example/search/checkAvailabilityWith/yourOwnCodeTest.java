package org.example.search.checkAvailabilityWith;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class yourOwnCodeTest {
    private String yourOwnCodeValue = "your_own_code";
    private String catalogLabel = "your_catalog_label";

    @Test
    void givenCorrectParameters_whenYourOwnCode_thenCorrect() {
        HttpResponse<String> response = yourOwnCode.getYourOwnCode(RootApiUrlTest.ROOT_API_URL, yourOwnCodeValue, catalogLabel, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenYourOwnCode_thenIncorrect() {
        HttpResponse<String> response = yourOwnCode.getYourOwnCode(RootApiUrlTest.ROOT_API_URL, "incorrectYourOwnCodeValue", "incorrectCatalogLabel", TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }

}