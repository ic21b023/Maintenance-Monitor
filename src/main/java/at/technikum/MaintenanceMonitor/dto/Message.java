package at.technikum.MaintenanceMonitor.dto;

import java.time.LocalDateTime;
import java.time.Month;

public class Message {

    private String message;
    private LocalDateTime lastUpdateTime;
    public Message(String message){
        this.message = message;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Message getEmpty(){
        Message dto = new Message("Message was received");

        return dto;
    }

    public static Message getCopy(Message dto){
        return new Message(dto.getMessage());
    }
}
