package tn.esprit.bibliogestioncatalogues.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bibliogestioncatalogues.entities.Commentaire;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.entities.Utilisateur;
import tn.esprit.bibliogestioncatalogues.repo.CommentaireRepository;
import tn.esprit.bibliogestioncatalogues.repo.LivreRepository;
import tn.esprit.bibliogestioncatalogues.repositories.UtilisateurRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService implements ILivreService {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    @Override
    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    @Override
    public Livre saveLivre(Livre livre) {
        try {
            System.out.println("Données du livre reçu : " + livre);
            if (livre.getId() != null) {
                System.out.println("Attention : ID fourni (" + livre.getId() + "), il sera ignoré pour la création.");
                livre.setId(null); // Forcer la création d'une nouvelle entité
            }
            Livre savedLivre = livreRepository.save(livre);
            System.out.println("Livre enregistré avec succès : " + savedLivre);
            return savedLivre;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement du livre : " + e.getMessage());
        }
    }

    @Override
    public Livre updateLivre(Long id, Livre updatedLivre) {
        return livreRepository.findById(id).map(livre -> {
            livre.setTitre(updatedLivre.getTitre());
            livre.setIsbn(updatedLivre.getIsbn());
            livre.setAnneePublication(updatedLivre.getAnneePublication());
            livre.setEditeur(updatedLivre.getEditeur());
            livre.setNombrePages(updatedLivre.getNombrePages());
            livre.setResume(updatedLivre.getResume());
            livre.setPrix(updatedLivre.getPrix());
            livre.setStockDisponible(updatedLivre.getStockDisponible());
            livre.setImageCouverture(updatedLivre.getImageCouverture());
            livre.setCategorie(updatedLivre.getCategorie());
            livre.setAuteurs(updatedLivre.getAuteurs());
            return livreRepository.save(livre);
        }).orElse(null);
    }

    @Override
    public void deleteLivre(Long id) {
        livreRepository.deleteById(id);
    }

    @Override
    public ByteArrayInputStream exportLivresToExcel(List<Livre> livres) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Livres");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Titre", "ISBN", "Année", "Éditeur", "Prix", "Stock"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (Livre livre : livres) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(livre.getId());
                row.createCell(1).setCellValue(livre.getTitre());
                row.createCell(2).setCellValue(livre.getIsbn());
                row.createCell(3).setCellValue(livre.getAnneePublication());
                row.createCell(4).setCellValue(livre.getEditeur());
                row.createCell(5).setCellValue(livre.getPrix());
                row.createCell(6).setCellValue(livre.getStockDisponible());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream exportLivresToPdf(List<Livre> livres) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add a title to the PDF
            document.add(new Paragraph("Liste des Livres")
                    .setBold()
                    .setFontSize(16));

            // Define column widths for the table
            float[] columnWidths = {1, 3, 2, 1, 2, 1, 1};
            Table table = new Table(columnWidths);

            // Add table headers
            table.addHeaderCell("ID");
            table.addHeaderCell("Titre");
            table.addHeaderCell("ISBN");
            table.addHeaderCell("Année");
            table.addHeaderCell("Éditeur");
            table.addHeaderCell("Prix");
            table.addHeaderCell("Stock");

            // Add book data to the table
            for (Livre livre : livres) {
                table.addCell(String.valueOf(livre.getId()));
                table.addCell(livre.getTitre() != null ? livre.getTitre() : "");
                table.addCell(livre.getIsbn() != null ? livre.getIsbn() : "");
                table.addCell(String.valueOf(livre.getAnneePublication()));
                table.addCell(livre.getEditeur() != null ? livre.getEditeur() : "");
                table.addCell(String.valueOf(livre.getPrix()));
                table.addCell(String.valueOf(livre.getStockDisponible()));
            }

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            // Return the PDF as a ByteArrayInputStream
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'exportation PDF : " + e.getMessage());
        }
    }

    @Override
    public void importLivresFromExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                Livre livre = new Livre();

                Cell titreCell = row.getCell(1);
                if (titreCell != null) {
                    livre.setTitre(getCellValueAsString(titreCell));
                }

                Cell isbnCell = row.getCell(2);
                if (isbnCell != null) {
                    livre.setIsbn(getCellValueAsString(isbnCell));
                }

                Cell anneeCell = row.getCell(3);
                if (anneeCell != null) {
                    livre.setAnneePublication((int) getCellValueAsNumeric(anneeCell));
                }

                Cell editeurCell = row.getCell(4);
                if (editeurCell != null) {
                    livre.setEditeur(getCellValueAsString(editeurCell));
                }

                Cell prixCell = row.getCell(5);
                if (prixCell != null) {
                    livre.setPrix(getCellValueAsNumeric(prixCell));
                }

                Cell stockCell = row.getCell(6);
                if (stockCell != null) {
                    livre.setStockDisponible((int) getCellValueAsNumeric(stockCell));
                }

                livreRepository.save(livre);
            }
        }
    }

    @Override
    public List<Livre> searchLivres(String titre, String auteur, Long categorieId, Integer anneeMin, Integer anneeMax, Boolean disponible) {
        return null;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default: return "";
        }
    }

    private double getCellValueAsNumeric(Cell cell) {
        if (cell == null) return 0.0;
        switch (cell.getCellType()) {
            case NUMERIC: return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("La cellule doit contenir une valeur numérique : " + cell.getStringCellValue());
                }
            default: throw new IllegalArgumentException("Type de cellule non supporté pour une valeur numérique : " + cell.getCellType());
        }
    }

    @Override
    public Commentaire addCommentaire(Long livreId, Long utilisateurId, Commentaire commentaire) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID : " + livreId));
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + utilisateurId));

        commentaire.setLivre(livre);
        commentaire.setUtilisateur(utilisateur);
        commentaire.setDateCreation(new Date());
        return commentaireRepository.save(commentaire);
    }

    @Override
    public List<Commentaire> getCommentairesByLivre(Long livreId) {
        return commentaireRepository.findByLivreId(livreId);
    }
}