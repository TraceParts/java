package org.example.dataIndexing;

import org.example.CultureInfoTest;
import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class listOfCategoriesTest {
    private final String classificationCode = "your_classification_code";

    @Test
    void givenCorrectParameters_whenListOfCategories_thenCorrect() {
        HttpResponse<String> response = listOfCategories.getListOfCategories(RootApiUrlTest.ROOT_API_URL, classificationCode, CultureInfoTest.CULTURE_INFO, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenListOfCategories_thenIncorrect() {
        HttpResponse<String> response = listOfCategories.getListOfCategories(RootApiUrlTest.ROOT_API_URL, "incorrectClassificationCode", CultureInfoTest.CULTURE_INFO, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}