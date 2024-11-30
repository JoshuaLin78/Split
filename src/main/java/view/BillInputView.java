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
import interface_adapter.bill_input.BillInputController;
import api.ImageReader;
import entity.Order;
import interface_adapter.bill_input.BillInputState;
import interface_adapter.bill_input.BillInputViewModel;

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
        JButton clearButton = new JButton("Clear Bill");

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        BillInputView.this,
                        "Are you sure you want to clear the bill? This action cannot be undone.",
                        "Confirm Clear Bill",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) {
                    clearBillTable(); // Clear the table if the user confirms
                }
            }
        });

        JButton doneButton = new JButton("Return Home");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel parent = (JPanel) BillInputView.this.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "Home");
            }
        });

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

                    // set up the process prompts
                    JDialog progressDialog = new JDialog((Frame) null, "Processing Image", true);
                    progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                    progressDialog.setSize(400, 150);
                    progressDialog.setLocationRelativeTo(BillInputView.this);

                    JPanel progressPanel = new JPanel(new BorderLayout());
                    JLabel progressLabel = new JLabel("Initializing...");
                    JProgressBar progressBar = new JProgressBar(0, 100);
                    progressBar.setValue(0);

                    JButton cancelButton = new JButton("Cancel");
                    progressPanel.add(progressLabel, BorderLayout.NORTH);
                    progressPanel.add(progressBar, BorderLayout.CENTER);
                    progressPanel.add(cancelButton, BorderLayout.SOUTH);
                    progressDialog.add(progressPanel);

                    // set up swing worker
                    SwingWorker<Void, String> worker = new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            try {
                                // Step 1: Initialize (10% progress)
                                publish("Starting image processing...", "10");
                                Thread.sleep(500); // Simulated delay

                                // Step 2: Process Image (30% progress)
                                publish("Extracting text using Google Vision...", "30");
                                String extractedText = ImageReader.processImageFile(filePath);
                                if (isCancelled()) return null;

                                if (extractedText.startsWith("Error")) {
                                    throw new IOException("Failed to process image: " + extractedText);
                                }

                                // Step 3: Organize Text (60% progress)
                                publish("Organizing text with AI...", "60");
                                String organizedText = OrganizeText.callGPT(extractedText);
                                if (isCancelled()) return null;

                                if (organizedText.isEmpty()) {
                                    throw new IOException("Failed to organize text.");
                                }

                                // Step 4: Parse to Array (90% progress)
                                publish("Parsing organized text...", "90");
                                String[][] billInformation = JsonTo2DArray.convertJson(organizedText);
                                if (isCancelled()) return null;

                                if (billInformation.length == 0) {
                                    throw new IOException("No valid bill information found.");
                                }

                                // Step 5: Update Table (simulate 100% with delay)
                                publish("Updating table with bill data...", "100");
                                SwingUtilities.invokeLater(() -> updateTableWithBillData(billInformation));
                                imageNameField.setText(imageName);

                                // Simulated delay to ensure 100% message is visible
                                Thread.sleep(1000);
                            } catch (Exception ex) {
                                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(BillInputView.this,
                                        "Error during processing: " + ex.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE));
                            }
                            return null;
                        }

                        @Override
                        protected void process(List<String> chunks) {
                            // update progress bar and status message with the latest values
                            String latestStatus = chunks.get(0); // status
                            String latestProgress = chunks.get(1); // value of progress bar as a string
                            progressLabel.setText(latestStatus);
                            progressBar.setValue(Integer.parseInt(latestProgress));
                        }

                        @Override
                        protected void done() {
                            progressDialog.dispose();
                            if (isCancelled()) {
                                JOptionPane.showMessageDialog(BillInputView.this, "Operation was canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    };

                    cancelButton.addActionListener(event -> {
                        worker.cancel(true);
                        progressDialog.dispose();
                    });

                    SwingUtilities.invokeLater(() -> progressDialog.setVisible(true));
                    worker.execute();
                }
            }
        });


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitBill();
            }
        });

        buttonsPanel.add(doneButton);
        buttonsPanel.add(uploadButton);
        buttonsPanel.add(imageNameField);
        buttonsPanel.add(submitButton);
        buttonsPanel.add(clearButton);
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
            if (i == 1){
                addRow(i, true);
            }
            addRow(i, false);
        }

        // adds tablePanel to a scroll pane and center it
        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        add(scrollPane, BorderLayout.CENTER);

        // bottom Panel for tax tips, etc.
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // centering the bottom panel

        // buttons to manage rows
        JButton addRowButton = new JButton("Add Row");
        JButton removeRowButton = new JButton("Remove Row");

        addRowButton.setPreferredSize(new Dimension(100, 30));
        removeRowButton.setPreferredSize(new Dimension(120, 30));  // increased width for full text display

        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow(tablePanel.getComponentCount() / 5, false);  // calculate row number
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

    private void updateTableWithBillData(String[][] billInformation) {
        // clear all components in the panel and reset the price map
        tablePanel.removeAll();
        originalPriceMap.clear();


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


        int currentRow = 1;
        for (String[] row : billInformation) {
            String dishName = row[0];
            double basePrice = Double.parseDouble(row[2]);
            int quantity = Integer.parseInt(row[1]);


            JLabel rowNumberLabelRow = new JLabel(String.valueOf(currentRow), SwingConstants.CENTER);
            JTextField itemField = new JTextField(dishName);
            JTextField priceField = new JTextField(String.format("%.2f", basePrice * quantity));
            JTextField quantityField = new JTextField(String.valueOf(quantity));
            JTextField orderedByField = new JTextField();


            JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JButton increaseButton = new JButton("+");
            JButton decreaseButton = new JButton("-");
            quantityPanel.add(decreaseButton);
            quantityPanel.add(quantityField);
            quantityPanel.add(increaseButton);


            increaseButton.addActionListener(e -> updateQuantity(quantityField, priceField, originalPriceMap.getOrDefault(priceField, basePrice), 1));
            decreaseButton.addActionListener(e -> updateQuantity(quantityField, priceField, originalPriceMap.getOrDefault(priceField, basePrice), -1));


            priceField.addFocusListener(new FocusAdapter() {
                private String previousValue = ""; // store the previous value

                @Override
                public void focusGained(FocusEvent e) {
                    // save the current value before editing
                    previousValue = priceField.getText();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    String currentValue = priceField.getText();

                    // only proceed if the value has changed
                    if (!currentValue.equals(previousValue)) {
                        try {
                            double newBasePrice = Double.parseDouble(currentValue);
                            if (newBasePrice <= 0) {
                                throw new IllegalArgumentException("Price must be greater than zero.");
                            }

                            // update the original price map with the new base price
                            originalPriceMap.put(priceField, newBasePrice);

                            // reset quantity to 1 and update the price field based on the new base price
                            quantityField.setText("1");
                            priceField.setText(String.format("%.2f", newBasePrice));

                            // recalculate the total
                            calculateTotal();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(
                                    BillInputView.this,
                                    "Please enter a valid numeric price.",
                                    "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            // restore the previous value in case of invalid input
                            priceField.setText(previousValue);
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(
                                    BillInputView.this,
                                    ex.getMessage(),
                                    "Invalid Price",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            // restore the previous value in case of invalid input
                            priceField.setText(previousValue);
                        }
                    }
                }
            });


            tablePanel.add(rowNumberLabelRow);
            tablePanel.add(itemField);
            tablePanel.add(priceField);
            tablePanel.add(quantityPanel);
            tablePanel.add(orderedByField);


            originalPriceMap.put(priceField, basePrice);
            currentRow++;
        }


        tablePanel.revalidate();
        tablePanel.repaint();


        calculateTotal();
    }



    // helper method to update quantity
    private void updateQuantity(JTextField quantityField, JTextField priceField, double basePrice, int delta) {
        try {
            int quantity = Integer.parseInt(quantityField.getText()) + delta;
            if (quantity > 0) {
                quantityField.setText(String.valueOf(quantity));
                priceField.setText(String.format("%.2f", basePrice * quantity));
                calculateTotal();
            }
        } catch (NumberFormatException e) {
            // handle invalid input gracefully
            JOptionPane.showMessageDialog(null, "Invalid quantity input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitBill() {
        List<Order> orders = new ArrayList<>();
        boolean invalidInputFound = false;

        double subtotal = 0.0;


        for (int i = 5; i < tablePanel.getComponentCount(); i += 5) {
            JTextField itemField = (JTextField) tablePanel.getComponent(i + 1);
            JTextField priceField = (JTextField) tablePanel.getComponent(i + 2);
            JTextField quantityField = (JTextField) ((JPanel) tablePanel.getComponent(i + 3)).getComponent(1);
            JTextField orderedByField = (JTextField) tablePanel.getComponent(i + 4);

            boolean rowInvalid = false;

            // check and highlight empty "Item Name" field
            if (itemField.getText().isEmpty()) {
                itemField.setBackground(Color.PINK);
                rowInvalid = true;
            } else {
                itemField.setBackground(Color.WHITE);
            }

            // check and highlight empty "Price" field
            if (priceField.getText().isEmpty()) {
                priceField.setBackground(Color.PINK);
                rowInvalid = true;
            } else {
                priceField.setBackground(Color.WHITE);
            }

            // check and highlight empty "Ordered By" field
            if (orderedByField.getText().isEmpty()) {
                orderedByField.setBackground(Color.PINK);
                rowInvalid = true;
            } else {
                orderedByField.setBackground(Color.WHITE);
            }

            if (rowInvalid) {
                invalidInputFound = true; // mark row as invalid
                continue; // skip this row
            }

            try {
                String itemName = itemField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                subtotal += price;

                // split the "Ordered By" text field by commas
                String[] consumers = orderedByField.getText().split(",\\s*");

                orders.add(new Order(itemName, price / quantity, quantity, consumers));
            } catch (NumberFormatException e) {
                invalidInputFound = true;
            }
        }

        // warning if invalid data is found
        if (invalidInputFound) {
            JOptionPane.showMessageDialog(this, "Some rows have missing or invalid data. Please fix them before submitting.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // proceed only if all fields are valid
        double tax = Double.parseDouble(taxField.getText());
        double tip = Double.parseDouble(tipField.getText());
        double total = Double.parseDouble(totalField.getText());

        final BillInputState currentState = billInputViewModel.getState();

        billInputController.execute(orders, subtotal, tax, tip, total);
    }

    private void clearBillTable() {
        int componentsPerRow = 5;

        for (Component component : tablePanel.getComponents()) {

            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;

                textField.setText("");
            }
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component subComponent : panel.getComponents()) {
                    if (subComponent instanceof JTextField) {
                        JTextField textField = (JTextField) subComponent;

                        textField.setText("1");
                    }
                }
            }
        }

        originalPriceMap.clear();
        calculateTotal();
        tablePanel.revalidate();
        tablePanel.repaint();
    }


    // add a new row for bill
    private void addRow(int rowNum, boolean greyText) {
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

        if (greyText) {
            orderedByField.setText("Enter 'Me*' for items ordered by you");
            orderedByField.setForeground(Color.GRAY);
            orderedByField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (orderedByField.getText().equals("Enter 'Me' for items ordered by you")) {
                        orderedByField.setText("");
                        orderedByField.setForeground(Color.BLACK); // Set color to black when typing
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (orderedByField.getText().isEmpty()) {
                        orderedByField.setText("Enter 'Me' for items ordered by you");
                        orderedByField.setForeground(Color.GRAY); // Reset to grey if empty
                    }
                }
            }
        );}

        priceField.addFocusListener(new FocusAdapter() {
            private String previousValue = ""; // to store the previous value of the field

            @Override
            public void focusGained(FocusEvent e) {
                // save the current value when the field gains focus
                previousValue = priceField.getText();
            }

            @Override
            public void focusLost(FocusEvent e) {
                String currentValue = priceField.getText();

                // only validate if the value has changed
                if (!currentValue.equals(previousValue)) {
                    try {
                        double enteredPrice = Double.parseDouble(currentValue);

                        // check if the price is negative or zero
                        if (enteredPrice <= 0) {
                            throw new IllegalArgumentException("Price must be greater than zero.");
                        }

                        // valid positive price logic
                        originalPriceMap.put(priceField, enteredPrice); // store the original price
                        quantityField.setText("1"); // reset quantity to 1
                        updatePrice(priceField, quantityField, enteredPrice); // initial calculation with quantity 1
                        calculateTotal(); // recalculate total after setting a new price

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                                BillInputView.this,
                                "Please enter a valid numeric price.",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                        );
                        priceField.setText(""); // clear the field for a new valid input

                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(
                                BillInputView.this,
                                ex.getMessage(), // display specific error message for negative or zero price
                                "Invalid Price",
                                JOptionPane.ERROR_MESSAGE
                        );
                        priceField.setText(""); // clear the field for a new valid input
                    }
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
        double total = subtotal * (1 + (tax / 100)) * (1 + (tip / 100));

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
