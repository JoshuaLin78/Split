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

    public void execute(List<Order> orders, double subtotal, double tax, double tip, double total, List<Debtor> debtors){
        final WriteOffDebtInputData writeOffDebtInputData = new WriteOffDebtInputData(orders, subtotal, tax,
                tip, total, debtors);

        userWriteOffDebtUseCaseInteractor.execute(writeOffDebtInputData);
    }
}
