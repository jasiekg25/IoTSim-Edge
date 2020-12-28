package org.edge.project.data_models;

public class Solar {
    private double lat;
    private double lon;
    private double peakPower;
    private double loss;

    public Solar(double lat, double lon, double peakPower, double loss) {
        this.lat = lat;
        this.lon = lon;
        this.peakPower = peakPower;
        this.loss = loss;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getPeakPower() {
        return peakPower;
    }

    public double getLoss() {
        return loss;
    }
}
