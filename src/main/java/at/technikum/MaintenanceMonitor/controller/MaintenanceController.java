package at.technikum.MaintenanceMonitor.controller;

import at.technikum.MaintenanceMonitor.dto.Message;
import at.technikum.MaintenanceMonitor.service.MaintenanceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @GetMapping("/uptime")
    ModelAndView getMessage(ModelAndView modelAndView){
        modelAndView.addObject("message", maintenanceService.getMessage());
        modelAndView.addObject("condition", maintenanceService.getMessage().isEmpty());
        modelAndView.setViewName("index");

        String timeStamp = maintenanceService.getLastUpdateTime() == null ? "" : maintenanceService.getLastUpdateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        modelAndView.addObject("time", timeStamp);
        return modelAndView;
    }

    @DeleteMapping("/uptime/reset")
    String resetMessage(){

        maintenanceService.resetMessage();

        return "";
    }
}
