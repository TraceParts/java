package org.example;

import org.example.common.GetLanguagesList;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class TokenTest {
    public static final String TOKEN = "your_token";

    @Test
    void givenCorrectToken_whenGetLanguagesList_thenCorrect() {
        HttpResponse<String> response = GetLanguagesList.getGetLanguageList(RootApiUrlTest.ROOT_API_URL, TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenInvalidToken_whenGetLanguagesList_thenIncorrect() {
        // invalid token format
        HttpResponse<String> response = GetLanguagesList.getGetLanguageList(RootApiUrlTest.ROOT_API_URL, "invalidToken");
        ApiResults.showApiReturn(response);
        assertEquals(500, response.statusCode());
    }

    private String getIncorrectToken() {
        String originalLastChar = TokenTest.TOKEN.substring(TokenTest.TOKEN.length() - 1);
        String modifiedLastChar = "";
        if (originalLastChar.matches("^\\d$")) {
            // originalLastChar is a digit
            modifiedLastChar = "a";
        } else {
            // originalLastChar is not a digit
            modifiedLastChar = "1";
        }
        return TokenTest.TOKEN.substring(0, TokenTest.TOKEN.length() - 1) + modifiedLastChar;
    }

    @Test
    void givenIncorrectToken_whenGetLanguagesList_thenIncorrect() {
        // incorrect token value
        String incorrectToken = getIncorrectToken();
        HttpResponse<String> response = GetLanguagesList.getGetLanguageList(RootApiUrlTest.ROOT_API_URL, incorrectToken);
        ApiResults.showApiReturn(response);
        assertEquals(401, response.statusCode());
    }
}
