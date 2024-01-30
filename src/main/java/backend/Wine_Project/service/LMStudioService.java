package backend.Wine_Project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


@Service
public class LMStudioService {

    private final RestTemplate restTemplate;

    @Autowired
    public LMStudioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callLocalLMStudio(String textToProcess) {
        // Set up the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the request body
        String requestBody = "{\"prompt\": \"" + textToProcess + "\"}";

        // Create an HTTP entity with the headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request to your local LMStudio server
        String lmStudioUrl = "http://localhost:8081/v1/completions"; // Replace YOUR_PORT with the port where your local LMStudio server is running
        return restTemplate.exchange(lmStudioUrl, HttpMethod.POST, requestEntity, String.class).getBody();
    }



}