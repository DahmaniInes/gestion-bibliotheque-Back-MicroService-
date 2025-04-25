package tn.esprit.ecommerce.services;

import org.springframework.stereotype.Service;
import tn.esprit.ecommerce.entities.commande;
import tn.esprit.ecommerce.entities.panier;
import tn.esprit.ecommerce.exceptions.resourcenotfoundexception;
import tn.esprit.ecommerce.repositories.commanderepository;
import tn.esprit.ecommerce.repositories.panierrepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class commandeservice {

    private final commanderepository commanderepository;
    private final panierrepository panierrepository;
    private final twilioservice twilioservice;
    private final pdfinvoiceservice pdfinvoiceservice;

    public commandeservice(
            commanderepository commanderepository,
            panierrepository panierrepository,
            twilioservice twilioservice,
            pdfinvoiceservice pdfinvoiceservice) {
        this.commanderepository = commanderepository;
        this.panierrepository = panierrepository;
        this.twilioservice = twilioservice;
        this.pdfinvoiceservice = pdfinvoiceservice;
    }

    public commande createCommande(commande commande) {
        if (commande.getPhone() == null || commande.getPhone().isEmpty()) {
            throw new IllegalArgumentException("le numéro de téléphone est requis");
        }
        if (commande.getPanierId() == null) {
            throw new IllegalArgumentException("l'id du panier est requis");
        }
        if (commande.getAdresse() == null || commande.getAdresse().isEmpty()) {
            throw new IllegalArgumentException("l'adresse est requise");
        }
        if (commande.getMethodePaiement() == null || commande.getMethodePaiement().isEmpty()) {
            throw new IllegalArgumentException("la méthode de paiement est requise");
        }
        if (commande.getEmail() == null || commande.getEmail().isEmpty()) {
            throw new IllegalArgumentException("l'email est requis"); // Validation ajoutée
        }
        if (commande.getStatut() == null) {
            commande.setStatut(tn.esprit.ecommerce.entities.commande.StatutCommande.EN_ATTENTE);
        }
        if (commande.getDateCreation() == null) {
            commande.setDateCreation(LocalDate.now());
        }

        panier panier = panierrepository.findById(commande.getPanierId())
                .orElseThrow(() -> new resourcenotfoundexception("panier non trouvé avec l'id: " + commande.getPanierId()));

        commande savedCommande = commanderepository.save(commande);

        // Envoi SMS via Twilio
       twilioservice.sendorderconfirmationsms(
                savedCommande.getPhone(),
                savedCommande.getStatut().name(),
                panier.getPrixTotal()
        );

        // Génération et envoi de la facture PDF par email
        byte[] pdfBytes = null;
        try {
            pdfBytes = pdfinvoiceservice.generateInvoicePdf(savedCommande, panier);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        pdfinvoiceservice.sendInvoiceEmail(savedCommande, pdfBytes);

        return savedCommande;
    }

    public List<commande> getAllCommandes() {
        return commanderepository.findAll();
    }

    public Optional<commande> getCommandeById(Long id) {
        return commanderepository.findById(id);
    }

    public commande updateCommande(Long id, commande commandeDetails) {
        commande commande = commanderepository.findById(id)
                .orElseThrow(() -> new resourcenotfoundexception("commande non trouvée avec l'id: " + id));

        if (commandeDetails.getPanierId() != null) {
            panierrepository.findById(commandeDetails.getPanierId())
                    .orElseThrow(() -> new resourcenotfoundexception("panier non trouvé avec l'id: " + commandeDetails.getPanierId()));
            commande.setPanierId(commandeDetails.getPanierId());
        }
        if (commandeDetails.getAdresse() != null && !commandeDetails.getAdresse().isEmpty()) {
            commande.setAdresse(commandeDetails.getAdresse());
        }
        if (commandeDetails.getDateCreation() != null) {
            commande.setDateCreation(commandeDetails.getDateCreation());
        }
        if (commandeDetails.getStatut() != null) {
            commande.setStatut(commandeDetails.getStatut());
        }
        if (commandeDetails.getMethodePaiement() != null && !commandeDetails.getMethodePaiement().isEmpty()) {
            commande.setMethodePaiement(commandeDetails.getMethodePaiement());
        }
        if (commandeDetails.getPhone() != null && !commandeDetails.getPhone().isEmpty()) {
            commande.setPhone(commandeDetails.getPhone());
        }
        if (commandeDetails.getEmail() != null && !commandeDetails.getEmail().isEmpty()) {
            commande.setEmail(commandeDetails.getEmail()); // Mise à jour de l'email
        }

        return commanderepository.save(commande);
    }

    public void deleteCommande(Long id) {
        commande commande = commanderepository.findById(id)
                .orElseThrow(() -> new resourcenotfoundexception("commande non trouvée avec l'id: " + id));
        commanderepository.delete(commande);
    }
}