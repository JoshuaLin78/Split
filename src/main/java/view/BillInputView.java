package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interface_adapter.bill_input.BillInputController;
import api.ImageReader;
import entity.Order;
import interface_adapter.bill_input.BillInputPresenter;
import interface_adapter.bill_input.BillInputState;
import interface_adapter.bill_input.BillInputViewModel;
import use_cases.bill_input.BillInputInputBoundary;
import use_cases.bill_input.BillInputInteractor;
import use_cases.bill_input.BillInputOutputBoundary;
import use_cases.bill_input.MockBillInputInteractor;

public class BillInputView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Bill Input";

    private final JTextField imageNameField;
    //The variable that holds the extracted text from the uploaded image
    private String extractedText;
    private JPanel tablePanel;
    private Map<JTextField, Double> originalPriceMap = new HashMap<>(); // Stores original prices for each priceField
    private JTextField taxField;
    private JTextField tipField;
    private JTextField totalField;

    private BillInputViewModel billInputViewModel;
    private BillInputController billInputController;

    public BillInputView(BillInputViewModel billInputViewModel) {
        this.billInputViewModel = billInputViewModel;
        billInputViewModel.addPropertyChangeListener(this);

        //setTitle("New Bill Entry");
        setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // header
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("New Bill Entry");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton uploadButton = new JButton("Upload Photo");
        imageNameField = new JTextField(15);
        imageNameField.setEditable(false);
        JButton submitButton = new JButton("Submit Bill");

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(BillInputView.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String imageName = fileChooser.getSelectedFile().getName();

                    String extractedText = ImageReader.processImageFile(filePath);

                    imageNameField.setText(imageName);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitBill();
            }
        });

        buttonsPanel.add(uploadButton);
        buttonsPanel.add(imageNameField);
        buttonsPanel.add(submitButton);
        add(buttonsPanel, BorderLayout.NORTH);

        // create the bill table
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0, 5, 5, 5));  // 5 columns including row number and quantity

        // headers for the bill
        JLabel rowNumberLabel = new JLabel("#", SwingConstants.CENTER);
        JLabel itemNameLabel = new JLabel("Item Name", SwingConstants.CENTER);
        JLabel priceLabel = new JLabel("Price", SwingConstants.CENTER);
        JLabel quantityLabel = new JLabel("Quantity", SwingConstants.CENTER);
        JLabel orderedByLabel = new JLabel("Ordered by", SwingConstants.CENTER);

        rowNumberLabel.setFont(new Font("Arial", Font.BOLD, 12));
        itemNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        orderedByLabel.setFont(new Font("Arial", Font.BOLD, 12));

        tablePanel.add(rowNumberLabel);
        tablePanel.add(itemNameLabel);
        tablePanel.add(priceLabel);
        tablePanel.add(quantityLabel);
        tablePanel.add(orderedByLabel);

        // start with 5 rows by default
        for (int i = 1; i <= 5; i++) {
            addRow(i);
        }

        // adds tablePanel to a scroll pane and center it
        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        add(scrollPane, BorderLayout.CENTER);

        // bottom Panel for tax tips, etc.
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centering the bottom panel

        // buttons to manage rows
        JButton addRowButton = new JButton("Add Row");
        JButton removeRowButton = new JButton("Remove Row");

        addRowButton.setPreferredSize(new Dimension(100, 30));
        removeRowButton.setPreferredSize(new Dimension(120, 30));  // Increased width for full text display

        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow(tablePanel.getComponentCount() / 5);  // Calculate row number
                tablePanel.revalidate();
                tablePanel.repaint();
                calculateTotal();
            }
        });

        removeRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRow();
                tablePanel.revalidate();
                tablePanel.repaint();
                calculateTotal();
            }
        });

        bottomPanel.add(addRowButton);
        bottomPanel.add(removeRowButton);

        // tax Field
        JLabel taxLabel = new JLabel("Tax (%)");
        taxField = new JTextField("0", 5);
        taxField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateTotal();
            }
        });

        // tip Field
        JLabel tipLabel = new JLabel("Tip (%)");
        tipField = new JTextField("0", 5);
        tipField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateTotal();
            }
        });

        // total Field
        JLabel totalLabel = new JLabel("Total:");
        totalField = new JTextField("0.00", 10);
        totalField.setEditable(false);

        bottomPanel.add(taxLabel);
        bottomPanel.add(taxField);
        bottomPanel.add(tipLabel);
        bottomPanel.add(tipField);
        bottomPanel.add(totalLabel);
        bottomPanel.add(totalField);

        add(bottomPanel, BorderLayout.SOUTH);

        //setVisible(true);
    }

    private void submitBill() {
        List<Order> orders = new ArrayList<>();
        boolean invalidInputFound = false;

        for (int i = 5; i < tablePanel.getComponentCount(); i += 5) {  // skips the headers
            JTextField itemField = (JTextField) tablePanel.getComponent(i + 1);
            JTextField priceField = (JTextField) tablePanel.getComponent(i + 2);
            JTextField quantityField = (JTextField) ((JPanel) tablePanel.getComponent(i + 3)).getComponent(1);
            JTextField orderedByField = (JTextField) tablePanel.getComponent(i + 4);

            // make sure nothing is empty
            if (itemField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty()) {
                invalidInputFound = true;
                continue;
            }

            try {
                String itemName = itemField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                // split the ordered by text field by commas
                String[] consumers = orderedByField.getText().split(",\\s*");

                orders.add(new Order(itemName, price / quantity, quantity, consumers));
            } catch (NumberFormatException e) {
                invalidInputFound = true;
                continue;
            }
        }

        // warning if user tries to submit bad data
        if (invalidInputFound) {
            JOptionPane.showMessageDialog(this, "Some rows have missing or invalid data and were skipped.", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        double tax = Double.parseDouble(taxField.getText());
        double tip = Double.parseDouble(tipField.getText());
        double total = Double.parseDouble(totalField.getText());

        final BillInputState currentState = billInputViewModel.getState();

        // call controller's execute
        billInputController.execute(orders, tax, tip, total);
    }

    // add a new row for bill
    private void addRow(int rowNum) {
        JLabel rowNumberLabel = new JLabel(String.valueOf(rowNum), SwingConstants.CENTER);
        JTextField itemField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField orderedByField = new JTextField();
        JTextField quantityField = new JTextField("1");  // default quantity to 1
        JButton increaseQuantityButton = new JButton("+");
        JButton decreaseQuantityButton = new JButton("-");

        rowNumberLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        itemField.setPreferredSize(new Dimension(100, 25));
        priceField.setPreferredSize(new Dimension(100, 25));
        orderedByField.setPreferredSize(new Dimension(100, 25));

        // add a focus listener to update the original price when the user leaves the priceField
        priceField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    double enteredPrice = Double.parseDouble(priceField.getText());
                    if (enteredPrice > 0) {
                        originalPriceMap.put(priceField, enteredPrice);  // store the original price
                        quantityField.setText("1");  // reset quantity to 1
                        updatePrice(priceField, quantityField, enteredPrice); // initial calculation with quantity 1
                        calculateTotal();  // recalculate total after setting a new price
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BillInputView.this, "Please enter a valid numeric price.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    priceField.setText(""); // clear the field for a new valid input
                }
            }
        });

        // panel to hold quantity controls
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        quantityPanel.add(decreaseQuantityButton);
        quantityPanel.add(quantityField);
        quantityPanel.add(increaseQuantityButton);

        // action listener for increase quantity button
        increaseQuantityButton.addActionListener(e -> {
            int currentQuantity = Integer.parseInt(quantityField.getText());
            quantityField.setText(String.valueOf(currentQuantity + 1));
            updatePrice(priceField, quantityField, originalPriceMap.getOrDefault(priceField, 0.0));
            calculateTotal();  // Recalculate total after changing quantity
        });

        // action listener for decrease quantity button
        decreaseQuantityButton.addActionListener(e -> {
            int currentQuantity = Integer.parseInt(quantityField.getText());
            if (currentQuantity > 1) {
                quantityField.setText(String.valueOf(currentQuantity - 1));
                updatePrice(priceField, quantityField, originalPriceMap.getOrDefault(priceField, 0.0));
                calculateTotal();  // recalculate total after changing quantity
            }
        });

        tablePanel.add(rowNumberLabel);
        tablePanel.add(itemField);
        tablePanel.add(priceField);
        tablePanel.add(quantityPanel);
        tablePanel.add(orderedByField);
    }

    private void updatePrice(JTextField priceField, JTextField quantityField, double originalPrice) {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            double updatedPrice = originalPrice * quantity;
            priceField.setText(String.format("%.2f", updatedPrice));
        } catch (NumberFormatException ex) {
            priceField.setText("0.00");
        }
    }

    private void calculateTotal() {
        double subtotal = 0;
        for (JTextField priceField : originalPriceMap.keySet()) {
            try {
                subtotal += Double.parseDouble(priceField.getText());
            } catch (NumberFormatException ignored) {
            }
        }

        double tax = parsePercentage(taxField.getText());
        double tip = parsePercentage(tipField.getText());
        double total = subtotal * (1 + tax / 100) * (1 + tip / 100);

        totalField.setText(String.format("%.2f", total));
    }

    private double parsePercentage(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void removeRow() {
        int componentCount = tablePanel.getComponentCount();
        if (componentCount > 10) {
            for (int i = 0; i < 5; i++) {
                Component component = tablePanel.getComponent(componentCount - 1 - i);
                if (component instanceof JTextField && originalPriceMap.containsKey(component)) {
                    originalPriceMap.remove(component);  // Remove from the map
                }
                tablePanel.remove(component);
            }
            calculateTotal();
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        //not implemented message according to lab
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final BillInputState state = (BillInputState) evt.getNewValue();
        //some error message according to lab
    }

    public String getViewName() {
        return viewName;
    }

    public void setBillInputController(BillInputController billInputController) {
        this.billInputController = billInputController;
    }

//    public static void main(String[] args) {
//        //BillInputInputBoundary mockInteractor = new MockBillInputInteractor();
//        BillInputOutputBoundary billInputOutputBoundary = new BillInputPresenter();
//        BillInputInputBoundary billInputInteractor = new BillInputInteractor(billInputOutputBoundary);
//
//        //just replace mockInteractor with the real one for testing
//        //BillInputController controller = new BillInputController(mockInteractor);
//        BillInputController controller = new BillInputController(billInputInteractor);
//        SwingUtilities.invokeLater(() -> new BillInputView(controller));
//    }
}
