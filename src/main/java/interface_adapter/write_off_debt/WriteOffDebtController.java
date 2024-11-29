package interface_adapter.write_off_debt;

import entity.Debtor;
import entity.Order;
import use_cases.write_off_debt.WriteOffDebtInputBoundary;
import use_cases.write_off_debt.WriteOffDebtInputData;

import java.util.List;

public class WriteOffDebtController {
    private final WriteOffDebtInputBoundary userWriteOffDebtUseCaseInteractor;

    public WriteOffDebtController(WriteOffDebtInputBoundary userWriteOffDebtUseCaseInteractor) {
        this.userWriteOffDebtUseCaseInteractor = userWriteOffDebtUseCaseInteractor;
    }

    public void execute(Debtor debtor, double amount) {
        final WriteOffDebtInputData writeOffDebtInputData = new WriteOffDebtInputData(debtor, amount);

        userWriteOffDebtUseCaseInteractor.execute(writeOffDebtInputData);
    }
}
