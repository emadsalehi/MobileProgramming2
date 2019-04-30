package com.example.mobileprograming2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Database {
    private ArrayList <String>  mainData;
    private String JsonString

    public String getString() {
        String fullString = "";
        StringBuilder fullStringBuilder = new StringBuilder();
        URL url = null;
        try {
            url = new URL("https://jsonplaceholder.typicode.com/posts");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = "";
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            fullStringBuilder.append(line + "\n");
        }
        fullString = fullStringBuilder.toString();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
