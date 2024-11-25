package view;

import entity.Debtor;
import entity.DebtorFactory;
import interface_adapter.bill_confirmation.BillConfirmationController;
import interface_adapter.bill_confirmation.BillConfirmationPresenter;
import interface_adapter.bill_confirmation.BillConfirmationState;
import interface_adapter.bill_confirmation.BillConfirmationViewModel;
import use_cases.bill_confirmation.*;


import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class BillConfirmationView extends JFrame implements ActionListener, PropertyChangeListener{
    private final String viewName = "Bill Confirmation";
    private final Object[][] debtorData;
    private DefaultTableModel tableModel;


    private BillConfirmationViewModel billConfirmationViewModel;
    private BillConfirmationController billConfirmationController;

    public BillConfirmationView(Object[][] debtorData) {
//    public BillConfirmationView(
//            BillConfirmationController billConfirmationViewModel) {
        this.debtorData = debtorData;
//        this.billConfirmationViewModel = billConfirmationViewModel;
//        billConfirmationViewModel.addPropertyChangeListener(this);

        setTitle(viewName);
        setSize(800, 600);

        String[] columns = new String[]{"Names", "Amount Owed"};
        this.tableModel = new DefaultTableModel(debtorData, columns);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        //not implemented message according to lab
        if (evt.getSource() instanceof JButton button) {
            if ("Confirm".equals(button.getText())) {
                System.out.println("Confirm");
//                billConfirmationController.confirmAction();
            } else if ("Cancel".equals(button.getText())) {
//                billConfirmationController.cancelAction();
                System.out.println("cancel");
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final BillConfirmationState state = (BillConfirmationState) evt.getNewValue();
        //some error message according to lab
    }

    public String getViewName() {
        return viewName;
    }

    public void setBillConfirmationController(BillConfirmationController billConfirmationController) {
//        this.billConfirmationController = billConfirmationController;
    }

    public static void main(String[] args) {
//        DebtorFactory debtorFactory = new DebtorFactory();
//        List<Debtor> debtorList = new ArrayList<>();>
//        debtorList.add(debtorFactory.create("A", 10.00, 10.00));
//        debtorList.add(debtorFactory.create("B", 20.10, 20.10));
//        debtorList.add(debtorFactory.create("C", 30.00, 30.00));
//
//        BillConfirmationOutputBoundary billConfirmationOutputBoundary  = new BillConfirmationPresenter();
//        BillConfirmationInputBoundary interactor = new BillConfirmationInteractor(billConfirmationOutputBoundary);
//        BillConfirmationController billConfirmationController = new BillConfirmationController(interactor);
//
//        SwingUtilities.invokeLater(() -> new BillConfirmationView(billConfirmationController));

//        //temporary test
        Object[][] test = {
                {"A", 10.23},
                {"B", 12.67},
                {"C", 2.34}
        };
        new BillConfirmationView(test);
    }
}
