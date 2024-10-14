package org.example.account;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class checkTheExistenceOfAUserAccountTest {
    private final String userEmail = "your_user_email";

    @Test
    void givenCorrectParameters_whenCheckTheExistenceOfAUserAccount_thenCorrect() {
        HttpResponse<String> response = checkTheExistenceOfAUserAccount.getCheckTheExistenceOfAUserAccount(RootApiUrlTest.ROOT_API_URL, userEmail, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenInvalidUserEmail_whenCheckTheExistenceOfAUserAccount_thenIncorrect() {
        // invalid userEmail format
        HttpResponse<String> response = checkTheExistenceOfAUserAccount.getCheckTheExistenceOfAUserAccount(RootApiUrlTest.ROOT_API_URL, "invalidUserEmail", TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }

    @Test
    void givenIncorrectUserEmail_whenCheckTheExistenceOfAUserAccount_thenIncorrect() {
        // incorrect userEmail value
        HttpResponse<String> response = checkTheExistenceOfAUserAccount.getCheckTheExistenceOfAUserAccount(RootApiUrlTest.ROOT_API_URL, "incorrectUserEmail@incorrect.com", TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}