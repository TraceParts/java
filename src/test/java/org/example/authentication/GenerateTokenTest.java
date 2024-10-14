package org.example.authentication;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.RootApiUrlTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class GenerateTokenTest {
    private final String tenantUid = "your_tenant_uid";
    private final String apiKey = "your_api_key";

    @Test
    void givenCorrectParameters_whenGenerateToken_thenCorrect() {
        HttpResponse<String> response = GenerateToken.postGenerateToken(RootApiUrlTest.ROOT_API_URL, tenantUid, apiKey);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode()); // http code 200 means success
        JsonElement je = JsonParser.parseString(response.body());
        assertTrue(je.isJsonObject());
        JsonObject obj = je.getAsJsonObject();
        assertAll("Testing results formats",
                () -> assertTrue(obj.has("token"), "Object should have a 'token' field"),
                () -> assertTrue(obj.has("expiryDate"), "Object should have a 'expiryDate' field"));
    }

    @Test
    void givenInvalidRootApiUrl_whenGenerateToken_thenIncorrect() {
        // rootApiUrl is invalid because it misses a '/' at the end for example
        assertThrows(RuntimeException.class, () -> GenerateToken.postGenerateToken("https://www.traceparts.com", tenantUid, apiKey));
    }

    @Test
    void givenIncorrectRootApiUrl_whenGenerateToken_thenIncorrect() {
        // rootApiUrl is incorrect because it is not an endpoint
        HttpResponse<String> response = GenerateToken.postGenerateToken("https://www.traceparts.com/", tenantUid, apiKey);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }

    @Test
    void givenIncorrectTenantUid_whenGenerateToken_thenIncorrect() {
        // tenant UID refers to no one
        HttpResponse<String> response = GenerateToken.postGenerateToken(RootApiUrlTest.ROOT_API_URL, "incorrectTenantUid", apiKey);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }

    @Test
    void givenIncorrectApiKey_whenGenerateToken_thenIncorrect() {
        // api key refers to no one
        HttpResponse<String> response = GenerateToken.postGenerateToken(RootApiUrlTest.ROOT_API_URL, tenantUid, "incorrectApiKey");
        ApiResults.showApiReturn(response);
        assertEquals(401, response.statusCode());
    }
}