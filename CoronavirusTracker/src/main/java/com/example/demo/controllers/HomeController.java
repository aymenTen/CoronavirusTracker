package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.models.LocationStats;
import com.example.demo.services.CoronaVirusDataService;

@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataService service;
	
	@GetMapping("/")
	public String home (Model model) {
		List <LocationStats> allStats= service.getAllStats();
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int change = allStats.stream().mapToInt(stat -> stat.getChange()).sum();
		
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("stats", service.getAllStats());
		model.addAttribute("change",change);
		return "home";
	}
}
