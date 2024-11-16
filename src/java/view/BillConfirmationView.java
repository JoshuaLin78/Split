package view;

import entity.Order;
import interface_adapter.bill_confirmation.BillConfirmationController;
import interface_adapter.bill_confirmation.BillConfirmationState;
import interface_adapter.bill_confirmation.BillConfirmationViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillConfirmationView extends JFrame implements ActionListener, PropertyChangeListener {
    private final String viewName = "Bill Confirmation";

    private BillConfirmationViewModel billConfirmationViewModel;
    private BillConfirmationController billConfirmationController;

    public BillConfirmationView(BillConfirmationViewModel billConfirmationViewModel) {
        this.billConfirmationViewModel = billConfirmationViewModel;
        billConfirmationViewModel.addPropertyChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        //not implemented message according to lab
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
        this.billConfirmationController = billConfirmationController;
    }

//    public static void main(String[] args) {
//        //BillInputInputBoundary mockInteractor = new MockBillInputInteractor();
//        BillInputOutputBoundary billInputOutputBoundary = new BillInputPresenter();
//        BillInputInputBoundary billInputInteractor = new BillInputInteractor(billInputOutputBoundary);
//
//        //just replace mockInteractor with the real one for testing
//        //BillInputController controller = new BillInputController(mockInteractor);
//        BillInputController controller = new BillInputController(billInputInteractor);
//        SwingUtilities.invokeLater(() -> new BillInputView(controller));
//    }
}
