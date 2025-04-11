package tn.esprit.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class lignepanier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "panier_id")
    private Long panierId;

    @NotNull
    @Column(name = "produit_id")
    private Long produitId;

    @Positive
    private Integer quantite;

    public lignepanier() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Positive Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(@Positive Integer quantite) {
        this.quantite = quantite;
    }

    public @NotNull Long getProduitId() {
        return produitId;
    }

    public void setProduitId(@NotNull Long produitId) {
        this.produitId = produitId;
    }

    public @NotNull Long getPanierId() {
        return panierId;
    }

    public void setPanierId(@NotNull Long panierId) {
        this.panierId = panierId;
    }

    public lignepanier(Long panierId, Long produitId, Integer quantite) {
        this.panierId = panierId;
        this.produitId = produitId;
        this.quantite = quantite;
    }
}