package backend.Wine_Project.service.orderService;

import backend.Wine_Project.converter.OrderConverter;
import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.dto.orderDto.OrderGetDto;

import backend.Wine_Project.exceptions.PDFGeneratorException;
import backend.Wine_Project.exceptions.ShoppingCartAlreadyBeenOrderedException;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.repository.OrderRepository;
import backend.Wine_Project.service.InvoiceGeneratorService;

import backend.Wine_Project.service.clientService.ClientService;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import backend.Wine_Project.util.Messages;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Set;


@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartServiceImp shoppingCartService;

    private final ClientService clientService;

    private final InvoiceGeneratorService invoiceGeneratorService;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository, ShoppingCartServiceImp shoppingCartService, ClientService clientService, InvoiceGeneratorService invoiceGeneratorService) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.clientService = clientService;
        this.invoiceGeneratorService = invoiceGeneratorService;

    }

    @Override
    public List<OrderGetDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderConverter::fromModelToOrderGetDto).toList();
    }

    @Override
    public Long create(OrderCreateDto order) {

        ShoppingCart shoppingCart = shoppingCartService.getById(order.shoppingCartId());

        if (shoppingCart.isOrdered()) {
            throw new ShoppingCartAlreadyBeenOrderedException(Messages.SHOPPING_CART_ALREADY_ORDERED.getMessage());
        }

        Order newOrder = new Order(shoppingCart);
        orderRepository.save(newOrder);


        try {
            newOrder.setPdfContent(generatePDFBytes(printInvoice(shoppingCart)));
            savePDFToFile("src/main/java/backend/Wine_Project/invoices/invoice_"+ newOrder.getId() + ".pdf", generatePDFBytes(printInvoice(shoppingCart)));
        } catch (IOException e) {
            throw new PDFGeneratorException(Messages.PDF_GENERATION_ERROR.getMessage());
        }

        shoppingCartService.closeShoppingCart(shoppingCart);

        orderRepository.save(newOrder);
        return newOrder.getId();

    }

    public String printInvoice(ShoppingCart shoppingCart) {
        Set<Item> itemsShoppingCart = shoppingCart.getItems();
        Iterator<Item> it = itemsShoppingCart.iterator();

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Document document = new Document();

            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            while (it.hasNext()) {
                Item nextItem = it.next();
                document.add(new Paragraph(
                        nextItem.getWine().getName() + "\t" +
                                nextItem.getQuantity() + "\t" +
                                nextItem.getTotalPrice()
                ));
            }

            // Add an empty line before the total
            document.add(Chunk.NEWLINE);

            // Adding total using a PdfPTable to ensure proper formatting
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell("Total:");
            table.addCell(""); // Empty cell
            table.addCell(String.valueOf(shoppingCartService.setTotalAmount(shoppingCart.getItems(), shoppingCart)));
            document.add(table);

            document.close();

            // Assuming you have a method to save the PDF bytes to your database
            // You can adapt this based on your data access layer

            return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Error generating PDF for shopping cart", e);
        }

    }

    /*public String printInvoice(ShoppingCart shoppingCart) {
        String invoiceText = "";
        for (Item item : shoppingCart.getItems()) {
            invoiceText.concat("Wine: "+item.getWine().getName() + " - unit. price: " + item.getWine().getPrice()
                    + " - quantity: " + item.getQuantity() + "\n" + "Total price: " + item.getTotalPrice() + "\n\n");
        }
        String finalText = "Order number: " + shoppingCart.getId() + "\n" + "Client: " + shoppingCart.getClient().getName() + "\n" + "Nif: " + shoppingCart.getClient().getNif()
                + "\n" + "Total Amount: " + shoppingCart.getTotalAmount();

        return invoiceText+finalText;
    }*/





    private byte[] generatePDFBytes(String invoiceContent) throws IOException {
        String sanitizedContent = invoiceContent.replaceAll("[^\\x20-\\x7E]", "").replace("\n", " ").replace("\r", ""); // Replace newline characters

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(sanitizedContent);
            contentStream.endText();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
       // document.save(filePath);
        //savePDFToFile(filePath, byteArrayOutputStream.toString());// Save the document to the specified file path
        document.close();

        return byteArrayOutputStream.toByteArray();
    }


    private void savePDFToFile(String filePath, byte[] pdfBytes) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        String sanitizedContent = new String(pdfBytes, StandardCharsets.UTF_8);
        String sanitizedContent2 = sanitizedContent.replaceAll("[^\\x20-\\x7E]", "").replace("\n", " ").replace("\r", "");


        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.COURIER_BOLD , 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(sanitizedContent2);
            contentStream.endText();
        }

        document.save(filePath); // Save the document to the specified file path
        document.close();

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Long id, OrderGetDto modelUpdateDto) {

    }

    @Override
    public OrderGetDto get(Long id) {
        return null;
    }

}
