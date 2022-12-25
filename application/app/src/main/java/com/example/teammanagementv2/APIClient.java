package com.example.teammanagementv2;

import com.example.teammanagementv2.entities.Task;
import com.example.teammanagementv2.entities.UserProfile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

    private final String BASE_URL = "https://c93f-86-120-95-90.eu.ngrok.io" ;
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

    public ArrayList<Task> fetchUserTasks(String username) throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();
//        tasks.add(new Task(1, "aaa", null, null, null));
//        tasks.add(new Task(2, "bbb", null, null, null));
        //return tasks;

        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/tasks/find/" + username)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return new ArrayList<Task>(Arrays.asList(objectMapper.readValue(response.body().byteStream(), Task[].class)));
            } else {
                return new ArrayList<Task>();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
