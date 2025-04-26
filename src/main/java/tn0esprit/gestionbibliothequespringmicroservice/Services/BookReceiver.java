package tn0esprit.gestionbibliothequespringmicroservice.Services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tn0esprit.gestionbibliothequespringmicroservice.dto.BookEvent;

@Component
public class BookReceiver {

    @RabbitListener(queues = "bookQueue")
    public void receiveBookEvent(BookEvent event) {
        System.out.println("Received Book: " + event.getTitle());
        // update stock here
    }
}
