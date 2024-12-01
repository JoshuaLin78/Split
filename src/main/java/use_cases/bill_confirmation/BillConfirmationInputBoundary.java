package use_cases.bill_confirmation;

/**
 * Input boundary for actions related to confirming the current bill.
 */
public interface BillConfirmationInputBoundary {

    /**
     * Executes the bill confirmation use case
     * @param billConfirmationInputData the input data
     */
    void execute(BillConfirmationInputData billConfirmationInputData);

    /**
     * Executes the return to the bill input view.
     */
    void returnToBillInputView();
}
