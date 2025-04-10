package tn0esprit.gestionbibliothequespringmicroservice.Services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import tn0esprit.gestionbibliothequespringmicroservice.dto.StockReportDTO;

import java.io.ByteArrayOutputStream;

@Service
public class PdfReportService {

    public byte[] exportStockReportToPdf(StockReportDTO report) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Stock Report", titleFont));
        document.add(new Paragraph("Total Books: " + report.getTotalBooks(), bodyFont));
        document.add(new Paragraph("Reserved/Available Ratio: " + report.getReservedAvailableRatio(), bodyFont));

        document.add(new Paragraph("Top 5 Most Reserved:", titleFont));
        for (var entry : report.getTop5MostReserved().entrySet()) {
            document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), bodyFont));
        }

        document.add(new Paragraph("Daily Stock Changes:", titleFont));
        for (var entry : report.getDailyChanges().entrySet()) {
            document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), bodyFont));
        }

        document.add(new Paragraph("Monthly Stock Changes:", titleFont));
        for (var entry : report.getMonthlyChanges().entrySet()) {
            document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), bodyFont));
        }

        document.close();
        return out.toByteArray();
    }
}
