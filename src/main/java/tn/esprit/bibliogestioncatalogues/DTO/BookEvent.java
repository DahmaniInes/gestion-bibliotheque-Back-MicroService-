package tn.esprit.bibliogestioncatalogues.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String isbn;
    private String title;
    private int stock;
}