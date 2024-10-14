package org.example.product;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

//📘 Warning! Any tries will be recorded in the Production data.
class productConfigurationTest {
    //Required
    private final String partFamilyCode = "your_part_family_code";
    private final String cultureInfo = "your_culture_info";
    private final String initialSelectionPath = "your_initial_selection_path";
    private final String symbol = "your_symbol";
    private final String value = "your_value";
    //Optional
    private final String cadDetailLevel = "-1"; // your_cad_detail_level
    //Deprecated
    private final Integer currentStepNumber = 0; //your_current_step_number

    @Test
    void givenCorrectParametersAndOptions_whenProductConfiguration_thenCorrect() {
        HashMap<String, String> options = new HashMap<>();
        options.put("partFamilyCode", partFamilyCode);
        options.put("cultureInfo", cultureInfo);
        options.put("initialSelectionPath", initialSelectionPath);
        options.put("symbol", symbol);
        options.put("value", value);
        //TODO: Be careful ! Any tries will be recorded in the Production data.
        HttpResponse<String> response = productConfiguration.postProductConfiguration(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, options);
        ApiResults.showApiReturn(response);
        assertEquals(200, response.statusCode());
    }

    @Test
    void givenIncorrectParametersAndOptions_whenProductConfiguration_thenIncorrect() {
        HashMap<String, String> options = new HashMap<>();
        options.put("partFamilyCode", "incorrectPartFamilyCode");
        options.put("cultureInfo", "incorrectCultureInfo");
        options.put("initialSelectionPath", "incorrectInitialSelectionPath");
        options.put("symbol", "incorrectSymbol");
        options.put("value", "incorrectValue");
        options.put("cadDetailLevel", "incorrectCadDetailLevel");
        HttpResponse<String> response = productConfiguration.postProductConfiguration(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, options);
        ApiResults.showApiReturn(response);
        assertEquals(500, response.statusCode());
    }

    @Test
    void givenWithoutParametersAndOptions_whenProductConfiguration_thenIncorrect() {
        HashMap<String, String> options = new HashMap<>(); // no parameter or option
        HttpResponse<String> response = productConfiguration.postProductConfiguration(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, options);
        ApiResults.showApiReturn(response);
        assertEquals(400, response.statusCode());
    }
}