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

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(250, 250, 250)); // Light gray background
        setSize(800, 600);

        // Header with blue banner
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(100, 149, 237)); // Cornflower blue

        JLabel titleLabel = new JLabel("Bill Confirmation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Scrollable Panel for details
        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        scrollablePanel.setBackground(Color.WHITE);
        scrollablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel for Summary and Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        summaryPanel.setOpaque(false);
        taxLabel = createSummaryLabel("Tax: $0.00");
        tipLabel = createSummaryLabel("Tips: $0.00");
        totalLabel = createSummaryLabel("Grand Total: $0.00");

        summaryPanel.add(taxLabel);
        summaryPanel.add(tipLabel);
        summaryPanel.add(totalLabel);

        bottomPanel.add(summaryPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton confirmButton = createModernButton("Confirm");
        JButton cancelButton = createModernButton("Cancel");

        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createSummaryLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(80, 80, 80)); // Medium gray
        return label;
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

    private void updateScrollablePanel(List<Debtor> debtors, List<Order> orders, double taxPercent, double tipPercent) {
        scrollablePanel.removeAll();

        for (Debtor debtor : debtors) {
            JPanel debtorPanel = new JPanel(new BorderLayout());
            debtorPanel.setOpaque(false);

            // Name Label with blue color
            JLabel nameLabel = new JLabel(debtor.getName(), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nameLabel.setForeground(new Color(100, 149, 237)); // Blue
            debtorPanel.add(nameLabel, BorderLayout.NORTH);

            JPanel ordersPanel = new JPanel();
            ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
            ordersPanel.setOpaque(false);

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
                                String.format("  %s (x%d): $%.2f%s", order.getName(), order.getQuantity(), splitPrice,
                                        splitInfo), SwingConstants.LEFT
                        );
                        orderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        orderLabel.setForeground(new Color(90, 90, 90)); // Subtle gray
                        ordersPanel.add(orderLabel);

                        subtotal += splitPrice;
                    }
                }
            }

            double tax = 1 + (taxPercent / 100);
            double tip = 1 + (tipPercent / 100);
            double total = subtotal * tax * tip;

            JLabel subtotalLabel = new JLabel(String.format("  Subtotal: $%.2f", subtotal), SwingConstants.LEFT);
            JLabel totalLabel = new JLabel(String.format("  Total (with Tax & Tip): $%.2f", total), SwingConstants.LEFT);

            subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            subtotalLabel.setForeground(new Color(100, 100, 100)); // Subtle gray
            totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
            totalLabel.setForeground(new Color(60, 63, 65)); // Dark gray

            ordersPanel.add(subtotalLabel);
            ordersPanel.add(totalLabel);
            ordersPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding

            debtorPanel.add(ordersPanel, BorderLayout.CENTER);
            scrollablePanel.add(debtorPanel);
            scrollablePanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator
        }

        scrollablePanel.revalidate();
        scrollablePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JButton button) {
            if ("Confirm".equals(button.getText())) {
                BillConfirmationState currentState = billConfirmationViewModel.getState();
                billConfirmationController.execute(currentState.getOrders(),
                        currentState.getSubtotal(),
                        currentState.getTax(),
                        currentState.getTip(),
                        currentState.getTotal(),
                        currentState.getDebtors());
            } else if ("Cancel".equals(button.getText())) {
                billConfirmationController.returnToBillInputView();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            BillConfirmationState state = (BillConfirmationState) evt.getNewValue();

            updateScrollablePanel(state.getDebtors(), state.getOrders(), state.getTax(), state.getTip());

            taxLabel.setText(String.format("Tax: $%.2f", state.getSubtotal() * (state.getTax() / 100)));
            tipLabel.setText(String.format("Tips: $%.2f", state.getSubtotal() * (1 + (state.getTax() / 100)) *
                    (state.getTip() / 100)));
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
