package app;

import java.awt.CardLayout;

import javax.swing.*;

import entity.DebtorFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.bill_confirmation.BillConfirmationController;
import interface_adapter.bill_confirmation.BillConfirmationPresenter;
import interface_adapter.bill_confirmation.BillConfirmationViewModel;
import interface_adapter.bill_input.BillInputController;
import interface_adapter.bill_input.BillInputPresenter;
import interface_adapter.bill_input.BillInputViewModel;
import use_cases.bill_confirmation.BillConfirmationInputBoundary;
import use_cases.bill_confirmation.BillConfirmationInteractor;
import use_cases.bill_confirmation.BillConfirmationOutputBoundary;
import use_cases.bill_input.BillInputInputBoundary;
import use_cases.bill_input.BillInputInteractor;
import use_cases.bill_input.BillInputOutputBoundary;
import view.BillConfirmationView;
import view.BillInputView;
import view.ViewManager;

/**
 * The AppBuiler class, builds the different parts of the Clean Architecture Split program one by one.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final DebtorFactory debtorFactory = new DebtorFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private BillInputView billInputView;
    private BillInputViewModel billInputViewModel;
    private BillConfirmationView billConfirmationView;
    private BillConfirmationViewModel billConfirmationViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the BillInputView to the application
     * @return this builder
     */
    public AppBuilder addBillInputView(){
        billInputViewModel = new BillInputViewModel();
        billInputView = new BillInputView(billInputViewModel);
        cardPanel.add(billInputView, billInputView.getViewName());
        return this;
    }

    public AppBuilder addBillInputUseCase() {
        final BillInputOutputBoundary billInputOutputBoundary = new BillInputPresenter(viewManagerModel,
                billInputViewModel, billConfirmationViewModel);
        final BillInputInputBoundary billInputInteractor = new BillInputInteractor(billInputOutputBoundary,
                debtorFactory);

        final BillInputController controller = new BillInputController(billInputInteractor);
        billInputView.setBillInputController(controller);
        return this;
    }

    /**
     * Adds the BillConfirmationView to the application
     * @return this builder
     */
    public AppBuilder addBillConfirmationView(){
        billConfirmationViewModel = new BillConfirmationViewModel();
        billConfirmationView = new BillConfirmationView(billConfirmationViewModel);
        cardPanel.add(billConfirmationView, billConfirmationView.getViewName());
        return this;
    }

    public AppBuilder addBillConfirmationUseCase() {
        final BillConfirmationOutputBoundary billConfirmationOutputBoundary =
                new BillConfirmationPresenter(viewManagerModel, billConfirmationViewModel);
        final BillConfirmationInputBoundary billConfirmationInteractor = new BillConfirmationInteractor(
                billConfirmationOutputBoundary);

        final BillConfirmationController controller = new BillConfirmationController(billConfirmationInteractor);
        billConfirmationView.setBillConfirmationController(controller);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the BillInput to be displayed.
     * The Dashboard should be the first view to be displayed but that has not been created yet.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Split");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(billInputView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
