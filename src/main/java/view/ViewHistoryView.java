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

public class ViewHistoryView extends JPanel implements ActionListener, PropertyChangeListener {
    private final JPanel scrollablePanel;
    private final JScrollPane scrollPane;
    private final String viewName = "View History";

    private ViewHistoryViewModel viewHistoryViewModel;
    private ViewHistoryController viewHistoryController;

    public ViewHistoryView(ViewHistoryViewModel viewHistoryModel) {
        this.viewHistoryViewModel = viewHistoryModel;
        viewHistoryModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(250, 250, 250)); // Light gray background
        setSize(800, 600);

        // Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(100, 149, 237)); // Cornflower blue

        JLabel titleLabel = new JLabel("Order History", SwingConstants.CENTER);
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

    private void updateScrollablePanel(List<OrderSummary> orderSummaries) {
        scrollablePanel.removeAll();

        for (int i = orderSummaries.size() - 1; i >= 0; i--) {
            JPanel orderPanel = new JPanel(new BorderLayout());
            orderPanel.setOpaque(false);

            JLabel nameLabel = new JLabel("Bill " + (i + 1), SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nameLabel.setForeground(new Color(100, 149, 237)); // Blue
            orderPanel.add(nameLabel, BorderLayout.NORTH);

            JLabel totalLabel = new JLabel(String.format("Total: $%.2f", orderSummaries.get(i).getTotal()), SwingConstants.LEFT);
            totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            totalLabel.setForeground(new Color(90, 90, 90)); // Subtle gray
            orderPanel.add(totalLabel, BorderLayout.CENTER);

            JButton viewOrderButton = createModernButton("View");
            int finalI = i;
            viewOrderButton.addActionListener(e -> viewHistoryController.orderView(orderSummaries.get(finalI)));
            orderPanel.add(viewOrderButton, BorderLayout.SOUTH);

            scrollablePanel.add(orderPanel);
            scrollablePanel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Separator
        }

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