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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bryan.tracker.model.LocationStats;

@Service
public class RecoveredService {
	
	private static final Logger log = LoggerFactory.getLogger(ConfirmedService.class);
	
	private static String recoveredDataTime;
	
	private static String RECOVERED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
	
	private List<LocationStats> recoveredStats = new ArrayList<>();
	
	/* Method that gets the recovered csv file from the specified URL */
	@PostConstruct
	@Scheduled(cron = "*/60 * * * * *")
	public void fetchRecoveredData() throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		String resultData = restTemplate.getForObject(RECOVERED_URL, String.class);
		recoveredStats = parseData(resultData);
		updatedDataTime();
	}
	
	public List<LocationStats> getRecoveredStats() {
		return recoveredStats;
	}
	
	/* Method that parse the csv files using commons-csv dependency */
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
	
	public static String getRecoveredDataTime() {
		return recoveredDataTime;
	}
	
	public void updatedDataTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YY HH:mm:ss");
		recoveredDataTime = dateFormat.format(new Date());
		log.info("Recovered data updated on {}", recoveredDataTime);
	}

}
