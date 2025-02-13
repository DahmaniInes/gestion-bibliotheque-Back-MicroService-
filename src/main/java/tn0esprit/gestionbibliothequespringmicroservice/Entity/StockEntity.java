package tn0esprit.gestionbibliothequespringmicroservice.Entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookTitle;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int available;

    @Column(nullable = false)
    private int reserved;
}
