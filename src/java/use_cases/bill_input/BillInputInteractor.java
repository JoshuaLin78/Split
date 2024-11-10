package use_cases.bill_input;

public class BillInputInteractor implements BillInputInputBoundary {
    private final BillInputOutputBoundary userPresenter;

    public BillInputInteractor(BillInputOutputBoundary billInputOutputBoundary) {
        this.userPresenter = billInputOutputBoundary;
    }

    @Override
    public void execute(BillInputInputData billInputInputData){

    }
}
