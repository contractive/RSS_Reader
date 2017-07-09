package com.example.administrator.rssreader.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 09/07/2017.
 */

public class HTTPDataHandler {
    static String stream = "";
    public HTTPDataHandler(){}
    public String  getHTTPDataHandler(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null){
                    builder.append(line);
                    stream = builder.toString();
                }
                urlConnection.disconnect();
            }
            return stream;
        } catch (Exception ex){
            return null;
        }
    }
}
