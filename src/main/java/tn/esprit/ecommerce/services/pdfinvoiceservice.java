package tn.esprit.ecommerce.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.ecommerce.entities.commande;
import tn.esprit.ecommerce.entities.panier;

import java.io.ByteArrayOutputStream;

@Service
public class pdfinvoiceservice {

    @Autowired
    private JavaMailSender mailSender;

    public byte[] generateInvoicePdf(commande commande, panier panier) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // Création du PDF avec iText 7
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Contenu de la facture
            document.add(new Paragraph("Facture de Commande #" + commande.getId()));
            document.add(new Paragraph("Date de création : " + commande.getDateCreation()));
            document.add(new Paragraph("Client ID : " + panier.getClientId()));
            document.add(new Paragraph("Adresse de livraison : " + commande.getAdresse()));
            document.add(new Paragraph("Méthode de paiement : " + commande.getMethodePaiement()));
            document.add(new Paragraph("Statut : " + commande.getStatut()));
            document.add(new Paragraph("Prix total : " + panier.getPrixTotal() + " TND"));
            document.add(new Paragraph("Merci pour votre achat !"));

            // Fermeture du document
            document.close();

        } catch (Exception e) {
            throw new Exception("Erreur lors de la génération du PDF : " + e.getMessage());
        }

        return baos.toByteArray();
    }

    public void sendInvoiceEmail(commande commande, byte[] pdfBytes) {
        try {
            MimeMessageHelper message = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            message.setTo(commande.getEmail());
            message.setSubject("Votre facture pour la commande #" + commande.getId());
            message.setText("Bonjour,\n\nVeuillez trouver ci-joint la facture de votre commande.\n\nCordialement,\nL'équipe E-Commerce");
            message.addAttachment("facture_commande_" + commande.getId() + ".pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(message.getMimeMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}