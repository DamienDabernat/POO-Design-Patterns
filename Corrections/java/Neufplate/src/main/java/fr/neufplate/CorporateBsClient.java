package fr.neufplate;

import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CorporateBsClient {

    private static CorporateBsClient instance;

    private static final String apiUrl = "https://corporatebs-generator.sameerkumar.website";

    public static CorporateBsClient getInstance() {
        if (instance == null) {
            instance = new CorporateBsClient();
        }
        return instance;
    }


    public static String generateCorporateBs() {
        return parseJson(getInstance().makeRequest());
    }

    private String makeRequest() {
        try {
            URL url = new URL(apiUrl + "/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String parseJson(String json) {
        Pattern phrasePattern = Pattern.compile("\"phrase\":\"(.*)\"");
        Matcher phraseMatcher = phrasePattern.matcher(json);
        if(phraseMatcher.find()) {
            return phraseMatcher.group(1);
        }
        return null;
    }

}