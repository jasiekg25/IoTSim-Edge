package org.edge.project.data_models;

import java.util.List;

public class HourlyPowerData {
    private List<HourAndPower> data;

    public HourlyPowerData(List<HourAndPower> data) {
        this.data = data;
    }

    public List<HourAndPower> getData() {
        return data;
    }

    public double getPowerFromMoment(int moment) {
        return this.data.get(moment).getPower();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        this.data.forEach(hp -> res.append(hp.toString()).append("\n"));
        return res.toString();
    }
}
