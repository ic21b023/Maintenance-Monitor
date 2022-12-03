package at.technikum.MaintenanceMonitor.service;

import at.technikum.MaintenanceMonitor.dto.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MaintenanceService {
    private static final String DEF_MESSAGE = "";

    private Message messageModel = new Message(DEF_MESSAGE);

    public void setMessage(String message){
        messageModel.setMessage(message);
    }

    public void setLastUpdateTime(LocalDateTime time){
        messageModel.setLastUpdateTime(time);
    }

    public String getMessage(){
        return messageModel.getMessage();
    }

    public LocalDateTime getLastUpdateTime() {
        return messageModel.getLastUpdateTime();
    }

    public void resetMessage(){
        setMessage(DEF_MESSAGE);
        setLastUpdateTime(LocalDateTime.now());
    }
}
