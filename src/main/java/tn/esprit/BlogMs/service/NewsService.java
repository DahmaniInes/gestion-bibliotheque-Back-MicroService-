package tn.esprit.BlogMs.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class NewsService {

    private static final String NEWS_API = "https://newsapi.org/v2/everything?q={query}&apiKey={key}";

    @Value("${newsapi.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<NewsPost> getNewsPosts(String query) {
        try {
            NewsApiResponse response = restTemplate.getForObject(
                    NEWS_API,
                    NewsApiResponse.class,
                    query,
                    apiKey
            );

            if (response == null || response.articles == null) {
                return List.of();
            }

            return response.articles.stream()
                    .filter(article -> article.title != null && !article.title.isEmpty())
                    .map(article -> new NewsPost(
                            article.title,
                            article.content,
                            article.urlToImage
                    ))
                    .limit(5)
                    .toList();

        } catch (Exception e) {
            // Log the error
            System.err.println("Error fetching news: " + e.getMessage());
            return List.of();
        }
    }

    // DTO Classes with proper field names
    public static class NewsApiResponse {
        public List<Article> articles;
    }

    public static class Article {
        public String title;
        public String content;
        public String urlToImage; // Matches NewsAPI JSON field name
    }

    public record NewsPost(String title, String content, String imageUrl) {}
}