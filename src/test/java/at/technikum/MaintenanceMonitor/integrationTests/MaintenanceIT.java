package at.technikum.MaintenanceMonitor.integrationTests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class MaintenanceIT {

    @Test
    public void testPut() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/uptime/setMessage"))
                .header("Content-Type", "text/plain")
                .PUT(HttpRequest.BodyPublishers.ofString("Test"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.body().isEmpty());
        assertEquals(HttpStatus.OK.value(),response.statusCode());
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
        Element title = doc.getElementById("title");
        Element message = doc.getElementById("message");
        Element updatetime = doc.getElementById("updatetime");
        LocalTime setTime = LocalTime.parse(updatetime.text().split(" ")[2]);

        Duration duration = Duration.between(currentTime, setTime);

        assertEquals("Maintenance Monitor",doc.title());
        assertEquals("Maintenance Monitor",title.text());
        assertEquals(t_message, message.text());
        assertTrue(duration.getSeconds() <= 3);
        assertEquals(HttpStatus.OK.value(),response.statusCode());
    }

    private HttpResponse<String> setUpMessageViaPost(String message) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/uptime/setMessage"))
                .header("Content-Type", "text/plain")
                .PUT(HttpRequest.BodyPublishers.ofString(message))
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

        assertTrue(response.body().isEmpty());
        assertEquals(HttpStatus.OK.value(),response.statusCode());
    }
}
