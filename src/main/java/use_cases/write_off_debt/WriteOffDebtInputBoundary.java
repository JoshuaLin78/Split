package use_cases.write_off_debt;

/**
 * The input boundary for the write-off debt use case.
 */
public interface WriteOffDebtInputBoundary {

    /**
     * Executes the write-off debt use case
     * @param writeOffDebtInputData the input data
     */
    void execute(WriteOffDebtInputData writeOffDebtInputData);
}
