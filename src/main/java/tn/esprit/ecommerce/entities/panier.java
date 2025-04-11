package tn.esprit.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class panier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "client_id")
    private Long clientId;

    @PositiveOrZero
    @Column(name = "prix_total")
    private Double prixTotal;

    public @NotNull StatutPanier getStatut() {
        return statut;
    }

    public void setStatut(@NotNull StatutPanier statut) {
        this.statut = statut;
    }

    public @PositiveOrZero Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(@PositiveOrZero Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public @NotNull Long getClientId() {
        return clientId;
    }

    public void setClientId(@NotNull Long clientId) {
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatutPanier statut;

    public enum StatutPanier {
        ACTIF, VALIDE, ABANDONNE
    }

    public panier() {}

    public panier(Long clientId, Double prixTotal, StatutPanier statut) {
        this.clientId = clientId;
        this.prixTotal = prixTotal;
        this.statut = statut;
    }
}