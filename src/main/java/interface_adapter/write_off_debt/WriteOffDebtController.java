package interface_adapter.write_off_debt;

import entity.Debtor;
import use_cases.write_off_debt.WriteOffDebtInputBoundary;
import use_cases.write_off_debt.WriteOffDebtInputData;

/**
 * Controller for the write-off debt use case.
 */
public class WriteOffDebtController {
    private final WriteOffDebtInputBoundary userWriteOffDebtUseCaseInteractor;

    public WriteOffDebtController(WriteOffDebtInputBoundary userWriteOffDebtUseCaseInteractor) {
        this.userWriteOffDebtUseCaseInteractor = userWriteOffDebtUseCaseInteractor;
    }

    /**
     * Executes the write-off debt use case.
     * @param debtor The debtor to write off debt for.
     * @param amount The amount to write off.
     */
    public void execute(Debtor debtor, double amount) {
        final WriteOffDebtInputData writeOffDebtInputData = new WriteOffDebtInputData(debtor, amount);
        userWriteOffDebtUseCaseInteractor.execute(writeOffDebtInputData);
    }
}
