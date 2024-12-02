package use_cases.bill_confirmation;

import data_access.InMemoryDebtorDataAccessObject;
import entity.Debtor;
import entity.DebtorFactory;
import entity.Order;
import entity.OrderFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillConfirmationInteractorTest {

    @Test
    void successTest(){
        BillConfirmationDebtorDataAccessInterface debtorRepository = new InMemoryDebtorDataAccessObject();

        OrderFactory orderFactory = new OrderFactory();
        Order order = orderFactory.create("Burger", 9.99, 2, new String[]{"Joe", "Bob"});
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        DebtorFactory debtorFactory = new DebtorFactory();
        Debtor debtor1 = debtorFactory.create("Joe", 6.21, 5.00);
        debtorRepository.saveNew(debtor1);
        Debtor debtor2 = debtorFactory.create("Bob", 6.21, 0.00);
        debtorRepository.saveNew(debtor2);
        List<Debtor> debtors= new ArrayList<>();
        debtors.add(debtor1);
        debtors.add(debtor2);

        BillConfirmationInputData inputData = new BillConfirmationInputData(orders, 9.99, 1.13, 1.10, 12.42, debtors);

        BillConfirmationOutputBoundary successPresenter = new BillConfirmationOutputBoundary() {
            @Override
            public void prepareSuccessView(BillConfirmationOutputData outputData) {
                for (Debtor d: outputData.getAllDebtors()) {
                    if (d.getName().equals(debtor1.getName())) {
                        assertEquals(0.00, d.getCurrDebt());
                        assertEquals(11.21, d.getTotalDebt());
                    }
                    if (d.getName().equals(debtor2.getName())) {
                        assertEquals(0.00, d.getCurrDebt());
                        assertEquals(6.21, d.getTotalDebt());
                    }
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {

            }

            /**
             * Returns to Bill Input View for editing.
             */
            @Override
            public void returnToBillInputView() {

            }
        };

        BillConfirmationInputBoundary interactor = new BillConfirmationInteractor(debtorRepository, successPresenter);
        interactor.execute(inputData);
    }
}
