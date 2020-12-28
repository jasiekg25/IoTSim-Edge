package org.edge.project.data_models;

import java.time.LocalDateTime;

public class HourAndPower {
    private LocalDateTime time;
    private double power;

    public HourAndPower(LocalDateTime time, double power) {
        this.time = time;
        this.power = power;
    }

    public LocalDateTime getTime() { return time; }

    public void setTime(LocalDateTime time) { this.time = time; }

    public double getPower() { return power; }

    public void setPower(double power) { this.power = power; }

    @Override
    public String toString() {
        return "T=" + time + ", P=" + power;
    }
}
