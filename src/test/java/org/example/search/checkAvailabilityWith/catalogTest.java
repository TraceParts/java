package org.example.search.checkAvailabilityWith;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class catalogTest {
    private String catalogLabel = "your_catalog_label";

    @Test
    void givenCorrectParameters_whenCatalog_thenCorrect() {
        HttpResponse<String> response = catalog.getCatalog(RootApiUrlTest.ROOT_API_URL, catalogLabel, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenCatalog_thenIncorrect() {
        HttpResponse<String> response = catalog.getCatalog(RootApiUrlTest.ROOT_API_URL, "incorrectCatalogLabel", TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}