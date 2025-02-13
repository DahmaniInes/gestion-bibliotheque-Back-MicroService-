package tn0esprit.gestionbibliothequespringmicroservice.Controllers;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn0esprit.gestionbibliothequespringmicroservice.Entity.StockEntity;
import tn0esprit.gestionbibliothequespringmicroservice.Repositories.StockRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping
    public List<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockEntity> getStockById(@PathVariable Long id) {
        Optional<StockEntity> stock = stockRepository.findById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StockEntity createStock(@RequestBody StockEntity stock) {
        return stockRepository.save(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockEntity> updateStock(@PathVariable Long id, @RequestBody StockEntity stockDetails) {
        return stockRepository.findById(id).map(stock -> {
            stock.setBookTitle(stockDetails.getBookTitle());
            stock.setAuthor(stockDetails.getAuthor());
            stock.setQuantity(stockDetails.getQuantity());
            stock.setAvailable(stockDetails.getAvailable());
            stock.setReserved(stockDetails.getReserved());
            return ResponseEntity.ok(stockRepository.save(stock));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStock(@PathVariable Long id) {
        return stockRepository.findById(id).map(stock -> {
            stockRepository.delete(stock);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
