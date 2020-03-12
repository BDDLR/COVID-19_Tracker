package com.bryan.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bryan.tracker.model.LocationStats;
import com.bryan.tracker.service.DeathService;

@Controller
public class DeathController {
	
	@Autowired
	private DeathService service;
	
	static String deathDataTime;
	
	@GetMapping("/death")
	public String getConfirmed(Model model) {
		List<LocationStats> deathStats = service.getDeathStats();
		
		int totalDeathCases = deathStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
		deathDataTime = DeathService.getConfirmedDataTime();
		
		model.addAttribute("deathStats", deathStats);
		model.addAttribute("totalDeathCases", totalDeathCases);
		model.addAttribute("deathDataTime", deathDataTime);
		
		return "death";
	}

}
