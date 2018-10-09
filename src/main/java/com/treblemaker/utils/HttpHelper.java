package com.treblemaker.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class HttpHelper {

    public URLConnection getURLConnection(String urlStr, String apiUser, String apiPassword) throws IOException {
        URL url = new URL(urlStr);

        String auth = apiUser + ":" + apiPassword;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String basicAuth = "Basic " + new String( encodedAuth );

        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", basicAuth);
        return urlConnection;
    }
}
