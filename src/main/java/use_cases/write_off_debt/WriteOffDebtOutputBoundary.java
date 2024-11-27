package use_cases.write_off_debt;

public interface WriteOffDebtOutputBoundary {

    /**
     * Prepares the success view for the WriteOffDebt Use Case
     * @param outputData the output data
     */
    void prepareSuccessView(WriteOffDebtOutputData outputData);

    /**
     * Prepares the failure view for the WriteOffDebt Use Case
     * @param errorMessage the message explaining the error
     */
    void prepareFailureView(String errorMessage);
}
