package use_cases.bill_confirmation;

import entity.Debtor;

import java.util.List;

public class BillConfirmationInteractor implements BillConfirmationInputBoundary {
    private final BillConfirmationDebtorDataAccessInterface debtorDataAccessInterface;
    private final BillConfirmationOutputBoundary userPresenter;

    public BillConfirmationInteractor(BillConfirmationDebtorDataAccessInterface debtorDataAccessInterface,
                                      BillConfirmationOutputBoundary billConfirmationOutputData) {
        this.debtorDataAccessInterface = debtorDataAccessInterface;
        this.userPresenter = billConfirmationOutputData;
    }

    /**
     * Confirms the bill details and saves the data.
     * @param billConfirmationInputData the input data for bill confirmation
     */
    @Override
    public void execute(BillConfirmationInputData billConfirmationInputData) {
        for (Debtor debtor: billConfirmationInputData.getDebtors()){
            if(debtorDataAccessInterface.existsByName(debtor.getName())){
                debtorDataAccessInterface.update(debtor);
            }
            else{
                debtorDataAccessInterface.saveNew(debtor);
            }
        }

        final BillConfirmationOutputData billConfirmationOutputData = new BillConfirmationOutputData(
                                                                            billConfirmationInputData.getOrders(),
                                                                            billConfirmationInputData.getTax(),
                                                                            billConfirmationInputData.getTip(),
                                                                            billConfirmationInputData.getTotal(),
                                                                            billConfirmationInputData.getDebtors());
        userPresenter.prepareSuccessView(billConfirmationOutputData);
    }

    /**
     * Returns to the bill input view for editing.
     */
    @Override
    public void returnToBillInputView(){
        userPresenter.returnToBillInputView();
    }
}

