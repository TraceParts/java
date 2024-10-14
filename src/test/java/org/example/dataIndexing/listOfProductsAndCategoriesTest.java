package org.example.dataIndexing;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class listOfProductsAndCategoriesTest {
    private final String classificationCode = "your_classification_code";
    private final String partFamilyCode = "your_part_family_code";

    @Test
    void givenCorrectParameters_whenListOfProductsAndCategories_thenCorrect() {
        HttpResponse<String> response = listOfProductsAndCategories.getListOfProductsAndCategories(RootApiUrlTest.ROOT_API_URL, classificationCode, TokenTest.TOKEN, partFamilyCode);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenListOfProductsAndCategories_thenIncorrect() {
        HttpResponse<String> response = listOfProductsAndCategories.getListOfProductsAndCategories(RootApiUrlTest.ROOT_API_URL, "incorrectClassificationCode", TokenTest.TOKEN, "incorrectPartFamilyCode");
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}