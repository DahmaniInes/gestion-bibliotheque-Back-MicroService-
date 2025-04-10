package tn0esprit.gestionbibliothequespringmicroservice.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockReportDTO {
    private int totalBooks;
    private double reservedAvailableRatio;
    private Map<String, Integer> top5MostReserved; // title -> reserved
    private Map<String, Integer> dailyChanges;
    private Map<String, Integer> monthlyChanges;
}
