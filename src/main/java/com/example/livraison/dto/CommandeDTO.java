package com.example.livraison.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeDTO {
    private Long id;
    private String adress;
    private Date dateC;
    private String statut;
    private String methodePaiement;
    private String phone;
    private Double prixTotal;
}
