package use_cases.bill_confirmation;

public interface BillConfirmationInputBoundary {

    /**
     * Executes the bill confirmation use case
     * @param billConfirmationInputData the input data
     */
    void execute(BillConfirmationInputData billConfirmationInputData);
}
