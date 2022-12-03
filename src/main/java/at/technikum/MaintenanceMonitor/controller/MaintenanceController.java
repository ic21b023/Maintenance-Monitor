package at.technikum.MaintenanceMonitor.controller;

import at.technikum.MaintenanceMonitor.service.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PutMapping(path="/uptime/setMessage")
    ResponseEntity<Void> setMessage(@RequestBody String message){
        maintenanceService.setMessage(message);
        maintenanceService.setLastUpdateTime(LocalDateTime.now());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/uptime", produces = MediaType.TEXT_HTML_VALUE)
    ModelAndView getMessage(ModelAndView modelAndView){
        modelAndView.addObject("message", maintenanceService.getMessage());
        modelAndView.addObject("condition", maintenanceService.getMessage().isEmpty());
        modelAndView.setViewName("index.html");
        String timeStamp = maintenanceService.getLastUpdateTime() == null ? "" : maintenanceService.getLastUpdateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        modelAndView.addObject("time", timeStamp);
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @DeleteMapping("/uptime/reset")
    ResponseEntity<Void> resetMessage(){
        maintenanceService.resetMessage();
        return ResponseEntity.ok().build();
    }
}
