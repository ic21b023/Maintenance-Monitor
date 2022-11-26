package at.technikum.MaintenanceMonitor.dto;

import java.time.LocalDateTime;
import java.time.Month;

public class Message {

    private String message ="";
    private LocalDateTime lastUpdateTime=LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0, 0);

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static Message getEmpty(){
        Message dto = new Message();
        dto.setMessage("");
        return dto;
    }
}
