package com.bryan.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bryan.tracker.model.LocationStats;
import com.bryan.tracker.service.ConfirmedService;
import com.bryan.tracker.service.RecoveredService;

@Controller
public class RecoveredController {

	@Autowired
	private RecoveredService service;

	static String recoveredDataTime;

	@GetMapping("/recovered")
	public String getRecovered(Model model) {
		List<LocationStats> recoveredStats = service.getRecoveredStats();

		int totalRecoveredCases = recoveredStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
		recoveredDataTime = RecoveredService.getRecoveredDataTime();

		model.addAttribute("recoveredStats", recoveredStats);
		model.addAttribute("totalRecoveredCases", totalRecoveredCases);
		model.addAttribute("recoveredDataTime", recoveredDataTime);

		return "recovered";
	}

}
