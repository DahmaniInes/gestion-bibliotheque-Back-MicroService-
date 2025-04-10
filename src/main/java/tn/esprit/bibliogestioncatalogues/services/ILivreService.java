package tn.esprit.bibliogestioncatalogues.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bibliogestioncatalogues.entities.Livre;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ILivreService {
    List<Livre> getAllLivres();
    Optional<Livre> getLivreById(Long id);
    Livre saveLivre(Livre livre);
    Livre updateLivre(Long id, Livre livre);
    void deleteLivre(Long id);

    ByteArrayInputStream exportLivresToExcel(List<Livre> livres) throws IOException;
    ByteArrayInputStream exportLivresToPdf(List<Livre> livres);
    void importLivresFromExcel(MultipartFile file) throws IOException;
}
