package tn.esprit.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Entity
public class lignepanier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "panier_id")
    private Long panierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public lignepanier() {
    }

    public lignepanier(Integer quantite, Long produitId, Long panierId) {
        this.quantite = quantite;
        this.produitId = produitId;
        this.panierId = panierId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Long getPanierId() {
        return panierId;
    }

    public void setPanierId(Long panierId) {
        this.panierId = panierId;
    }

    @Column(name = "produit_id")
    private Long produitId;

    private Integer quantite;
}
