package com.noussa.event.Services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.noussa.event.Entities.Evenement;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generateCertificate(Evenement evenement, String participantName) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 1. Création du document
        Document document = new Document(PageSize.A4.rotate()); // Format paysage
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // 2. Ajout du contenu
        addCertificateHeader(document);
        addCertificateBody(document, evenement, participantName);
        addCertificateFooter(document);

        document.close();
        return outputStream.toByteArray();
    }

    private void addCertificateHeader(Document document) throws DocumentException {
        Paragraph header = new Paragraph("ATTESTATION DE PARTICIPATION",
                new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD));
        header.setAlignment(Element.ALIGN_CENTER);
        header.setSpacingAfter(30f);
        document.add(header);
    }

    private void addCertificateBody(Document document, Evenement evenement, String participantName) throws DocumentException {
        // Tableau pour une meilleure mise en page
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(80);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Contenu principal
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 16);
        String contentText = String.format(
                "Ce certificat atteste que %s a participé à l'événement '%s' qui s'est tenu le %s à %s.",
                participantName,
                evenement.getTitle(),
                evenement.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm")),
                evenement.getLocation()
        );

        PdfPCell cell = new PdfPCell(new Phrase(contentText, contentFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(20f);
        table.addCell(cell);

        document.add(table);
    }

    private void addCertificateFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("\n\nFait le " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                new Font(Font.FontFamily.HELVETICA, 12));
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);
    }
}