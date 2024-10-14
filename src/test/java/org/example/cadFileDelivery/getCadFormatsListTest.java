package org.example.cadFileDelivery;

import org.example.CultureInfoTest;
import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class getCadFormatsListTest {
    private String partFamilyCode = "your_part_family_code";
    private String selectionPath = "your_selection_path";

    private String classificationCode = "your_classification_code";
    private String partNumber = "your_part_number";

    @Test
    void givenCorrectParametersAndFirstOptionsPair_whenGetCadFormatsList_thenCorrect() {
        HashMap<String, String> possibleOptions = new HashMap<>(); // provide first required options pair
        possibleOptions.put("partFamilyCode", partFamilyCode);
        possibleOptions.put("selectionPath", selectionPath);
        HttpResponse<String> response = getCadFormatsList.getGetCadFormatsList(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, CultureInfoTest.CULTURE_INFO, possibleOptions);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenCorrectParametersAndIncorrectFirstOptionsPair_whenGetCadFormatsList_thenCorrect() {
        HashMap<String, String> possibleOptions = new HashMap<>(); // provide first required options pair
        possibleOptions.put("partFamilyCode", "incorrectPartFamilyCode");
        possibleOptions.put("selectionPath", "incorrectSelectionPath");
        HttpResponse<String> response = getCadFormatsList.getGetCadFormatsList(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, CultureInfoTest.CULTURE_INFO, possibleOptions);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }

    @Test
    void givenCorrectParametersAndSecondOptionsPair_whenGetCadFormatsList_thenCorrect() {
        HashMap<String, String> possibleOptions = new HashMap<>(); // provide second required options pair
        possibleOptions.put("classificationCode", classificationCode);
        possibleOptions.put("partNumber", partNumber);
        HttpResponse<String> response = getCadFormatsList.getGetCadFormatsList(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, CultureInfoTest.CULTURE_INFO, possibleOptions);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenCorrectParametersAndIncorrectSecondOptionsPair_whenGetCadFormatsList_thenCorrect() {
        HashMap<String, String> possibleOptions = new HashMap<>(); // provide second required options pair
        possibleOptions.put("classificationCode", "incorrectClassificationCode");
        possibleOptions.put("partNumber", "incorrectPartNumber");
        HttpResponse<String> response = getCadFormatsList.getGetCadFormatsList(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, CultureInfoTest.CULTURE_INFO, possibleOptions);
        ApiResults.showApiReturn(response);
        assertEquals(404, response.statusCode());
    }

    @Test
    void givenCorrectParametersButNoOptions_whenGetCadFormatsList_thenIncorrect() {
        HashMap<String, String> possibleOptions = new HashMap<>(); // provide none of the required options pairs
        HttpResponse<String> response = getCadFormatsList.getGetCadFormatsList(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, CultureInfoTest.CULTURE_INFO, possibleOptions);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }
}
