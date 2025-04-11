package tn.esprit.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long panierId;

    @NotBlank
    private String adresse;

    private LocalDate dateCreation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatutCommande statut;

    @NotBlank
    private String methodePaiement;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public enum StatutCommande {
        EN_ATTENTE, EN_COURS, EXPEDIEE, LIVREE, ANNULEE
    }

    public commande() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank String phone) {
        this.phone = phone;
    }

    public @NotBlank String getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(@NotBlank String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public @NotNull StatutCommande getStatut() {
        return statut;
    }

    public void setStatut(@NotNull StatutCommande statut) {
        this.statut = statut;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public @NotBlank String getAdresse() {
        return adresse;
    }

    public void setAdresse(@NotBlank String adresse) {
        this.adresse = adresse;
    }

    public @NotNull Long getPanierId() {
        return panierId;
    }

    public void setPanierId(@NotNull Long panierId) {
        this.panierId = panierId;
    }

    public commande(Long panierId, String adresse, LocalDate dateCreation, StatutCommande statut,
                    String methodePaiement, String phone) {
        this.panierId = panierId;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.methodePaiement = methodePaiement;
        this.phone = phone;
    }
}