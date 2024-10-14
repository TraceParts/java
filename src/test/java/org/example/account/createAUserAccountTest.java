package org.example.account;

import org.example.RootApiUrlTest;
import org.example.TokenTest;
import org.example.utils.ApiResults;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

//📘 Warning! Any tries will be recorded in the Production data.
class createAUserAccountTest {
    private final String userEmail = "your_user_email";
    //Optional
    private final String company = "your_user_company";
    private final String country = "your_user_country";
    private final String name = "your_user_name";
    private final String fname = "your_user_fname";
    private final String addr1 = "your_user_addr1";
    private final String addr2 = "your_user_addr2";
    private final String addr3 = "your_user_addr3";
    private final String city = "your_user_city";
    private final String state = "your_user_state";
    private final String zipCode = "your_user_zipCode";
    private final String phone = "your_user_phone";
    private final String fax = "your_user_fax";
    private final Boolean tpOptIn = false; //your_user_tpOptIn
    private final Boolean partnersOptIn = false; //your_user_partnersOptIn

    @Test
    void givenCorrectParametersAndOptions_whenCreateAUserAccount_thenCorrect() {
        HashMap<String, String> options = new HashMap<>();
        options.put("userEmail", userEmail);
        options.put("company", company);
        options.put("country", country);
        options.put("name", name);
        options.put("fname", fname);
        options.put("addr1", addr1);
        options.put("addr2", addr2);
        options.put("addr3", addr3);
        options.put("city", city);
        options.put("state", state);
        options.put("zipCode", zipCode);
        options.put("phone", phone);
        options.put("fax", fax);
        options.put("tpOptIn", tpOptIn.toString());
        options.put("partnersOptIn", partnersOptIn.toString());
        //TODO: delete this test user after creation to avoid DB clutter
        HttpResponse<String> response = createAUserAccount.postCreateAUserAccount(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, userEmail, options);
        ApiResults.showApiReturn(response);
        assertEquals(201, response.statusCode());
    }

    @Test
    void givenInvalidParameters_whenCreateAUserAccount_thenIncorrect() {
        // userEmail format is invalid because it does not correspond to an email format
        HashMap<String, String> options = new HashMap<>(); // no other options
        HttpResponse<String> response = createAUserAccount.postCreateAUserAccount(RootApiUrlTest.ROOT_API_URL, TokenTest.TOKEN, "invalidUserEmail", options);
        ApiResults.showApiReturn(response);
        assertEquals(500, response.statusCode());
    }
}