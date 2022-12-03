package at.technikum.MaintenanceMonitor.dto;

import java.time.LocalDateTime;

public class Message {
    private String message;
    private LocalDateTime lastUpdateTime;

    public Message(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message=message;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
