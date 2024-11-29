package view;

import entity.Debtor;
import interface_adapter.bill_confirmation.BillConfirmationState;
import interface_adapter.check_debtors.CheckDebtorsController;
import interface_adapter.check_debtors.CheckDebtorsState;
import interface_adapter.check_debtors.CheckDebtorsViewModel;
import use_cases.check_debtors.CheckDebtorsInteractor;
import use_cases.check_debtors.CheckDebtorsOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CheckDebtorsView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Check Debtors";

    private final JPanel scrollablePanel;
    private final JScrollPane scrollPane;
    private final JLabel totalLabel;

    private interface_adapter.check_debtors.CheckDebtorsViewModel checkDebtorsViewModel;
    private interface_adapter.check_debtors.CheckDebtorsController checkDebtorsController;

    public CheckDebtorsView(CheckDebtorsViewModel checkDebtorsViewModel) {
        this.checkDebtorsViewModel = checkDebtorsViewModel;
        checkDebtorsViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setSize(800, 600);


        JLabel titleLabel = new JLabel("Check Debtors", SwingConstants.CENTER);
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
        double totalDebt = 0.00;
        for (Debtor debtor : debtors) {
            JLabel nameLabel = new JLabel(debtor.getName(), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
            scrollablePanel.add(nameLabel);

            double debt = debtor.getTotalDebt();
            totalDebt += debt;

            JLabel debtLabel = new JLabel(String.format("$%.2f", debt), SwingConstants.LEFT);
            scrollablePanel.add(debtLabel);
        }

        JLabel totalLabel = new JLabel(String.format("Total Owed: $%.2f", totalDebt), SwingConstants.LEFT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scrollablePanel.add(totalLabel);
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
    }

    public String getViewName() {
        return viewName;
    }

    public void setCheckDebtorsController(CheckDebtorsController checkDebtorsController) {
        this.checkDebtorsController = checkDebtorsController;
    }
}