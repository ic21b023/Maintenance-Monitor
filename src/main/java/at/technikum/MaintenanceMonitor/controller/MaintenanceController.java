package at.technikum.MaintenanceMonitor.controller;

import at.technikum.MaintenanceMonitor.dto.Message;
import at.technikum.MaintenanceMonitor.service.MaintenanceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService){
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/uptime/setMessage")
    Message setMessage(@RequestBody Message dto){
        maintenanceService.setMessage(dto.getMessage());
        maintenanceService.setLastUpdateTime(LocalDateTime.now());
        return maintenanceService.getCopyOfCurrentlyStoredMessage();
    }
}
