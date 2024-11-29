package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.JsonTo2DArray;
import api.OrganizeText;
import api.ImageReader;
import entity.Order;
import interface_adapter.home.HomeController;
import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;

public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Home";

    private JPanel tablePanel;
    private JTextField taxField;
    private JTextField tipField;
    private JTextField totalField;

    private HomeViewModel homeViewModel;
    private HomeController homeController;

    public HomeView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        homeViewModel.addPropertyChangeListener(this);

        //setTitle("New Bill Entry");
        setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        // header
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton newBillButton = new JButton("New Bill");
        JButton viewHistoryButton = new JButton("View History");
        JButton viewDebtorsButton = new JButton("View Debtors");

        newBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeController.switchToNewBillView();
            }
        });


        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeController.switchToHistoryView();
            }
        });

        viewDebtorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeController.switchToDebtorsView();
            }
        });

        buttonsPanel.add(newBillButton);
        buttonsPanel.add(viewHistoryButton);
        buttonsPanel.add(viewDebtorsButton);
        add(buttonsPanel, BorderLayout.NORTH);

        //setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        //not implemented message according to lab
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final HomeState state = (HomeState) evt.getNewValue();
        //some error message according to lab
    }

    public String getViewName() {
        return viewName;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}