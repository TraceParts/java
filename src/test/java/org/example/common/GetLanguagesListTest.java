package org.example.common;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetLanguagesListTest {
    @Test
    void givenCorrectParameters_whenGetLanguagesList_thenCorrect() {
        HttpResponse<String> response = GetLanguagesList.getGetLanguageList(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }
}