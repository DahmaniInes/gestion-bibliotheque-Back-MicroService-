package tn0esprit.gestionbibliothequespringmicroservice.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn0esprit.gestionbibliothequespringmicroservice.dto.GoogleBooksResponse;

@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate;

    @Value("${google.books.api.key}")
    private String apiKey;


    public GoogleBooksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GoogleBooksResponse searchBooks(String query) {
        String url = String.format("https://www.googleapis.com/books/v1/volumes?q=%s&key=%s", query, apiKey);
        return restTemplate.getForObject(url, GoogleBooksResponse.class);
    }
}
