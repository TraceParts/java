package org.example.dataIndexing;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class catalogContactDetailsTest {
    private final String classificationCode = "your_classification_code";

    @Test
    void givenCorrectParameters_whenCatalogContactDetails_thenCorrect() {
        HttpResponse<String> response = catalogContactDetails.getCatalogContactDetails(RootApiUrlTest.ROOT_API_URL, classificationCode, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenCatalogContactDetails_thenIncorrect() {
        HttpResponse<String> response = catalogContactDetails.getCatalogContactDetails(RootApiUrlTest.ROOT_API_URL, "incorrectClassificationCode", TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}