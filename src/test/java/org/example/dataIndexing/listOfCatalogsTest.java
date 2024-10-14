package org.example.dataIndexing;

import org.example.CultureInfoTest;
import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class listOfCatalogsTest {
    @Test
    void givenCorrectParameters_whenListOfCatalogs_thenCorrect() {
        HttpResponse<String> response = listOfCatalogs.getListOfCatalogs(RootApiUrlTest.ROOT_API_URL, CultureInfoTest.CULTURE_INFO, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }
}