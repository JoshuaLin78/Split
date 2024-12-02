package use_cases.write_off_debt;

public class WriteOffDebtInteractor implements WriteOffDebtInputBoundary {
    private final WriteOffDebtDebtorDataAccessInterface debtorDataAccessInterface;
    private final WriteOffDebtOutputBoundary userPresenter;

    public WriteOffDebtInteractor(WriteOffDebtDebtorDataAccessInterface debtorDataAccessInterface,
                                      WriteOffDebtOutputBoundary writeOffDebtOutputData) {
        this.debtorDataAccessInterface = debtorDataAccessInterface;
        this.userPresenter = writeOffDebtOutputData;
    }

    /**
     * Confirms the bill details and saves the data.
     * @param writeOffDebtInputData the input data for write off debt use case
     */
    @Override
    public void execute(WriteOffDebtInputData writeOffDebtInputData) {
        debtorDataAccessInterface.writeOffDebt(writeOffDebtInputData.getDebtor(), writeOffDebtInputData.getAmount());
        final WriteOffDebtOutputData writeOffDebtOutputData =
                new WriteOffDebtOutputData(debtorDataAccessInterface.getAll());
        userPresenter.prepareSuccessView(writeOffDebtOutputData);
    }
}

