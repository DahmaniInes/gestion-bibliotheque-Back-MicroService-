package tn0esprit.gestionbibliothequespringmicroservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleBooksResponse {
    @JsonProperty("items")
    private List<BookItem> items;
}

@Getter
@Setter
class BookItem {
    private VolumeInfo volumeInfo;
}

@Getter
@Setter
class VolumeInfo {
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private List<String> categories;
    private String thumbnail;
}
