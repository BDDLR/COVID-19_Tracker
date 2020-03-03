package com.bryan.tracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.bryan.tracker.model.LocationStats;

@Controller
public class CoronavirusDataService {

	private static final Logger log = LoggerFactory.getLogger(CoronavirusDataService.class);

	private static String updatedDataTime;

	private static String CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

	private static String RECOVERED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";

	// private static String CONFIRMED_URL =
	// "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

	private List<LocationStats> confirmedStats = new ArrayList<>();

	private List<LocationStats> recoveryStats = new ArrayList<>();

	private List<LocationStats> deathStats = new ArrayList<>();

	@PostConstruct
	@Scheduled(cron = "*/60 * * * * *")
	public void fetchConfirmedData() throws IOException {

		RestTemplate restTemplate = new RestTemplate();
		String resultData = restTemplate.getForObject(CONFIRMED_URL, String.class);
		confirmedStats = parseData(resultData);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YY HH:mm:ss");
		updatedDataTime = dateFormat.format(new Date());

		log.info("Data updated on {}", updatedDataTime);

	}

	public List<LocationStats> parseData(String data) throws IOException {
		StringReader in = new StringReader(data);

		List<LocationStats> auxStats = new ArrayList<>();

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

		for (CSVRecord record : records) {
			LocationStats locationStats = new LocationStats();
			locationStats.setState(record.get("Province/State"));
			locationStats.setCountry(record.get("Country/Region"));

			int latestCases = Integer.parseInt(record.get(record.size() - 1));
			int prevCases = Integer.parseInt(record.get(record.size() - 2));

			locationStats.setLatestTotal(latestCases);
			locationStats.setIncreaseFromYesterday(latestCases - prevCases);

			auxStats.add(locationStats);
		}

		return auxStats;

	}

	public List<LocationStats> getConfirmedStats() {
		return confirmedStats;
	}

	public String getUpdatedDataTime() {
		return updatedDataTime;
	}
}
