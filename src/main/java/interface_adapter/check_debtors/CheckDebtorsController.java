package interface_adapter.check_debtors;

import use_cases.check_debtors.CheckDebtorsInputBoundary;

public class CheckDebtorsController {
    public final CheckDebtorsInputBoundary userCheckDebtorsUseCaseInteractor;

    public CheckDebtorsController(CheckDebtorsInputBoundary userCheckDebtorsUseCaseInteractor){
        this.userCheckDebtorsUseCaseInteractor = userCheckDebtorsUseCaseInteractor;
    }

    public void switchView(){userCheckDebtorsUseCaseInteractor.switchView();}
}
