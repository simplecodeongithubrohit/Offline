package com.extraaaz.Offline.services;

import com.extraaaz.Offline.model.CachedData;
import com.extraaaz.Offline.repository.CachedDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.InetAddress;

@Service
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CachedDataRepository cachedDataRepository;

    private static final String BASE_URL = "http://localhost:2007/api";

    private boolean isOnline() {
        try {
            InetAddress address = InetAddress.getByName("google.com");
            return address.isReachable(2000); // Timeout in milliseconds
        } catch (Exception e) {
            return false;
        }
    }

    private String fetchDataFromApi(String endpoint, String requestBody) {
        String url = BASE_URL + endpoint;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            logger.error("HTTP Status Code: {}", e.getStatusCode());
            logger.error("Response Body: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Failed to fetch data from API: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch data from API: " + e.getMessage());
        }
    }

    private void cacheData(String endpoint, String data) {
        CachedData cachedData = new CachedData();
        cachedData.setEndpoint(endpoint);
        cachedData.setResponseData(data);
        cachedDataRepository.save(cachedData);
    }

    private String getCachedData(String endpoint) {
        CachedData cachedData = cachedDataRepository.findByEndpoint(endpoint);
        return cachedData != null ? cachedData.getResponseData() : "No data available";
    }

    public String fetchData(String endpoint, String requestBody) {
        if (isOnline()) {
            try {
                String data = fetchDataFromApi(endpoint, requestBody);
                cacheData(endpoint, data);
                return data;
            } catch (Exception e) {
                logger.error("Error fetching data from API: {}", e.getMessage());
                return getCachedData(endpoint);
            }
        } else {
            return getCachedData(endpoint);
        }
    }
}
