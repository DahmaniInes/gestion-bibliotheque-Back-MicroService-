package tn.esprit.bibliogestioncatalogues.services;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.repo.LivreRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService implements ILivreService {

    @Autowired
    private LivreRepository livreRepository;

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
        return livreRepository.save(livre);
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

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream exportLivresToPdf(List<Livre> livres) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Liste des Livres :"));

            for (Livre livre : livres) {
                document.add(new Paragraph("Titre: " + livre.getTitre() + ", ISBN: " + livre.getIsbn()));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void importLivresFromExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Livre livre = new Livre();
                livre.setTitre(row.getCell(0).getStringCellValue());
                livre.setIsbn(row.getCell(1).getStringCellValue());
                livre.setAnneePublication((int) row.getCell(2).getNumericCellValue());
                livre.setEditeur(row.getCell(3).getStringCellValue());
                livre.setPrix(row.getCell(4).getNumericCellValue());
                livre.setStockDisponible((int) row.getCell(5).getNumericCellValue());

                livreRepository.save(livre);
            }
        }
    }
}
