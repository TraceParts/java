package org.example.dataIndexing;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class listOfPartNumbersTest {
    private final String partFamilyCode = "your_part_family_code";
    //Optional
    private final Boolean returnYourOwnCodes = false;

    @Test
    void givenCorrectParameters_whenListOfPartNumbers_thenCorrect() {
        HttpResponse<String> response = listOfPartNumbers.getListOfPartNumbers(RootApiUrlTest.ROOT_API_URL, partFamilyCode, TokenTest.TOKEN, returnYourOwnCodes);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenInvalidParameters_whenListOfPartNumbers_thenIncorrect() {
        // partFamilyCode value is invalid because it is not a dash separated numbers format
        HttpResponse<String> response = listOfPartNumbers.getListOfPartNumbers(RootApiUrlTest.ROOT_API_URL, "invalidPartFamilyCode", TokenTest.TOKEN, returnYourOwnCodes);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenListOfPartNumbers_thenIncorrect() {
        HttpResponse<String> response = listOfPartNumbers.getListOfPartNumbers(RootApiUrlTest.ROOT_API_URL, "00-00000000-000000", TokenTest.TOKEN, returnYourOwnCodes);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}