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
            double quantity = order.getQuantity();
            String[] consumers = order.getConsumers();

            JLabel priceLabel = new JLabel(String.format("$%.2f", price), SwingConstants.LEFT);
            JLabel quantityLabel = new JLabel("Quantity: " + String.valueOf(quantity), SwingConstants.LEFT);

            String orderedBy = "Ordered By: " + String.join(", ", consumers);

            JLabel consumerLabel = new JLabel(String.valueOf(orderedBy), SwingConstants.LEFT);
            scrollablePanel.add(priceLabel);
            scrollablePanel.add(quantityLabel);
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
    }

    public String getViewName() {
        return viewName;
    }

    public void setBillSummaryController(BillSummaryController billSummaryController) {
        this.billSummaryController = billSummaryController;
    }

}

