package use_cases.bill_input;

import entity.Debtor;
import entity.DebtorFactory;
import entity.Order;
import entity.OrderFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillInputInteractorTest {

    @Test
    void successTest(){
        OrderFactory orderFactory = new OrderFactory();
        Order order1 = orderFactory.create("Burger", 8.99, 3, new String[]{"Joe", "Bob", "Me*"});
        Order order2 = orderFactory.create("Chicken Strips", 6.99, 3, new String[]{"Jeff", "Bob"});
        Order order3 = orderFactory.create("Fries", 8.99, 2, new String[]{"Me*"});
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        double subtotal = 0;
        for (Order order: orders) {
            subtotal += order.getPrice() * order.getQuantity();
        }
        double tax = 13;
        double tip = 10;
        double total = subtotal * (1 + tax / 100) * (1 + tip / 100);

        BillInputInputData inputData = new BillInputInputData(orders, subtotal, tax, tip, total);

        BillInputOutputBoundary successPresenter = new BillInputOutputBoundary() {
            @Override
            public void prepareSuccessView(BillInputOutputData outputData) {
                for (Debtor d: outputData.getDebtors()) {
                    double total = 0;
                    for (Order order: orders) {
                        for (String consumer: order.getConsumers()) {
                            if (consumer.equals(d.getName())) {
                                total += order.getPrice() * order.getQuantity() / order.getConsumers().length;
                                break;
                            }
                        }
                    }
                    total = total * (1 + tax / 100) * (1 + tip / 100);
                    total = Math.round(total*Math.pow(10,2))/Math.pow(10,2);
                    assertEquals(total, d.getCurrDebt(), 0.01);
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {

            }
        };

        BillInputInputBoundary interactor = new BillInputInteractor(successPresenter, new DebtorFactory());
        interactor.execute(inputData);
    }
}
