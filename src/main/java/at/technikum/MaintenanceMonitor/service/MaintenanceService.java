package at.technikum.MaintenanceMonitor.service;

import at.technikum.MaintenanceMonitor.dto.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MaintenanceService {
    private Message messageModel = new Message("");

    public void setMessage(String message){
        messageModel.setMessage(message);
    }

    public void setLastUpdateTime(LocalDateTime time){
        messageModel.setLastUpdateTime(time);
    }

    public Message getCopyOfCurrentlyStoredMessage(){
        Message msg = new Message(messageModel.getMessage());
        msg.setLastUpdateTime(messageModel.getLastUpdateTime());

        return msg;
    }

    public String getMessage(){
        return messageModel.getMessage();
    }

    public LocalDateTime getLastUpdateTime() {
        return messageModel.getLastUpdateTime();
    }
}
