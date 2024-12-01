package view;

import entity.Debtor;
import interface_adapter.bill_confirmation.BillConfirmationState;
import interface_adapter.check_debtors.CheckDebtorsController;
import interface_adapter.check_debtors.CheckDebtorsState;
import interface_adapter.check_debtors.CheckDebtorsViewModel;
import interface_adapter.write_off_debt.WriteOffDebtController;
import use_cases.check_debtors.CheckDebtorsInteractor;
import use_cases.check_debtors.CheckDebtorsOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The view for the Check Debtors use case.
 */
public class CheckDebtorsView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Check Debtors";

    private final JPanel scrollablePanel;
    private final JScrollPane scrollPane;
    private final JLabel totalLabel;

    private CheckDebtorsViewModel checkDebtorsViewModel;
    private CheckDebtorsController checkDebtorsController;
    private WriteOffDebtController writeOffDebtController;

    public CheckDebtorsView(CheckDebtorsViewModel checkDebtorsViewModel) {
        this.checkDebtorsViewModel = checkDebtorsViewModel;
        checkDebtorsViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setSize(800, 600);


        JLabel titleLabel = new JLabel("View Debtors", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);


        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel summaryPanel = new JPanel(new GridLayout(1, 1));

        totalLabel = new JLabel("Total Owed: $0.00", SwingConstants.CENTER);

        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        summaryPanel.add(totalLabel);

        bottomPanel.add(summaryPanel);


        JPanel buttonPanel = new JPanel();
        JButton doneButton = new JButton("Done");

        doneButton.addActionListener(this);

        buttonPanel.add(doneButton);

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateScrollablePanel(List<Debtor> debtors) {
        scrollablePanel.removeAll();
        for (Debtor debtor : debtors) {
            JLabel nameLabel = new JLabel(debtor.getName(), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
            scrollablePanel.add(nameLabel);

            double debt = debtor.getTotalDebt();

            JLabel debtLabel = new JLabel(String.format("$%.2f", debt), SwingConstants.LEFT);
            scrollablePanel.add(debtLabel);

            JButton writeOffDebtButton = new JButton("Write Off Debt");

            writeOffDebtButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    do {
                        try {
                            String input = JOptionPane.showInputDialog("Enter the amount to write off: ");
                            if (input == null) {
                                return;
                            }
                            String inputRounded = String.format("%.2f", Double.parseDouble(input));
                            double amount = Double.parseDouble(inputRounded);
                            if (amount < 0) {
                                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive number.");
                                continue;
                            }
                            writeOffDebtController.execute(debtor, amount);
                            break;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                        }
                    } while (true);
                }
            });

            scrollablePanel.add(writeOffDebtButton);
        }

        scrollablePanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        scrollablePanel.revalidate();
        scrollablePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if ("Done".equals(evt.getActionCommand())) {
            checkDebtorsController.switchView();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CheckDebtorsState state = (CheckDebtorsState) evt.getNewValue();
        updateScrollablePanel(state.getDebtors());
        totalLabel.setText(String.format("Total Owed: $%.2f", state.getTotal()));
    }

    public String getViewName() {
        return viewName;
    }

    public void setCheckDebtorsController(CheckDebtorsController checkDebtorsController) {
        this.checkDebtorsController = checkDebtorsController;
    }

    public void setWriteOffDebtController(WriteOffDebtController writeOffDebtController) {
        this.writeOffDebtController = writeOffDebtController;
    }
}