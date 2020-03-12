package com.bryan.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bryan.tracker.model.LocationStats;
import com.bryan.tracker.service.ConfirmedService;

@Controller
public class ConfirmedController {
	
	@Autowired
	private ConfirmedService service;
	
	static String confirmedDataTime;
	
	@GetMapping("/confirmed")
	public String getConfirmed(Model model) {
		List<LocationStats> confirmedStats = service.getConfirmedStats();
		
		int totalConfirmedCases = confirmedStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
		confirmedDataTime = ConfirmedService.getConfirmedDataTime();
		
		model.addAttribute("confirmedStats", confirmedStats);
		model.addAttribute("totalConfirmedCases", totalConfirmedCases);
		model.addAttribute("confirmedDataTime", confirmedDataTime);
		
		return "confirmed";
	}
	
	

}
