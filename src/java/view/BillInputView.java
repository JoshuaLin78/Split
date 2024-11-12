package view;

import interface_adapter.bill_input.BillInputController;

import javax.swing.*;

public class BillInputView {
    private BillInputController billInputController;

    public BillInputView() {

    }

    public void setBillInputController(BillInputController billInputController) {
        this.billInputController = billInputController;
    }
}
