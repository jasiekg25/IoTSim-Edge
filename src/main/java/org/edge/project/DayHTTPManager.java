package org.edge.project;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.edge.project.data_models.HourAndPower;
import org.edge.project.data_models.HourlyPowerData;
import org.edge.project.data_models.OneHourData;
import org.edge.project.data_models.Solar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class DayHTTPManager {

    public DayHTTPManager() {}

    private static String buildUrl(String lat, String lon, double peakPower, double loss, LocalDateTime startDate, LocalDateTime endDate) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(10);
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
                .append(df.format(peakPower))
                .append("&loss=")
                .append(loss)
                .append("&pvcalculation=1&pvtechchoice=crystSi&optimalangles=1&outputformat=json");
        return strBuilder.toString();
    }

    public static HourlyPowerData getHourlyData(Solar solar, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        return getHourlyData(String.valueOf(solar.getLat()), String.valueOf(solar.getLon()), solar.getPeakPower(), solar.getLoss(), startDate, endDate);
    }
    public static HourlyPowerData getHourlyData(String lat, String lon, double peakPower, double loss, LocalDateTime startDate, LocalDateTime endDate) throws IOException {

        List<OneHourData> resultList = sendGET(buildUrl(lat, lon, peakPower, loss, startDate, endDate));

        return new HourlyPowerData(resultList.stream()
                .filter(ohd -> ohd.getLocalDateTime().isAfter(startDate) && ohd.getLocalDateTime().isBefore(endDate))
                .map(ohd -> new HourAndPower(ohd.getLocalDateTime(), ohd.getP()))
                .collect(Collectors.toList()));
    }

    private static List<OneHourData> sendGET(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            String timeData = gson.fromJson(response.toString(), JsonObject.class).get("outputs").getAsJsonObject().get("hourly").getAsJsonArray().toString();
            Type collectionType = new TypeToken<List<OneHourData>>(){}.getType();
            return gson.fromJson( timeData , collectionType);
        } else {
            System.out.println("GET request not worked");
        }
        return null;
    }

}
