package tn.esprit.bibliogestioncatalogues.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tn.esprit.bibliogestioncatalogues.DTO.BookEvent;
import tn.esprit.bibliogestioncatalogues.entities.Livre;

@Service
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBookEvent(BookEvent event) {
        System.out.println("Sending BookEvent to Rabbit");
        rabbitTemplate.convertAndSend("bookExchange", "book.created", event);
    }
    public BookEvent toBookEvent(Livre livre) {
        return new BookEvent(
                livre.getIsbn(),
                livre.getTitre(),
                livre.getStockDisponible()
        );
    }
}
