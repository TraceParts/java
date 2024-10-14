package org.example.dataIndexing;

import org.example.CultureInfoTest;
import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class listOfProductsTest {
    private final String classificationCode = "your_classification_code";
    //Optional
    private final String categoryCode = "your_category_code";

    @Test
    void givenCorrectParameters_whenListOfProducts_thenCorrect() {
        HttpResponse<String> response = listOfProducts.getListOfProducts(RootApiUrlTest.ROOT_API_URL, classificationCode, CultureInfoTest.CULTURE_INFO, TokenTest.TOKEN, categoryCode);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
        assertNotEquals("null", response.body());
    }

    @Test
    void givenIncorrectParameters_whenListOfProducts_thenIncorrect() {
        HttpResponse<String> response = listOfProducts.getListOfProducts(RootApiUrlTest.ROOT_API_URL, "incorrectClassificationCode", CultureInfoTest.CULTURE_INFO, TokenTest.TOKEN, "incorrectCategoryCode");
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
        assertEquals("null", response.body());
    }
}