package org.example.product;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class productConfiguration implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new productConfiguration()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "TraceParts code of the product family.")
    private String partFamilyCode;
    @CommandLine.Parameters(index = "2", description = "Language of the labels.")
    private String cultureInfo;
    @CommandLine.Parameters(index = "3", description = "Current SelectionPath from partFamilyInfo.")
    private String initialSelectionPath;
    @CommandLine.Parameters(index = "4", description = "Parameter code to update.")
    private String symbol;
    @CommandLine.Parameters(index = "5", description = "New value to set for the related symbol. When the parameter \"editable\" is set to \"true\", the value must start with =")
    private String value;

    @CommandLine.Option(names = {"--cadDetailLevel"}, defaultValue = "-1", description = "Integer related to the level of detail included in the CAD model.")
    private String cadDetailLevel;
    @CommandLine.Option(names = {"--currentStepNumber"}, defaultValue = "0", description = "[DEPRECATED] Current step of configuration.")
    private Integer currentStepNumber;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/post_v3-product-updateconfiguration
        ApiResults.showApiReturn(postProductConfiguration(RootApiUrl.ROOT_API_URL, token, getPossibleOptions()));
    }

    public static HttpResponse<String> postProductConfiguration(String rootApiUrl, String token, HashMap<String, String> possibleOptions) {
        StringBuilder possibleOptionsString = new StringBuilder();
        // Gathering all parameters
        for (Map.Entry<String, String> parameter : possibleOptions.entrySet()) {
            String key = parameter.getKey();
            String value = parameter.getValue();
            // Checking for each option if the value is usable (not empty)
            if (value != null && !value.isEmpty()) {
                // Format all usable options and adding them ones after others
                possibleOptionsString.append(key).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8)).append("&");
            }
        }

        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        // 📘 Warning! Any tries will be recorded in the Production data.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v3/Product/UpdateConfiguration?" + possibleOptionsString))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("POST", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }

    private HashMap<String, String> getPossibleOptions() {
        HashMap<String, String> optionalParameters = new HashMap<>();
        optionalParameters.put("partFamilyCode", partFamilyCode);
        optionalParameters.put("cultureInfo", cultureInfo);
        optionalParameters.put("initialSelectionPath", initialSelectionPath);
        optionalParameters.put("symbol", symbol);
        optionalParameters.put("value", value);
        optionalParameters.put("cadDetailLevel", cadDetailLevel);
        optionalParameters.put("currentStepNumber", currentStepNumber.toString());
        return optionalParameters;
    }
}

