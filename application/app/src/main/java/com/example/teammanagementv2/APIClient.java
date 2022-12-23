package com.example.teammanagementv2;

import com.example.teammanagementv2.entities.UserProfile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

    private final String BASE_URL = "https://4deb-2a02-2f09-980a-1100-e4a4-7f4c-e71a-7c60.eu.ngrok.io" ;
    private final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    Boolean doLogin(String username, String password) throws IOException {
        Map<String, String> loginBody = new TreeMap<>();
        loginBody.put("username", username);
        loginBody.put("password", password);

        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(loginBody));
        Request request = new Request.Builder()
                .url(BASE_URL + "/users/login")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.code() == 200;
    }

    void loginUserAsync(String username, String password, Callback callback) throws IOException {
        // async
        Map<String, String> loginBody = new TreeMap<>();
        loginBody.put("username", username);
        loginBody.put("password", password);
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(loginBody));
        Request request = new Request.Builder()
                .url(BASE_URL + "/users/login")
                .post(body)
                .build();

        client.newCall(request)
                .enqueue(callback);
    }

    public UserProfile fetchUserProfile(String username) throws IOException {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/users/profile/me")
                    .addHeader("x-username", username)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return objectMapper.readValue(response.body().byteStream(), UserProfile.class);
            } else {
                return UserProfile.NULL_PROFILE;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
    }


}
