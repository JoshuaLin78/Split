package view;

import entity.Debtor;
import entity.Order;
import interface_adapter.write_off_debt.WriteOffDebtController;
import interface_adapter.write_off_debt.WriteOffDebtState;
import interface_adapter.write_off_debt.WriteOffDebtViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class WriteOffDebtView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Write Off Debt";

    private WriteOffDebtViewModel writeOffDebtViewModel;
    private WriteOffDebtController writeOffDebtController;

    public WriteOffDebtView(WriteOffDebtViewModel writeOffDebtViewModel) {
        this.writeOffDebtViewModel = writeOffDebtViewModel;
        writeOffDebtViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setSize(800, 600);


        JLabel titleLabel = new JLabel("Write of Debt", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JButton button) {
            if ("Confirm".equals(button.getText())) {
                WriteOffDebtState currentState =  writeOffDebtViewModel.getState();

            } else if ("Cancel".equals(button.getText())) {

            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }


    public String getViewName() {
        return viewName;
    }

    public void setWriteOffDebtController(WriteOffDebtController writeOffDebtController) {
        this.writeOffDebtController = writeOffDebtController;
    }
}
