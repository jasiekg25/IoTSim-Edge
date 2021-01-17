package org.edge.project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BatteryLogger {
    private String filename;
    private List<Measurement> measurements = new ArrayList<>();
    private boolean hasBeenWritten = false;

    public BatteryLogger(String filename) {
        this.filename = filename;
    }

    public void log(double time, double value) {
        measurements.add(new Measurement(time, value));
    }

    public void write() {
        if (hasBeenWritten) return;
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Measurement measure : measurements) {
                writer.printf("%f\t%f\n", measure.time, measure.value);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        hasBeenWritten = true;
    }

    private static class Measurement {
        double time;
        double value;

        public Measurement(double time, double value) {
            this.time = time;
            this.value = value;
        }
    }
}
