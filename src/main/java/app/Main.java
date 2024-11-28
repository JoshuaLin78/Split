package app;

import javax.swing.JFrame;

/**
 * The Main class.
 */
public class Main {
    /**
     * Builds and runs the Clean Architecture application
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addBillInputView()
                                            .addBillConfirmationView()
                                            .addBillInputUseCase()
                                            .addBillConfirmationUseCase()
                                            .addCheckDebtorsView()
                                            .addCheckDebtorsUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }

}
