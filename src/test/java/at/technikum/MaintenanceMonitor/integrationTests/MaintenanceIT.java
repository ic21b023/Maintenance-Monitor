package at.technikum.MaintenanceMonitor.integrationTests;

import at.technikum.MaintenanceMonitor.dto.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MaintenanceIT {

    @Test
    public void testPost() throws IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String requestBody = objectMapper.writeValueAsString("Test");

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/uptime/setMessage"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Message messagee = new Message("Test",LocalDateTime.now());
        JsonNode expectedJsonNode = objectMapper.readTree(objectMapper.writeValueAsString(messagee));
        String expectedMessage = expectedJsonNode.get("message").textValue();
        String expectedLastUpdateTime = expectedJsonNode.get("lastUpdateTime").textValue();
        JsonNode actualJsonNode = objectMapper.readTree(response.body());
        String actualMessage = actualJsonNode.get("message").textValue();
        String actualLastUpdateTime = actualJsonNode.get("lastUpdateTime").textValue();

        LocalDateTime actualdateTime = LocalDateTime.parse(actualLastUpdateTime);
        LocalDateTime expecteddateTime = LocalDateTime.parse(expectedLastUpdateTime);
        Duration duration = Duration.between(actualdateTime,expecteddateTime);

        assertTrue(expectedMessage.equals(actualMessage) && duration.getSeconds()==0);
    }

    @Test
    public void testGet() throws IOException, InterruptedException {
        final String t_message = "Test";
        LocalTime currentTime = LocalTime.now();

        setUpMessageViaPost(t_message);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/uptime"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Document doc = Jsoup.parse(response.body());

        Element mainDiv = doc.getElementById("main");
        Element title = doc.getElementById("title");
        Element message = doc.getElementById("message");
        Element time = doc.getElementById("time");
        Element updateTimer = doc.getElementById("updateTimer");

        LocalTime setTime = LocalTime.parse(time.text().split(" ")[2]);

        Duration duration = Duration.between(currentTime, setTime);

        assertNotNull(mainDiv);
        assertNotNull(title);
        assertNotNull(message);
        assertNotNull(time);
        assertNotNull(updateTimer);
        assertTrue(time.hasText());
        assertEquals(t_message, message.text());
        assertTrue(duration.getSeconds() <= 3);
    }

    private HttpResponse<String> setUpMessageViaPost(String message) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String requestBody = objectMapper.writeValueAsString(message);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/uptime/setMessage"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    @Test
    public void testDelete() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/uptime/reset"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals("Delete OK",response.body());
    }
}
