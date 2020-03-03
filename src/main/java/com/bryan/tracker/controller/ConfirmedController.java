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
	
	@GetMapping("/confirmed")
	public String getConfirmed(Model model) {
		List<LocationStats> confirmedStats = service.getConfirmedStats();
		
		int totalReportedCases = confirmedStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
		String updatedDataTime = service.getUpdatedDataTime();
		
		model.addAttribute("confirmedStats", confirmedStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("updatedDataTime", updatedDataTime);
		
		return "confirmed";
	}

}
