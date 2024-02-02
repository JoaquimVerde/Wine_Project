package backend.Wine_Project.service;


import org.springframework.stereotype.Service;

@Service
public class InvoiceGeneratorService {
/*
    public static void generateInvoice(String filePath, String invoiceContent) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(invoiceContent);
            contentStream.endText();
        }

        document.save(filePath);
        document.close();
    }

 */

}
