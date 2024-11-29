package use_cases.home;

import entity.Debtor;

public class HomeOutputData {
    private final Debtor debtor;

    public HomeOutputData(Debtor debtor) {
        this.debtor = debtor;
    }

    public Debtor getDebtor() {
        return debtor;
    }
}

