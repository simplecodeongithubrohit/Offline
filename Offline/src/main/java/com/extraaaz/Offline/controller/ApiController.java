package com.extraaaz.Offline.controller;

import com.extraaaz.Offline.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/login")
    public String login(@RequestBody String requestBody) {
        return apiService.fetchData("/login", requestBody);
    }

    @PostMapping("/locations")
    public String addLocation(@RequestBody String requestBody) {
        return apiService.fetchData("/locations", requestBody);
    }

    @PostMapping("/registers")
    public String addRegister(@RequestBody String requestBody) {
        return apiService.fetchData("/registers", requestBody);
    }

    @PostMapping("/daily-registers")
    public String addDailyRegister(@RequestBody String requestBody) {
        return apiService.fetchData("/daily-registers", requestBody);
    }

    @PostMapping("/addSection")
    public String addSection(@RequestBody String requestBody) {
        return apiService.fetchData("/addSection", requestBody);
    }

    @PostMapping("/addArea")
    public String addArea(@RequestBody String requestBody) {
        return apiService.fetchData("/addArea", requestBody);
    }

    @PostMapping("/addFloor")
    public String addFloor(@RequestBody String requestBody) {
        return apiService.fetchData("/addFloor", requestBody);
    }
}
