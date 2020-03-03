package com.bryan.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bryan.tracker.model.LocationStats;
import com.bryan.tracker.service.CoronavirusDataService;

@Controller
public class ConfirmedController {
	
	@Autowired
	private CoronavirusDataService service;
	
	static String updatedDataTime;
	
	@GetMapping("/confirmed")
	public String getConfirmed(Model model) {
		List<LocationStats> confirmedStats = service.getConfirmedStats();
		
		int totalConfirmedCases = confirmedStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
		updatedDataTime = service.getUpdatedDataTime();
		
		model.addAttribute("confirmedStats", confirmedStats);
		model.addAttribute("totalConfirmedCases", totalConfirmedCases);
		model.addAttribute("updatedDataTime", updatedDataTime);
		
		return "confirmed";
	}
	
	@GetMapping("/recovered")
	public String getRecovered(Model model) {
		List<LocationStats> recoveredStats = service.getRecoveredStats();
		
		int totalRecoveredCases = recoveredStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
		updatedDataTime = service.getUpdatedDataTime();
		
		model.addAttribute("recoveredStats", recoveredStats);
		model.addAttribute("totalRecoveredCases", totalRecoveredCases);
		model.addAttribute("updatedDataTime", updatedDataTime);
		
		return "recovered";
	}

}
