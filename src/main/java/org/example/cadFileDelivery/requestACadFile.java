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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class requestACadFile implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new requestACadFile()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Email address associated to the CAD request event.")
    private String userEmail;
    @CommandLine.Parameters(index = "2", description = "Language for the labels of the CAD formats.")
    private String cultureInfo;
    @CommandLine.Parameters(index = "3", description = "TraceParts ID of the CAD format.")
    private Integer cadFormatId;

    @CommandLine.Option(names = {"--partFamilyCode"}, description = "TraceParts code of the product family.")
    private String partFamilyCode;
    @CommandLine.Option(names = {"--partNumber"}, description = "Identifier of a product (to use in combination with classificationCode). Part number as stored in the TraceParts database.")
    private String partNumber;
    @CommandLine.Option(names = {"--classificationCode"}, description = "TraceParts code of the classification (to use in combination with partNumber).")
    private String classificationCode;
    @CommandLine.Option(names = {"--selectionPath"}, description = "Selected configuration (to use in combination with partFamilyCode. If not provided, the product is loaded with default configuration).")
    private String selectionPath;

    @CommandLine.Option(names = {"--cadDetailLevelId"}, description = "TraceParts ID of the optional detail level for the CAD model.")
    private String cadDetailLevel;
    @CommandLine.Option(names = {"--languageId"}, description = "[DEPRECATED] TraceParts ID of the language (obsolete - please use cultureInfo).")
    private String languageId;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-search-productandcategorylist
        ApiResults.showApiReturn(postRequestACadFile(RootApiUrl.ROOT_API_URL, token, userEmail, cultureInfo, cadFormatId, getPossibleOptions()));
    }

    public static HttpResponse<String> postRequestACadFile(String rootApiUrl, String token, String userEmail, String cultureInfo, Integer cadFormatId, HashMap<String, String> possibleOptions) {
        StringBuilder possibleOptionsString = new StringBuilder();
        // Gathering all parameters
        for (Map.Entry<String, String> parameter : possibleOptions.entrySet()) {
            String key = parameter.getKey();
            // Encoding the value to avoid special characters
            String value = URLEncoder.encode(parameter.getValue(), StandardCharsets.UTF_8);
            // Checking for each option if the value is usable (not empty)
            if (value != null && !value.isEmpty()) {
                // Define regex patterns to match different types of values
                // Each of this types must be formated differently in the options string
                Pattern truePattern = Pattern.compile("^true$", Pattern.CASE_INSENSITIVE);
                Pattern falsePattern = Pattern.compile("^false$", Pattern.CASE_INSENSITIVE);
                Pattern numberPattern = Pattern.compile("^\\d+$");
                Matcher trueMatcher = truePattern.matcher(value);
                Matcher falseMatcher = falsePattern.matcher(value);
                Matcher numberMatcher = numberPattern.matcher(value);
                boolean trueMatchFound = trueMatcher.find();
                boolean falseMatchFound = falseMatcher.find();
                boolean numberMatchFound = numberMatcher.find();
                if (trueMatchFound) {
                    // We can't simply use Boolean.toString() here because inputs may are not correct and
                    // Boolean.toString() returns 1 or 0 not "true" or "false"
                    possibleOptionsString.append(",\"").append(key).append("\":true");
                } else if (falseMatchFound) {
                    // We can't simply use Boolean.toString() here because inputs may are not correct and
                    // Boolean.toString() returns 1 or 0 not "true" or "false"
                    possibleOptionsString.append(",\"").append(key).append("\":false");
                } else if (numberMatchFound) {
                    // Numbers must be added to the parameters string like int (1) not string ("1")
                    possibleOptionsString.append(",\"").append(key).append("\":").append(value);
                } else {
                    possibleOptionsString.append(",\"").append(key).append("\":").append('"').append(value).append('"');
                }
            }
        }
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        //📘 Warning! Any tries will be recorded in the Production data.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v3/Product/cadRequest"))
                .header("accept", "application/json")
                .header("content-type", "application/*+json")
                .header("authorization", "Bearer " + token)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"userEmail\":\"" + userEmail + "\",\"cultureInfo\":\"" + cultureInfo + "\",\"cadFormatId\":" + cadFormatId + possibleOptionsString + "}"))
                .build();
        return ApiResults.getApiReturn(request);
    }

    private HashMap<String, String> getPossibleOptions() {
        // This is made to simplify the options management
        HashMap<String, String> optionalParameters = new HashMap<>();
        optionalParameters.put("partFamilyCode", partFamilyCode);
        optionalParameters.put("selectionPath", selectionPath);
        optionalParameters.put("classificationCode", classificationCode);
        optionalParameters.put("partNumber", partNumber);
        optionalParameters.put("cadDetailLevel", cadDetailLevel);
        optionalParameters.put("languageId", languageId);
        return optionalParameters;
    }
}
