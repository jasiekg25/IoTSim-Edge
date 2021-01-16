![agh_logo](./src/readMeResources/logo.png)

# IoT Course at AGH University of Science and Technology in Krakow
## Extend the IoT-Sim-Edge Simulator with data from PVGIS

###### Authors
<li> Bochnia Konrad
<li> Gargas Jan
<li> Siwek Patryk
<li> Węgrzyn Michał

### Project's goals
The main goal was to simulate photovoltaic battery behaviour in certain geographical coordinates. 
We want to check if photovoltaic installation is profitable and how many batteries we need to have in order to supply enough energy.

### Solution description
#### Main Idea
The existing [IoT-Sim-Edge Simulator](https://github.com/DNJha/IoTSim-Edge "IoT-Sim-Edge Simulator Homepage") was extended to gather appropriate data from the [PVGIS](https://ec.europa.eu/jrc/en/pvgis "PVGIS Homepage"). 
Data is kept in added classes (*org.edge.project.data_models*) and used to run simulator with the new configuration.
 
#### Configuration
```java
public class Solar {
    private double lat;
    private double lon;
    private double peakPower;
    private double loss;
...
}

...

public class Configuration {
    private Solar solar;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
...
}
```

To run simulation we need to set the geographic coordinates (lat/lon) for the solar battery. We also need to set peakPower and lossPerTick in battery. 
When the battery's configuration is done, the next step is to decide about startDate and endDate. 


#### Data Gathering
Method *buildURL* in *DayHTTPManager* is building, based on the configuration, url for query. Next we transform results into java objects and run simulation.

```java
public class DayHTTPManager {
    ...
    private static String buildUrl(String lat, String lon, double peakPower, double loss, LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder strBuilder = new StringBuilder("https://re.jrc.ec.europa.eu/api/seriescalc?")
                .append("lat=")
                .append(lat)
                .append("&lon=")
                .append(lon)
                .append("&startyear=")
                .append(startDate.getYear())
                .append("&endyear=")
                .append(endDate.getYear())
                .append("&peakpower=")
                .append(peakPower)
                .append("&loss=")
                .append(loss)
                .append("&pvcalculation=1&pvtechchoice=crystSi&optimalangles=1&outputformat=json");
        return strBuilder.toString();
    }
    ...
}
```

#### Running Simulation


#### Example of Simulation's results
