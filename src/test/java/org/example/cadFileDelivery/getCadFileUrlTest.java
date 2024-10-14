package org.example.cadFileDelivery;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class getCadFileUrlTest {
    private final Integer cadRequestId = -1; // your_cad_request_id

    @Test
    void givenCorrectParameters_whenGetCadFileUrl_thenCorrect() {
        HttpResponse<String> response = getCadFileUrl.getGetCadFileUrl(RootApiUrlTest.ROOT_API_URL, cadRequestId, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertTrue((response.statusCode() == 200 || response.statusCode() == 204), "Status code should be 200 or 204 (Actual status code: " + response.statusCode() + ")");
    }

    @Test
    void givenIncorrectCadRequestId_whenGetCadFileUrl_thenIncorrect() {
        HttpResponse<String> response = getCadFileUrl.getGetCadFileUrl(RootApiUrlTest.ROOT_API_URL, -1, TokenTest.TOKEN);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }
}