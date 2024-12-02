package use_cases.home;

import entity.Debtor;

/**
 * The output data for the home use case.
 */
public class HomeOutputData {
    private final Debtor debtor;

    public HomeOutputData(Debtor debtor) {
        this.debtor = debtor;
    }

    public Debtor getDebtor() {
        return debtor;
    }
}

