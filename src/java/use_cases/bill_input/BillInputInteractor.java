package use_cases.bill_input;

import entity.Debtor;
import entity.DebtorFactory;
import entity.Order;

import java.util.ArrayList;
import java.util.List;

public class BillInputInteractor implements BillInputInputBoundary {
    private final BillInputOutputBoundary userPresenter;
    private final DebtorFactory debtorFactory;

    public BillInputInteractor(BillInputOutputBoundary billInputOutputBoundary, DebtorFactory debtorFactory) {
        this.userPresenter = billInputOutputBoundary;
        this.debtorFactory = debtorFactory;
    }

    /**
     * Creates a list of debtors, each with the debt they owe the user from the current bill, and sends it to presenter
     * @param billInputInputData the input data for bill input
     */
    @Override
    public void execute(BillInputInputData billInputInputData){
        List<Debtor> debtors = new ArrayList<>();

        for(Order order: billInputInputData.getOrders()){
            double pricePerPerson = order.getPrice() / order.getConsumers().length * (1 + billInputInputData.getTax() /
                    100) * (1 + billInputInputData.getTip() / 100);
            for(String consumer: order.getConsumers()){
                if (consumer.equals("Me*")){
                    continue;
                }
                boolean newDebtor = true;
                for (Debtor debtor : debtors) {
                    if (debtor.getName().equals(consumer)){
                        newDebtor = false;
                        break;
                    }
                }
                if (newDebtor) {
                    debtors.add(debtorFactory.create(consumer, pricePerPerson, 0));
                }
                else {
                    for (Debtor debtor : debtors) {
                        if (debtor.getName().equals(consumer)) {
                            debtor.addToCurrDebt(pricePerPerson);
                            break;
                        }
                    }
                }
            }
        }

        for (Debtor debtor : debtors) {
            debtor.addToTotalDebt(debtor.getCurrDebt());
        }

        final BillInputOutputData billInputOutputData = new BillInputOutputData(debtors);
        userPresenter.prepareSuccessView(billInputOutputData);
    }
}
