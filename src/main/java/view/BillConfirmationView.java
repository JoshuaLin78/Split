package view;

import entity.Debtor;
import entity.Order;
import interface_adapter.bill_confirmation.BillConfirmationController;
import interface_adapter.bill_confirmation.BillConfirmationState;
import interface_adapter.bill_confirmation.BillConfirmationViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class BillConfirmationView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Bill Confirmation";

    private final JPanel scrollablePanel;
    private final JScrollPane scrollPane;
    private final JLabel taxLabel;
    private final JLabel tipLabel;
    private final JLabel totalLabel;

    private BillConfirmationViewModel billConfirmationViewModel;
    private BillConfirmationController billConfirmationController;

    public BillConfirmationView(BillConfirmationViewModel billConfirmationViewModel) {
        this.billConfirmationViewModel = billConfirmationViewModel;
        billConfirmationViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setSize(800, 600);


        JLabel titleLabel = new JLabel("Bill Confirmation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);


        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3));
        taxLabel = new JLabel("Tax: $0.00", SwingConstants.CENTER);
        tipLabel = new JLabel("Tips: $0.00", SwingConstants.CENTER);
        totalLabel = new JLabel("Grand Total: $0.00", SwingConstants.CENTER);

        taxLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tipLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        summaryPanel.add(taxLabel);
        summaryPanel.add(tipLabel);
        summaryPanel.add(totalLabel);

        bottomPanel.add(summaryPanel);


        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateScrollablePanel(List<Debtor> debtors, List<Order> orders, double taxPercent, double tipPercent) {
        scrollablePanel.removeAll();

        for (Debtor debtor : debtors) {

            JLabel nameLabel = new JLabel(debtor.getName(), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
            scrollablePanel.add(nameLabel);

            double subtotal = 0;


            for (Order order : orders) {
                for (String consumer : order.getConsumers()) {
                    if (consumer.equals(debtor.getName())) {
                        double itemPrice = order.getPrice() * order.getQuantity();
                        double splitPrice = itemPrice / order.getConsumers().length;

                        String splitInfo = (order.getConsumers().length > 1)
                                ? " (Shared between " + String.join(", ", order.getConsumers()) + ")"
                                : "";
                        JLabel orderLabel = new JLabel(
                                String.format("  %s (x%d): $%.2f%s", order.getName(), order.getQuantity(), splitPrice, splitInfo),
                                SwingConstants.LEFT
                        );
                        scrollablePanel.add(orderLabel);

                        subtotal += splitPrice;
                    }
                }
            }


            JLabel subtotalLabel = new JLabel(String.format("  Subtotal: $%.2f", subtotal), SwingConstants.LEFT);
            double tax = subtotal * (taxPercent / 100);
            double tip = subtotal * (tipPercent / 100);
            double total = subtotal + tax + tip;

            JLabel totalLabel = new JLabel(String.format("  Total (with Tax & Tip): $%.2f", total), SwingConstants.LEFT);
            subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            totalLabel.setFont(new Font("Arial", Font.BOLD, 14));

            scrollablePanel.add(subtotalLabel);
            scrollablePanel.add(totalLabel);
            scrollablePanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator between persons
        }

        scrollablePanel.revalidate();
        scrollablePanel.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JButton button) {
            if ("Confirm".equals(button.getText())) {
                System.out.println("Confirmed");
                // Add confirm logic here
            } else if ("Cancel".equals(button.getText())) {
                System.out.println("Cancelled");
                // Add cancel logic here
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            BillConfirmationState state = (BillConfirmationState) evt.getNewValue();


            updateScrollablePanel(state.getDebtors(), state.getOrders(), state.getTax(), state.getTip());


            taxLabel.setText(String.format("Tax: $%.2f", state.getTotal() * (state.getTax() / 100)));
            tipLabel.setText(String.format("Tips: $%.2f", state.getTotal() * (state.getTip() / 100)));
            totalLabel.setText(String.format("Grand Total: $%.2f", state.getTotal()));
        }
    }


    public String getViewName() {
        return viewName;
    }

    public void setBillConfirmationController(BillConfirmationController billConfirmationController) {
        this.billConfirmationController = billConfirmationController;
    }
}
