package org.example;

import org.example.dataIndexing.listOfCatalogs;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CultureInfoTest {
    // example : "en" ('en' means 'English' and is the default value)
    public static final String CULTURE_INFO = "your_culture_info";

    @Test
    void givenCorrectCultureInfo_whenListOfCatalogs_thenCorrect() {
        assertTrue(CULTURE_INFO.matches("^[a-zA-Z]{2,4}(?:-[a-zA-Z]{2,4})*$"), "Culture info should match ISO 639 codes");
        HttpResponse<String> response = listOfCatalogs.getListOfCatalogs(RootApiUrlTest.ROOT_API_URL, CULTURE_INFO, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }
}
