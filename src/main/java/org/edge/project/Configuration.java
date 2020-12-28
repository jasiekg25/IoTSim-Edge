package org.edge.project;

import org.edge.project.DayHTTPManager;
import org.edge.project.data_models.HourlyPowerData;
import org.edge.project.data_models.Solar;

import java.io.IOException;
import java.time.LocalDateTime;

public class Configuration {
    private Solar solar;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private HourlyPowerData hourlyPowerData;
    private static int TICKS_IN_HOUR = 60;

    public Configuration(Solar solar, LocalDateTime startDate, int simulationTime) throws IOException {
        this.solar = solar;
        this.startDate = startDate;
        this.endDate = startDate.plusHours(simulationTime);

        this.hourlyPowerData = DayHTTPManager.getHourlyData(solar, startDate, endDate);
    }

    public double getPower(double time) {
        int fullHours = (int) time/TICKS_IN_HOUR;
        return this.hourlyPowerData.getPowerFromMoment(fullHours);
    }
}
