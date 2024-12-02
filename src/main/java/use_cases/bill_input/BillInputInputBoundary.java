package use_cases.bill_input;

/**
 * Input boundary for the bill input use case.
 */
public interface BillInputInputBoundary {

    /**
     * Executes the bill input use case
     * @param billInputInputData the input data
     */
    void execute(BillInputInputData billInputInputData);
}
