package app;

import java.awt.CardLayout;

import javax.swing.*;

import data_access.InMemoryDebtorDataAccessObject;
import entity.DebtorFactory;
import frameworks.GoogleVisionOCRProcessor;
import frameworks.GsonJsonParser;
import frameworks.OpenAITextOrganizer;
import interface_adapter.ViewManagerModel;
import interface_adapter.bill_confirmation.BillConfirmationController;
import interface_adapter.bill_confirmation.BillConfirmationPresenter;
import interface_adapter.bill_confirmation.BillConfirmationViewModel;
import interface_adapter.bill_input.BillInputController;
import interface_adapter.bill_input.BillInputPresenter;
import interface_adapter.bill_input.BillInputViewModel;
import interface_adapter.bill_summary.BillSummaryController;
import interface_adapter.bill_summary.BillSummaryPresenter;
import interface_adapter.bill_summary.BillSummaryViewModel;
import interface_adapter.check_debtors.CheckDebtorsController;
import interface_adapter.check_debtors.CheckDebtorsPresenter;
import interface_adapter.check_debtors.CheckDebtorsViewModel;
import interface_adapter.file_upload.FileUploadController;
import interface_adapter.file_upload.FileUploadPresenter;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomePresenter;
import interface_adapter.home.HomeViewModel;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapter.view_history.ViewHistoryPresenter;
import interface_adapter.view_history.ViewHistoryViewModel;
import interface_adapter.write_off_debt.WriteOffDebtController;
import interface_adapter.write_off_debt.WriteOffDebtPresenter;
import use_cases.bill_confirmation.BillConfirmationInputBoundary;
import use_cases.bill_confirmation.BillConfirmationInteractor;
import use_cases.bill_confirmation.BillConfirmationOutputBoundary;
import use_cases.bill_input.BillInputInputBoundary;
import use_cases.bill_input.BillInputInteractor;
import use_cases.bill_input.BillInputOutputBoundary;
import use_cases.bill_summary.BillSummaryInputBoundary;
import use_cases.bill_summary.BillSummaryInteractor;
import use_cases.bill_summary.BillSummaryOutputBoundary;
import use_cases.check_debtors.CheckDebtorsInputBoundary;
import use_cases.check_debtors.CheckDebtorsInteractor;
import use_cases.check_debtors.CheckDebtorsOutputBoundary;
import use_cases.file_upload.FileUploadInputBoundary;
import use_cases.file_upload.FileUploadInteractor;
import use_cases.file_upload.FileUploadOutputBoundary;
import use_cases.file_upload.interfaces.JsonParserInterface;
import use_cases.file_upload.interfaces.OCRProcessorInterface;
import use_cases.file_upload.interfaces.TextOrganizerInterface;
import use_cases.home.HomeInputBoundary;
import use_cases.home.HomeInteractor;
import use_cases.home.HomeOutputBoundary;
import use_cases.view_history.ViewHistoryInputBoundary;
import use_cases.view_history.ViewHistoryInteractor;
import use_cases.view_history.ViewHistoryOutputBoundary;
import use_cases.write_off_debt.WriteOffDebtInputBoundary;
import use_cases.write_off_debt.WriteOffDebtInteractor;
import use_cases.write_off_debt.WriteOffDebtOutputBoundary;
import view.*;

/**
 * The AppBuiler class, builds the different parts of the Clean Architecture Split program one by one.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final DebtorFactory debtorFactory = new DebtorFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final InMemoryDebtorDataAccessObject debtorDataAccessObject = new InMemoryDebtorDataAccessObject();

    private HomeView homeView;
    private HomeViewModel homeViewModel;
    private BillInputView billInputView;
    private BillInputViewModel billInputViewModel;
    private BillConfirmationView billConfirmationView;
    private BillConfirmationViewModel billConfirmationViewModel;
    private CheckDebtorsView checkDebtorsView;
    private CheckDebtorsViewModel checkDebtorsViewModel;
    private ViewHistoryView viewHistoryView;
    private ViewHistoryViewModel viewHistoryViewModel;
    private BillSummaryView billSummaryView;
    private BillSummaryViewModel billSummaryViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the HomeView to the application
     * @return this builder
     */
    public AppBuilder addHomeView(){
        homeViewModel = new HomeViewModel();
        homeView = new HomeView(homeViewModel);
        cardPanel.add(homeView, homeView.getViewName());
        return this;
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

    /**
     * Adds the CheckDebtorsView to the application
     * @return this builder
     */
    public AppBuilder addCheckDebtorsView(){
        checkDebtorsViewModel = new CheckDebtorsViewModel();
        checkDebtorsView = new CheckDebtorsView(checkDebtorsViewModel);
        cardPanel.add(checkDebtorsView, checkDebtorsView.getViewName());
        return this;
    }

    /**
     * Adds the ViewHistoryView to the application
     * @return this builder
     */
    public AppBuilder addViewHistoryView(){
        viewHistoryViewModel = new ViewHistoryViewModel();
        viewHistoryView = new ViewHistoryView(viewHistoryViewModel);
        cardPanel.add(viewHistoryView, viewHistoryView.getViewName());
        return this;
    }

    /**
     * Adds the BillSummaryView to the application
     * @return this builder
     */
    public AppBuilder addBillSummaryView(){
        billSummaryViewModel = new BillSummaryViewModel();
        billSummaryView = new BillSummaryView(billSummaryViewModel);
        cardPanel.add(billSummaryView, billSummaryView.getViewName());
        return this;
    }

    /**
     * Adds the HomeUseCase to the application
     * @return this builder
     */
    public AppBuilder addHomeUseCase() {
        final HomeOutputBoundary homeOutputBoundary = new HomePresenter(viewManagerModel,
                homeViewModel, billInputViewModel, checkDebtorsViewModel, viewHistoryViewModel);
        final HomeInputBoundary homeInteractor = new HomeInteractor(homeOutputBoundary);

        final HomeController controller = new HomeController(homeInteractor);
        homeView.setHomeController(controller);
        return this;
    }

    public AppBuilder addFileUploadUseCase() {
        final FileUploadOutputBoundary fileUploadOutputBoundary =
                new FileUploadPresenter(viewManagerModel, billInputViewModel);
        final OCRProcessorInterface ocrProcessor = new GoogleVisionOCRProcessor();
        final TextOrganizerInterface textOrganizer = new OpenAITextOrganizer();
        final JsonParserInterface jsonParser = new GsonJsonParser();
        final FileUploadInputBoundary fileUploadInteractor = new
                FileUploadInteractor(ocrProcessor, textOrganizer, jsonParser, fileUploadOutputBoundary);

        final FileUploadController fileUploadController = new FileUploadController(fileUploadInteractor);
        billInputView.setFileUploadController(fileUploadController);
        return this;
    }

    /**
     * Adds the BillInputUseCase to the application
     * @return this builder
     */
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
     * Adds the BillConfirmationUseCase to the application
     * @return this builder
     */
    public AppBuilder addBillConfirmationUseCase() {
        final BillConfirmationOutputBoundary billConfirmationOutputBoundary =
                new BillConfirmationPresenter(viewManagerModel, billConfirmationViewModel, homeViewModel,
                        checkDebtorsViewModel, billInputViewModel, viewHistoryViewModel);
        final BillConfirmationInputBoundary billConfirmationInteractor = new
                BillConfirmationInteractor(debtorDataAccessObject, billConfirmationOutputBoundary);

        final BillConfirmationController controller = new BillConfirmationController(billConfirmationInteractor);
        billConfirmationView.setBillConfirmationController(controller);
        return this;
    }

    /**
     * Adds the CheckDebtorsUseCase to the application
     * @return this builder
     */
    public AppBuilder addCheckDebtorsUseCase() {
        final CheckDebtorsOutputBoundary checkDebtorsOutputBoundary =
                new CheckDebtorsPresenter(viewManagerModel, checkDebtorsViewModel, homeViewModel);
        final CheckDebtorsInputBoundary checkDebtorsInteractor = new
                CheckDebtorsInteractor(checkDebtorsOutputBoundary);

        final CheckDebtorsController checkDebtorsController = new CheckDebtorsController(checkDebtorsInteractor);
        checkDebtorsView.setCheckDebtorsController(checkDebtorsController);
        return this;
    }

    /**
     * Adds the WriteOffDebtUseCase to the application
     * @return this builder
     */
    public AppBuilder addWriteOffDebtUseCase() {
        final WriteOffDebtOutputBoundary writeOffDebtOutputBoundary =
                new WriteOffDebtPresenter(viewManagerModel, checkDebtorsViewModel);
        final WriteOffDebtInputBoundary writeOffDebtInteractor = new
                WriteOffDebtInteractor(debtorDataAccessObject, writeOffDebtOutputBoundary);

        final WriteOffDebtController writeOffDebtController = new WriteOffDebtController(writeOffDebtInteractor);
        checkDebtorsView.setWriteOffDebtController(writeOffDebtController);
        return this;
    }

    /**
     * Adds the ViewHistoryUseCase to the application
     * @return this builder
     */
    public AppBuilder addViewHistoryUseCase() {
        final ViewHistoryOutputBoundary viewHistoryOutputBoundary =
                new ViewHistoryPresenter(viewManagerModel, homeViewModel, billSummaryViewModel);
        final ViewHistoryInputBoundary viewHistoryInteractor = new
                ViewHistoryInteractor(viewHistoryOutputBoundary);

        final ViewHistoryController viewHistoryController = new ViewHistoryController(viewHistoryInteractor);
        viewHistoryView.setViewHistoryController(viewHistoryController);
        return this;
    }

    /**
     * Adds the BillSummaryUseCase to the application
     * @return this builder
     */
    public AppBuilder addBillSummaryUseCase() {
        final BillSummaryOutputBoundary billSummaryOutputBoundary =
                new BillSummaryPresenter(viewManagerModel, viewHistoryViewModel);
        final BillSummaryInputBoundary billSummaryInteractor = new
                BillSummaryInteractor(billSummaryOutputBoundary);

        final BillSummaryController billSummaryController = new BillSummaryController(billSummaryInteractor);
        billSummaryView.setBillSummaryController(billSummaryController);
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

        viewManagerModel.setState(homeView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
