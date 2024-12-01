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

        setLayout(new BorderLayout());
        setSize(800, 600);


        JLabel titleLabel = new JLabel("Bill Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel summaryPanel = new JPanel(new GridLayout(1, 1));

        bottomPanel.add(summaryPanel);

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
        JButton doneButton = new JButton("Back");

        doneButton.addActionListener(this);

        buttonPanel.add(doneButton);

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateScrollablePanel(OrderSummary orderSummary) {
        scrollablePanel.removeAll();
        for (Order order : orderSummary.getOrders()) {
            JLabel nameLabel = new JLabel(order.getName(), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
            scrollablePanel.add(nameLabel);

            double price = order.getPrice();
            int quantity = order.getQuantity();
            String[] consumers = order.getConsumers();
            double subtotal = price * quantity;

            JLabel subtotalLabel = new JLabel(order.getName() + " (" + String.format("$%.2f", price) + ")"
                    + " x" + String.valueOf(quantity) + ": " + String.format("$%.2f", subtotal), SwingConstants.LEFT);


            String orderedBy = "Ordered By: " + String.join(", ", consumers);

            JLabel consumerLabel = new JLabel(String.valueOf(orderedBy), SwingConstants.LEFT);
            scrollablePanel.add(subtotalLabel);
            scrollablePanel.add(consumerLabel);
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

