package com.example.livraison.feign;

import com.example.livraison.dto.CommandeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "commande-service", url = "http://localhost:8082")

public interface CommandeClient {

    @GetMapping("/api/commandes/{id}")
    CommandeDTO getCommandeById(@PathVariable("id") Long id);
}
