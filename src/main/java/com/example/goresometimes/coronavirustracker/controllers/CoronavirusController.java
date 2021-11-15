package com.example.goresometimes.coronavirustracker.controllers;

import com.example.goresometimes.coronavirustracker.models.LocationStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.goresometimes.coronavirustracker.services.CoronavirusDataService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CoronavirusController {

    private final CoronavirusDataService coronavirusDataService;

    @Autowired
    // Injecting service dependency into controller class
    public CoronavirusController(CoronavirusDataService coronavirusDataService){
        this.coronavirusDataService = coronavirusDataService;
    };
//    @GetMapping("data")
//    public ResponseEntity<?> getCoronavirusData(){
//
//    }
}
