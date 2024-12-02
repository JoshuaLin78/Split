package use_cases.write_off_debt;


import data_access.InMemoryDebtorDataAccessObject;
import entity.Debtor;
import entity.DebtorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WriteOffDebtInteractorTest {

    @Test
    void successTest(){
        WriteOffDebtDebtorDataAccessInterface debtorRepository = new InMemoryDebtorDataAccessObject();
        DebtorFactory debtorFactory = new DebtorFactory();
        Debtor debtor = debtorFactory.create("Joe", 0.00, 5.00);
        debtorRepository.saveNew(debtor);
        WriteOffDebtInputData inputData = new WriteOffDebtInputData(debtor, 2.27);

        WriteOffDebtOutputBoundary successPresenter = new WriteOffDebtOutputBoundary() {
            @Override
            public void prepareSuccessView(WriteOffDebtOutputData outputData) {
                for (Debtor d: outputData.getDebtors()) {
                    if (d.getName().equals(debtor.getName())) {
                        assertEquals(2.73, d.getTotalDebt());
                    }
                }
            }

            @Override
            public void prepareFailureView(String errorMessage) {

            }
        };

        WriteOffDebtInputBoundary interactor = new WriteOffDebtInteractor(debtorRepository, successPresenter);
        interactor.execute(inputData);
    }
}
