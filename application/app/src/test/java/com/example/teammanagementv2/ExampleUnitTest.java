package com.example.teammanagementv2;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.teammanagementv2.entities.Task;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws IOException {
        Task t = new Task("desc", "string", "mking", LocalDateTime.now());
        APIClient apiClient = new APIClient();
        apiClient.createTask(t);
    }
}