package interface_adapter.view_history;

import entity.OrderSummary;
import use_cases.view_history.ViewHistoryInputBoundary;
import use_cases.view_history.ViewHistoryInputData;

public class ViewHistoryController {
    public final ViewHistoryInputBoundary userViewHistoryUseCaseInteractor;

    public ViewHistoryController(ViewHistoryInputBoundary userViewHistoryUseCaseInteractor){
        this.userViewHistoryUseCaseInteractor = userViewHistoryUseCaseInteractor;
    }

    public void orderView(OrderSummary orderSummary){
        ViewHistoryInputData viewHistoryInputData = new ViewHistoryInputData(orderSummary);
        userViewHistoryUseCaseInteractor.orderView(viewHistoryInputData);
    }

    public void returnHome(){
        userViewHistoryUseCaseInteractor.returnHome();
    }
}

