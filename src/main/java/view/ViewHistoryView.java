package view;

import entity.Debtor;
import entity.OrderSummary;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapter.view_history.ViewHistoryState;
import interface_adapter.view_history.ViewHistoryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The view for the View History use case.
 */
public class ViewHistoryView extends JPanel implements ActionListener, PropertyChangeListener {
    private final JPanel scrollablePanel;
    private final JScrollPane scrollPane;
    private final String viewName = "View History";

    private ViewHistoryViewModel viewHistoryViewModel;
    private ViewHistoryController viewHistoryController;

    public ViewHistoryView(ViewHistoryViewModel viewHistoryModel) {
        this.viewHistoryViewModel = viewHistoryModel;
        viewHistoryModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setSize(800, 600);


        JLabel titleLabel = new JLabel("Order History", SwingConstants.CENTER);
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
        JButton doneButton = new JButton("Done");

        doneButton.addActionListener(this);

        buttonPanel.add(doneButton);

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateScrollablePanel(List<OrderSummary> orderSummaries) {
        scrollablePanel.removeAll();
        for (int i = orderSummaries.size() - 1; i >= 0; i--) {
            OrderSummary order = orderSummaries.get(i);
            JLabel nameLabel = new JLabel("Bill " + String.valueOf(i + 1), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
            scrollablePanel.add(nameLabel);

            double total = order.getTotal();

            JLabel totalLabel = new JLabel(String.format("$%.2f", total), SwingConstants.LEFT);
            scrollablePanel.add(totalLabel);

            JButton viewOrderButton = new JButton("View");

            viewOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewHistoryController.orderView(order);
                }
            });

            scrollablePanel.add(viewOrderButton);
        }

        scrollablePanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        scrollablePanel.revalidate();
        scrollablePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if ("Done".equals(evt.getActionCommand())) {
            viewHistoryController.returnHome();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewHistoryState state = (ViewHistoryState) evt.getNewValue();
        updateScrollablePanel(state.getOrderSummaries());
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewHistoryController(ViewHistoryController viewHistoryController) {
        this.viewHistoryController = viewHistoryController;
    }

}
