package com.example.goresometimes.coronavirustracker.controllers;

import com.example.goresometimes.coronavirustracker.models.LocationStats;
import com.example.goresometimes.coronavirustracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService;

    @GetMapping("data")
    public List<LocationStats> home(Model model) throws IOException, InterruptedException {
        List<LocationStats> allStats = coronavirusDataService.fetchVirus();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return  allStats; // Return template value
    }
}
