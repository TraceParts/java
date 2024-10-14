package org.example.cadFileDelivery;

import org.example.CultureInfoTest;
import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

//📘 Warning! Any tries will be recorded in the Production data.
class requestACadFileTest {
    //Required
    private final String userEmail = "your_user_email";
    private final Integer cadFormatId = -1;
    //Required pairs (one required)
    //Pair 1
    private final String partFamilyCode = "your_part_family_code";
    private final String selectionPath = "your_selection_path"; // without selectionPath, the default configuration is used
    //Pair 2
    private final String classificationCode = "your_classification_code";
    private final String partNumber = "your_part_number";
    //Optional
    private final String cadDetailLevel = "your_cad_detail_level";
    //Deprecated
    private final String languageId = ""; //your_language_id

    @Test
    void givenCorrectParametersAndOptions_whenRequestACadFile_thenCorrect() {
        HashMap<String, String> options = new HashMap<>();
        options.put("partFamilyCode", partFamilyCode);
        options.put("selectionPath", selectionPath);
        options.put("classificationCode", classificationCode);
        options.put("partNumber", partNumber);
        options.put("cadDetailLevel", cadDetailLevel);
        options.put("languageId", languageId);
        //TODO: Be careful ! Any tries will be recorded in the Production data.
        HttpResponse<String> response = requestACadFile.postRequestACadFile(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, userEmail, CultureInfoTest.CULTURE_INFO, cadFormatId, options);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParametersAndOptions_whenRequestACadFile_thenIncorrect() {
        HashMap<String, String> options = new HashMap<>();
        options.put("partFamilyCode", "incorrectPartFamilyCode");
        options.put("selectionPath", "incorrectSelectionPath");
        options.put("classificationCode", "incorrectClassificationCode");
        options.put("partNumber", "incorrectPartNumber");
        options.put("cadDetailLevel", "incorrectCadDetailLevel");
        options.put("languageId", "incorrectLanguageId");
        HttpResponse<String> response = requestACadFile.postRequestACadFile(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, userEmail, CultureInfoTest.CULTURE_INFO, cadFormatId, options);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }

    @Test
    void givenWithoutParameterOrOption_whenRequestACadFile_thenIncorrect() {
        HashMap<String, String> options = new HashMap<>(); // no parameter or options
        HttpResponse<String> response = requestACadFile.postRequestACadFile(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, userEmail, CultureInfoTest.CULTURE_INFO, cadFormatId, options);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }
}