package at.technikum.MaintenanceMonitor.integrationTests;

import at.technikum.MaintenanceMonitor.dto.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
