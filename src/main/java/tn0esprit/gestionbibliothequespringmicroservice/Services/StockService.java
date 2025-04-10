package tn0esprit.gestionbibliothequespringmicroservice.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn0esprit.gestionbibliothequespringmicroservice.Entity.StockEntity;
import tn0esprit.gestionbibliothequespringmicroservice.dto.StockReportDTO;
import tn0esprit.gestionbibliothequespringmicroservice.Repositories.StockRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public StockReportDTO generateReport() {
        List<StockEntity> stocks = stockRepository.findAll();

        int totalBooks = stocks.stream().mapToInt(StockEntity::getQuantity).sum();
        int reserved = stocks.stream().mapToInt(StockEntity::getReserved).sum();
        int available = stocks.stream().mapToInt(StockEntity::getAvailable).sum();
        double ratio = available == 0 ? 0 : (double) reserved / available;

        Map<String, Integer> top5Reserved = stocks.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getReserved(), s1.getReserved()))
                .limit(5)
                .collect(Collectors.toMap(
                        StockEntity::getBookTitle,
                        StockEntity::getReserved,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        // Placeholder for daily/monthly changes (if you have logs/history)
        Map<String, Integer> dailyChanges = Map.of("2025-04-09", 12, "2025-04-10", 7);
        Map<String, Integer> monthlyChanges = Map.of("2025-03", 150, "2025-04", 80);

        return new StockReportDTO(totalBooks, ratio, top5Reserved, dailyChanges, monthlyChanges);
    }
}
