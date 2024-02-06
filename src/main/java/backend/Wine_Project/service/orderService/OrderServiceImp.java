package backend.Wine_Project.service.orderService;

import backend.Wine_Project.converter.OrderConverter;
import backend.Wine_Project.dto.orderDto.OrderCreateDto;
import backend.Wine_Project.dto.orderDto.OrderGetDto;
import backend.Wine_Project.exceptions.ShoppingCartAlreadyBeenOrderedException;
import backend.Wine_Project.model.Item;
import backend.Wine_Project.model.Order;
import backend.Wine_Project.model.ShoppingCart;
import backend.Wine_Project.repository.OrderRepository;
import backend.Wine_Project.service.shopppingCartService.ShoppingCartServiceImp;
import backend.Wine_Project.util.Messages;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;


@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartServiceImp shoppingCartService;





    @Autowired
    public OrderServiceImp(OrderRepository orderRepository, ShoppingCartServiceImp shoppingCartService) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;


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

        String path = "src/main/java/backend/Wine_Project/invoices/invoice_"+newOrder.getId()+".pdf";
        newOrder.setInvoicePath(path);
        generatePdfInvoice(newOrder);


        shoppingCartService.closeShoppingCart(shoppingCart);

        orderRepository.save(newOrder);
        return newOrder.getId();

    }
    @Override
    public void generatePdfInvoice(Order order) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(order.getInvoicePath()));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        document.open();

        try {
            document.add(new Paragraph(printInvoice(order.getShoppingCart())));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.close();
    }
    @Override
    public String printInvoice(ShoppingCart shoppingCart) {

        String header = "------ Order number: " + shoppingCart.getId() +" ------\n\n\n";

        String invoiceText = "";

        for (Item item : shoppingCart.getItems()) {
            invoiceText = invoiceText.concat( ("-- Wine: "+item.getWine().getName() + "\n" + " \tunit. price: " + item.getWine().getPrice()
                    + "\n" +"\tquantity: " + item.getQuantity() + "  __________________________________________________Total price: " + item.getTotalPrice() + "\n\n"));
        }

        String finalText = "Client: " + shoppingCart.getClient().getName() + "\n" + "Nif: " + shoppingCart.getClient().getNif()
                + "  _____________________________________________Total Amount: " + shoppingCart.getTotalAmount();

        return header + invoiceText +"\n\n\n"+ finalText;
    }

}
