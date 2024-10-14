package org.example.account;

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

//TODO: not tested -> need to test with someone who can manage test accounts
public class createAUserAccount implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new createAUserAccount()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Email address linked to the account.")
    private String userEmail;

    @CommandLine.Option(names = {"--company"}, description = "User company.")
    private String company;
    @CommandLine.Option(names = {"--country"}, description = "User country. ISO 3166-2 characters.")
    private String country;
    @CommandLine.Option(names = {"--name"}, description = "User last name.")
    private String name;
    @CommandLine.Option(names = {"--fname"}, description = "User first name.")
    private String fname;
    @CommandLine.Option(names = {"--addr1"}, description = "First field for the user address.")
    private String addr1;
    @CommandLine.Option(names = {"--addr2"}, description = "Second field for the user address.")
    private String addr2;
    @CommandLine.Option(names = {"--addr3"}, description = "Third field for the user address.")
    private String addr3;
    @CommandLine.Option(names = {"--city"}, description = "User city.")
    private String city;
    @CommandLine.Option(names = {"--state"}, description = "User state, for North America.")
    private String state;
    @CommandLine.Option(names = {"--zipCode"}, description = "User ZIP code.")
    private String zipCode;
    @CommandLine.Option(names = {"--phone"}, description = "User phone number.")
    private String phone;
    @CommandLine.Option(names = {"--fax"}, description = "User FAX number.")
    private String fax;
    @CommandLine.Option(names = {"--tpOptIn"}, description = " Consent to receive information sent by TraceParts by email about TraceParts services.")
    private Boolean tpOptIn;
    @CommandLine.Option(names = {"--partnersOptIn"}, description = "Consent to receive information sent by TraceParts by email about TraceParts’ partners’ services.")
    private Boolean partnersOptIn;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/post_v2-account-signup
        ApiResults.showApiReturn(postCreateAUserAccount(RootApiUrl.ROOT_API_URL, token, userEmail, getPossibleOptions()));
    }

    public static HttpResponse<String> postCreateAUserAccount(String rootApiUrl, String token, String userEmail, HashMap<String, String> possibleOptions) {
        StringBuilder optionalParametersString = new StringBuilder();

        // Gathering all parameters
        for (Map.Entry<String, String> parameter : possibleOptions.entrySet()) {
            String key = parameter.getKey();
            String value = URLEncoder.encode(parameter.getValue(), StandardCharsets.UTF_8);
            // Checking for each option if the value is usable (not empty)
            if (value != null && !value.isEmpty()) {
                // Define regex patterns to match different types of values
                // Each of this types must be formated differently in the options string
                Pattern truePattern = Pattern.compile("^true$", Pattern.CASE_INSENSITIVE);
                Pattern falsePattern = Pattern.compile("^false$", Pattern.CASE_INSENSITIVE);
                Matcher trueMatcher = truePattern.matcher(value);
                Matcher falseMatcher = falsePattern.matcher(value);
                boolean trueMatchFound = trueMatcher.find();
                boolean falseMatchFound = falseMatcher.find();
                if (trueMatchFound) {
                    // We can't simply use Boolean.toString() here because inputs may are not correct and
                    // Boolean.toString() returns 1 or 0 not "true" or "false"
                    optionalParametersString.append(",\"").append(key).append("\":true");
                } else if (falseMatchFound) {
                    // We can't simply use Boolean.toString() here because inputs may are not correct and
                    // Boolean.toString() returns 1 or 0 not "true" or "false"
                    optionalParametersString.append(",\"").append(key).append("\":false");
                } else {
                    optionalParametersString.append(",\"").append(key).append("\":").append('"').append(value).append('"');
                }
            }
        }

        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        // 📘 Warning! Any tries will be recorded in the Production data.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Account/SignUp"))
                .header("accept", "application/json")
                .header("content-type", "application/*+json")
                .header("authorization", "Bearer " + token)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"userEmail\":" + userEmail + optionalParametersString + "}"))
                .build();
        return ApiResults.getApiReturn(request);
    }

    private HashMap<String, String> getPossibleOptions() {
        HashMap<String, String> optionalParameters = new HashMap<>();
        optionalParameters.put("company", company);
        optionalParameters.put("country", country);
        optionalParameters.put("name", name);
        optionalParameters.put("fname", fname);
        optionalParameters.put("addr1", addr1);
        optionalParameters.put("addr2", addr2);
        optionalParameters.put("addr3", addr3);
        optionalParameters.put("city", city);
        optionalParameters.put("state", state);
        optionalParameters.put("zipCode", zipCode);
        optionalParameters.put("phone", phone);
        optionalParameters.put("fax", fax);
        optionalParameters.put("tpOptIn", tpOptIn.toString());
        optionalParameters.put("partnersOptIn", partnersOptIn.toString());
        return optionalParameters;
    }
}
