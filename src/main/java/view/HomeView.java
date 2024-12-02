package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.home.HomeController;
import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;

/**
 * The view for the Home use case.
 */
public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Home";

    private HomeViewModel homeViewModel;
    private HomeController homeController;

    public HomeView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        homeViewModel.addPropertyChangeListener(this);

        setSize(800, 600);
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(250, 250, 250)); // Light gray background

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(100, 149, 237)); // Cornflower blue

        JLabel appTitleLabel = new JLabel("Split (Ver 1)");
        appTitleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        appTitleLabel.setForeground(Color.WHITE);
        appTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(appTitleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerPanel, BorderLayout.NORTH);

        // Center Content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome to Split!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing

        JLabel infoLabel = new JLabel("Manage your bills and debts easily.");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setForeground(new Color(128, 128, 128)); // Subtle gray text
        centerPanel.add(infoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Add spacing

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        buttonsPanel.setOpaque(false);

        JButton newBillButton = createModernButton("New Bill");
        JButton viewHistoryButton = createModernButton("View History");
        JButton viewDebtorsButton = createModernButton("View Debtors");

        newBillButton.addActionListener(e -> homeController.switchToNewBillView());
        viewHistoryButton.addActionListener(e -> homeController.switchToHistoryView());
        viewDebtorsButton.addActionListener(e -> homeController.switchToDebtorsView());

        buttonsPanel.add(newBillButton);
        buttonsPanel.add(viewHistoryButton);
        buttonsPanel.add(viewDebtorsButton);

        centerPanel.add(buttonsPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("Split App © 2024");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setForeground(new Color(180, 180, 180)); // Soft gray

        add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        // Add hover effect
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

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Not implemented message
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final HomeState state = (HomeState) evt.getNewValue();
        // Handle property changes
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
