package tn0esprit.gestionbibliothequespringmicroservice.Services;

import org.springframework.web.multipart.MultipartFile;
import tn0esprit.gestionbibliothequespringmicroservice.Entity.Livre;

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


}
