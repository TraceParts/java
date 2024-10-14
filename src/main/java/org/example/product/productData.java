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

public class productData implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new productData()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "TraceParts code of the product family.")
    private String partFamilyCode;
    @CommandLine.Parameters(index = "2", description = "Language of the labels.")
    private String cultureInfo;

    @CommandLine.Option(names = {"--selectionPath"}, description = "Selected configuration (to use in combination with partFamilyCode. If not provided, the product is loaded with default configuration).")
    private String selectionPath;
    @CommandLine.Option(names = {"--cadDetailLevel"}, defaultValue = "-1", description = "Integer related to the level of detail included in the CAD model.")
    private String cadDetailLevel;

    @CommandLine.Option(names = {"--currentStepNumber"}, defaultValue = "0", description = "[DEPRECATED] Current step of configuration.")
    private Integer currentStepNumber;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v3-product-configure
        ApiResults.showApiReturn(getProductData(RootApiUrl.ROOT_API_URL, partFamilyCode, cultureInfo, token, getPossibleOptions()));
    }

    public static HttpResponse<String> getProductData(String rootApiUrl, String partFamilyCode, String cultureInfo, String token, HashMap<String, String> possibleOptions) {
        StringBuilder possibleOptionsString = new StringBuilder();
        // Gathering all parameters
        for (Map.Entry<String, String> parameter : possibleOptions.entrySet()) {
            String key = parameter.getKey();
            String value = parameter.getValue();
            // Checking for each option if the value is usable (not empty)
            if (value != null && !value.isEmpty()) {
                // Format all usable options and adding them ones after others
                possibleOptionsString.append("&").append(key).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8));
            }
        }

        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v3/Product/Configure?partFamilyCode=" + URLEncoder.encode(partFamilyCode, StandardCharsets.UTF_8) + "&cultureInfo=" + URLEncoder.encode(cultureInfo, StandardCharsets.UTF_8) + possibleOptionsString))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }

    private HashMap<String, String> getPossibleOptions() {
        HashMap<String, String> optionalParameters = new HashMap<>();
        optionalParameters.put("selectionPath", selectionPath);
        optionalParameters.put("cadDetailLevel", cadDetailLevel);
        optionalParameters.put("currentStepNumber", String.valueOf(currentStepNumber));
        return optionalParameters;
    }
}
