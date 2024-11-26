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
     * Confirms the bill details and format and sends the data for the Presenter
     * @param billConfirmationInputData the input data for bill confirmation
     */
    @Override
    public void execute(BillConfirmationInputData billConfirmationInputData) {
//        List<Debtor> debtors = billConfirmationInputData.getDebtors();
//        if (debtors.isEmpty()) {
//            return;
//            //add error message
//        } else {
//            Object[][] debtorArray = debtorsToArray(debtors);
//            BillConfirmationOutputData output = new BillConfirmationOutputData(debtorArray);
//            userPresenter.displayBillConfirmationView(output);
//        }

        for (Debtor debtor: billConfirmationInputData.getDebtors()){
            if(debtorDataAccessInterface.existsByName(debtor.getName())){
                debtorDataAccessInterface.update(debtor);
            }
            else{
                debtorDataAccessInterface.saveNew(debtor);
            }
        }
    }

    private static Object[][] debtorsToArray(List<Debtor> debtors) {
        Object[][] debtorData = new Object[debtors.size()][2];
        for (int i = 0; i < debtors.size(); i++) {
            Debtor debtor = debtors.get(i);
            debtorData[i][0] = debtor.getName();
            debtorData[i][1] = debtor.getCurrDebt();
        }
        return debtorData;
    }
}

