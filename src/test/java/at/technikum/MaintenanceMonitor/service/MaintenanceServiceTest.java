package at.technikum.MaintenanceMonitor.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaintenanceServiceTest {
    MaintenanceService maintenanceService = new MaintenanceService();

    @Test
    void getMessage() {
        assertEquals(maintenanceService.getMessage(),(""));
    }

    @Test
    void setMessage() {
        maintenanceService.setMessage("Test");
        assertEquals(maintenanceService.getMessage(),("Test"));
    }

    @Test
    void getLastUpdateTime() {
        assertEquals(null, maintenanceService.getLastUpdateTime());
    }

    @Test
    void setLastUpdateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        maintenanceService.setLastUpdateTime(localDateTime);

        assertEquals(localDateTime, maintenanceService.getLastUpdateTime());
    }
}
