package tn.esprit.ecommerce.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class twilioservice {

    private static final Logger logger = LoggerFactory.getLogger(twilioservice.class);

    private final String accountsid;
    private final String authtoken;
    private final String fromphonenumber;

    public twilioservice(
            @Value("${twilio.account-sid}") String accountsid,
            @Value("${twilio.auth-token}") String authtoken,
            @Value("${twilio.phone-number}") String fromphonenumber) {
        this.accountsid = accountsid;
        this.authtoken = authtoken;
        this.fromphonenumber = fromphonenumber;
        Twilio.init(accountsid, authtoken);
    }

    public void sendorderconfirmationsms(String tophonenumber, String orderstatus, Double totalprice) {
        if (tophonenumber == null || tophonenumber.isEmpty()) {
            logger.error("numéro de téléphone non fourni pour l'envoi du sms");
            throw new IllegalArgumentException("numéro de téléphone requis pour l'envoi du sms");
        }
        try {
            String messagebody = String.format(
                    "votre commande a été créée avec succès ! statut : %s, prix total : %.2f TND",
                    orderstatus, totalprice
            );

            Message.creator(
                    new PhoneNumber(tophonenumber),
                    new PhoneNumber(fromphonenumber),
                    messagebody
            ).create();

            logger.info("sms envoyé à {}", tophonenumber);
        } catch (Exception e) {
            logger.error("erreur lors de l'envoi du sms à {} : {}", tophonenumber, e.getMessage());
            throw new RuntimeException("échec de l'envoi du sms : " + e.getMessage());
        }
    }
}