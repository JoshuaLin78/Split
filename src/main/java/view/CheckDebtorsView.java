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

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(250, 250, 250)); // Light gray background
        setSize(800, 600);

        // Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(100, 149, 237)); // Cornflower blue

        JLabel titleLabel = new JLabel("View Debtors", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Scrollable Panel
        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        scrollablePanel.setBackground(Color.WHITE);
        scrollablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 1));
        summaryPanel.setOpaque(false);
        totalLabel = new JLabel("Total Owed: $0.00", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(80, 80, 80)); // Medium gray
        summaryPanel.add(totalLabel);

        bottomPanel.add(summaryPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton doneButton = createModernButton("Done");

        doneButton.addActionListener(this);
        buttonPanel.add(doneButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 230, 250)); // Light lavender
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });
        return button;
    }

    private void updateScrollablePanel(List<Debtor> debtors) {
        scrollablePanel.removeAll();

        for (Debtor debtor : debtors) {
            JPanel debtorPanel = new JPanel(new BorderLayout());
            debtorPanel.setOpaque(false);

            JLabel nameLabel = new JLabel(debtor.getName(), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nameLabel.setForeground(new Color(100, 149, 237)); // Blue
            debtorPanel.add(nameLabel, BorderLayout.NORTH);

            JLabel debtLabel = new JLabel(String.format("Owed: $%.2f", debtor.getTotalDebt()), SwingConstants.LEFT);
            debtLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            debtLabel.setForeground(new Color(90, 90, 90)); // Subtle gray
            debtorPanel.add(debtLabel, BorderLayout.CENTER);

            JButton writeOffDebtButton = createModernButton("Write Off Debt");
            writeOffDebtButton.addActionListener(e -> {
                while (true) {
                    try {
                        String input = JOptionPane.showInputDialog("Enter the amount to write off: ");
                        if (input == null) return;

                        double amount = Double.parseDouble(input);
                        if (amount < 0) {
                            JOptionPane.showMessageDialog(null, "Please enter a positive amount.");
                            continue;
                        }
                        writeOffDebtController.execute(debtor, amount);
                        break;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                    }
                }
            });
            debtorPanel.add(writeOffDebtButton, BorderLayout.SOUTH);

            scrollablePanel.add(debtorPanel);
            scrollablePanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator
        }

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