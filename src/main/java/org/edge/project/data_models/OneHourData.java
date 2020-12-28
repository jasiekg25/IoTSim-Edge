package org.edge.project.data_models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OneHourData {
    private String time;
    private double P;
    private double G;
    private double H_sun;
    private double T2m;
    private double WS10m;
    private double Int;

    public double getP() {
        return this.P;
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd:HHmm"));
    }
}
