package com.example.goresometimes.coronavirustracker.services;


import com.example.goresometimes.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
// Spring creates instance of this class
public class CoronavirusDataService {


    private final static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
//    private Object CSVFormat;




    @PostConstruct
    @Scheduled(cron = "* * 1 * * * ") // Causes the API to run on a daily basis to update the data
    // After instance has been created then execute this method to create GET request.
    public List<LocationStats> fetchVirus() throws IOException, InterruptedException {
        List<LocationStats> allStats = new ArrayList<>();
        // Create New Client
       HttpClient client = HttpClient.newHttpClient();
       // Create New Request
       HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL)).build();
       // Sending Client Request and Turning Data Into String
       HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        Reader csvBodyReader = new StringReader(httpResponse.body());
        CSVParser records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            System.out.println(locationStat);
            allStats.add(locationStat);
        }

        return allStats;
    }
}
