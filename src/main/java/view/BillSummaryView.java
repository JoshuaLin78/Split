package view;

import entity.Order;
import entity.OrderSummary;
import interface_adapter.bill_summary.BillSummaryController;
import interface_adapter.bill_summary.BillSummaryState;
import interface_adapter.bill_summary.BillSummaryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The view for the Bill Summary use case.
 */
public class BillSummaryView extends JPanel implements ActionListener, PropertyChangeListener {
    private final JPanel scrollablePanel;
    private final JScrollPane scrollPane;
    private final String viewName = "Bill Summary";

    private final JLabel taxLabel;
    private final JLabel tipLabel;
    private final JLabel totalLabel;

    private BillSummaryViewModel billSummaryViewModel;
    private BillSummaryController billSummaryController;

    public BillSummaryView(BillSummaryViewModel billSummaryModel) {
        this.billSummaryViewModel = billSummaryModel;
        billSummaryViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(20, 20));
        setSize(800, 600);
        setBackground(new Color(250, 250, 250)); // Light gray background


        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(100, 149, 237)); // Cornflower blue

        JLabel titleLabel = new JLabel("Bill Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);


        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        scrollablePanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);


        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        summaryPanel.setOpaque(false);
        taxLabel = createSummaryLabel("Tax: $0.00");
        tipLabel = createSummaryLabel("Tips: $0.00");
        totalLabel = createSummaryLabel("Grand Total: $0.00");

        summaryPanel.add(taxLabel);
        summaryPanel.add(tipLabel);
        summaryPanel.add(totalLabel);

        bottomPanel.add(summaryPanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton backButton = createModernButton("Back");

        backButton.addActionListener(this);

        buttonPanel.add(backButton);
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

    private void updateScrollablePanel(OrderSummary orderSummary) {
        scrollablePanel.removeAll();

        if (orderSummary == null || orderSummary.getOrders() == null || orderSummary.getOrders().isEmpty()) {
            JLabel emptyLabel = new JLabel("No orders to display.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(150, 150, 150)); // Subtle gray
            scrollablePanel.add(emptyLabel);
        } else {
            for (Order order : orderSummary.getOrders()) {
                JLabel nameLabel = new JLabel(order.getName(), SwingConstants.LEFT);
                nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
                nameLabel.setForeground(new Color(60, 63, 65)); // Dark gray
                scrollablePanel.add(nameLabel);

                double price = order.getPrice();
                int quantity = order.getQuantity();
                String[] consumers = order.getConsumers();
                double subtotal = price * quantity;

                JLabel subtotalLabel = new JLabel(order.getName() + " (" + String.format("$%.2f", price) + ")"
                        + " x" + quantity + ": " + String.format("$%.2f", subtotal), SwingConstants.LEFT);

                subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                subtotalLabel.setForeground(new Color(90, 90, 90)); // Medium gray

                String orderedBy = "Ordered By: " + String.join(", ", consumers);
                JLabel consumerLabel = new JLabel(orderedBy, SwingConstants.LEFT);
                consumerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                consumerLabel.setForeground(new Color(100, 100, 100)); // Subtle gray

                scrollablePanel.add(subtotalLabel);
                scrollablePanel.add(consumerLabel);
            }
        }


        scrollablePanel.add(new JSeparator(SwingConstants.HORIZONTAL));


        scrollablePanel.revalidate();
        scrollablePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if ("Back".equals(evt.getActionCommand())) {
            billSummaryController.goBack();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        BillSummaryState state = (BillSummaryState) evt.getNewValue();
        updateScrollablePanel(state.getOrderSummary());
        taxLabel.setText(String.format("Tax: $%.2f", state.getOrderSummary().getSubtotal() * (state.getOrderSummary().getTax() / 100)));
        tipLabel.setText(String.format("Tips: $%.2f", state.getOrderSummary().getSubtotal() * (1 + (state.getOrderSummary().getTax() / 100)) *
                (state.getOrderSummary().getTip() / 100)));
        totalLabel.setText(String.format("Grand Total: $%.2f", state.getOrderSummary().getTotal()));
    }

    public String getViewName() {
        return viewName;
    }

    public void setBillSummaryController(BillSummaryController billSummaryController) {
        this.billSummaryController = billSummaryController;
    }
}
