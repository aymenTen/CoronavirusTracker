package com.example.demo.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.models.LocationStats;



@Service
public class CoronaVirusDataService {
	private String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private List<LocationStats> allStats = new ArrayList<>();

	@PostConstruct
	@Scheduled(cron ="* * 1 * * *")
	public void fetchVirusData () throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest req = HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		HttpResponse<String> httpResponse = client.send(req, HttpResponse.BodyHandlers.ofString());

		StringReader csvBodyReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for (CSVRecord record : records) {
			LocationStats locationStats = new LocationStats();
			String country = record.get("Country/Region");
			String state = record.get("Province/State");
			int todayCount = Integer.parseInt(record.get(record.size()-1));
			int changeCount = Integer.parseInt(record.get(record.size()-1))-Integer.parseInt(record.get(record.size()-2));
			locationStats.setCountry(country);
			if(state.equals(""))
				locationStats.setState("/");
			else
				locationStats.setState(state);
			
			locationStats.setLatestTotalCases(todayCount);
			locationStats.setChange(changeCount);
			newStats.add(locationStats);
		}

		allStats=newStats;
	}

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	public void setAllStats(List<LocationStats> allStats) {
		this.allStats = allStats;
	}

}
