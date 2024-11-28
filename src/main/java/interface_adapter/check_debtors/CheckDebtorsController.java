package interface_adapter.check_debtors;

import use_cases.check_debtors.CheckDebtorsInteractor;

public class CheckDebtorsController {
    public final CheckDebtorsInteractor checkDebtorsInteractor;

    public  CheckDebtorsController(CheckDebtorsInteractor checkDebtorsInteractor){
        this.checkDebtorsInteractor = checkDebtorsInteractor;
    }

    public void switchView(){checkDebtorsInteractor.switchView();}
}
