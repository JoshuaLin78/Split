package use_cases.write_off_debt;

public interface WriteOffDebtInputBoundary {

    /**
     * Executes the write-off debt use case
     * @param writeOffDebtInputData the input data
     */
    void execute(WriteOffDebtInputData writeOffDebtInputData);
}
