package org.example.product;

import org.example.CultureInfoTest;
import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class productDataTest {
    private String partFamilyCode = "your_part_family_code";
    //Optional
    private String selectionPath = "your_selection_path";
    private String cadDetailLevel = "-1"; // your_cad_detail_level
    private Integer currentStepNumber = 0; // your_current_step_number

    @Test
    void givenCorrectParametersAndOptions_whenProductData_thenCorrect() {
        HashMap<String, String> options = new HashMap<>();
        options.put("selectionPath", selectionPath);
        options.put("cadDetailLevel", cadDetailLevel);
        options.put("currentStepNumber", String.valueOf(currentStepNumber));
        HttpResponse<String> response = productData.getProductData(RootApiUrlTest.ROOT_API_URL, partFamilyCode, CultureInfoTest.CULTURE_INFO, TokenTest.TOKEN, options);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParameters_whenProductData_thenIncorrect() {
        HashMap<String, String> options = new HashMap<>();
        HttpResponse<String> response = productData.getProductData(RootApiUrlTest.ROOT_API_URL, "incorrectPartFamilyCode", CultureInfoTest.CULTURE_INFO, TokenTest.TOKEN, options);
        ApiResults.showApiReturn(response);
        assertEquals(500, response.statusCode());
    }
}