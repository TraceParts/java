package org.example.cadFileDelivery;

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

public class getCadFormatsList implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new getCadFormatsList()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Language for the labels of the CAD formats.")
    private String cultureInfo;

    @CommandLine.Option(names = {"--partFamilyCode"}, description = "TraceParts code of the product family.")
    private String partFamilyCode;
    @CommandLine.Option(names = {"--selectionPath"}, description = "Selected configuration (it is used in combination with partFamilyCode if not provided part will be loaded with default configuration)")
    private String selectionPath;
    @CommandLine.Option(names = {"--classificationCode"}, description = "TraceParts code of the classification (to use in combination with partNumber).")
    private String classificationCode;
    @CommandLine.Option(names = {"--partNumber"}, description = "Identifier of a product (to use in combination with classificationCode). Part number as stored in the TraceParts database.")
    private String partNumber;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-search-productandcategorylist
        HttpResponse<String> response = getGetCadFormatsList(RootApiUrl.ROOT_API_URL, token, cultureInfo, getPossibleOptions());
        System.out.println(response.statusCode());
        ApiResults.showApiReturn(response);
    }

    public static HttpResponse<String> getGetCadFormatsList(String rootApiUrl, String token, String cultureInfo, HashMap<String, String> possibleOptions) {
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
                .uri(URI.create(rootApiUrl + "v3/Product/CadDataAvailability?cultureInfo=" + cultureInfo + possibleOptionsString))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }

    private HashMap<String, String> getPossibleOptions() {
        HashMap<String, String> optionalParameters = new HashMap<>();
        optionalParameters.put("partFamilyCode", partFamilyCode);
        optionalParameters.put("selectionPath", selectionPath);
        optionalParameters.put("classificationCode", classificationCode);
        optionalParameters.put("partNumber", partNumber);
        return optionalParameters;
    }
}