package org.example;

import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class the3dViewerImplementationTest {
    private String elsid = "your_elsid";

    @Test
    void givenCorrectParametersWithoutOptions_whenThe3dViewerImplementation_thenCorrect() {
        HashMap<String, String> options = new HashMap<>();
        String responseUrl = the3dViewerImplementation.getThe3dViewerImplementationUrl(elsid, CultureInfoTest.CULTURE_INFO, options);
        String expectedUrl = "https://www.traceparts.com/els/" +
                URLEncoder.encode(elsid, StandardCharsets.UTF_8) +
                "/" +
                URLEncoder.encode(CultureInfoTest.CULTURE_INFO, StandardCharsets.UTF_8) +
                "/api/viewer/3d?";
        assertEquals(expectedUrl, responseUrl);
    }

    @Test
    void givenCorrectParametersAndOptions_whenThe3dViewerImplementation_thenCorrect() {
        HashMap<String, String> options = new HashMap<>();
        options.put("enablePresentationMode", String.valueOf(true));
        String responseUrl = the3dViewerImplementation.getThe3dViewerImplementationUrl(elsid, CultureInfoTest.CULTURE_INFO, options);
        String expectedUrl = "https://www.traceparts.com/els/" +
                URLEncoder.encode(elsid, StandardCharsets.UTF_8) +
                "/" +
                URLEncoder.encode(CultureInfoTest.CULTURE_INFO, StandardCharsets.UTF_8) +
                "/api/viewer/3d?" +
                "enablePresentationMode=true&";
        assertEquals(expectedUrl, responseUrl);
    }

}