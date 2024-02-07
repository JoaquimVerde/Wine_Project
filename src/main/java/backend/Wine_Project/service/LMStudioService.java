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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String requestBody = "{\"prompt\": \"" + textToProcess + "\",\"temp\":0.1,\"n_predict\":200, \"max_tokens\":150}";


        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);


        String lmStudioUrl = "http://localhost:8081/v1/completions";
        return restTemplate.exchange(lmStudioUrl, HttpMethod.POST, requestEntity, String.class).getBody();
    }



}
