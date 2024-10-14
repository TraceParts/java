package org.example;

import org.example.common.GetLanguagesList;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class RootApiUrlTest {
    // example : "https://beta-cloud.traceparts.com/api/apollo-api-gateway/"
    public static final String ROOT_API_URL = "your_root_api_url";

    @Test
    void givenCorrectRootApiUrl_whenGetLanguagesList_thenCorrect() {
        HttpResponse<String> response = GetLanguagesList.getGetLanguageList(ROOT_API_URL, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenInvalidRootApiUrl_whenGetLanguagesList_thenIncorrect() {
        // invalid rootApiUrl format because it misses a '/' at the end
        assertThrows(RuntimeException.class, () -> GetLanguagesList.getGetLanguageList("https://www.traceparts.com", TokenTest.TOKEN));
    }

    @Test
    void givenIncorrectRootApiUrl_whenGetLanguagesList_thenIncorrect() {
        // incorrect rootApiUrl value because it is not an endpoint
        HttpResponse<String> response = GetLanguagesList.getGetLanguageList("https://www.traceparts.com/", TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}
