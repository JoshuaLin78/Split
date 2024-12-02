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

import interface_adapter.bill_input.BillInputController;
import entity.Order;
import interface_adapter.bill_input.BillInputState;
import interface_adapter.bill_input.BillInputViewModel;
import interface_adapter.check_debtors.CheckDebtorsState;
import interface_adapter.file_upload.FileUploadController;


/**
 * The {@code BillInputView} class represents the user interface for entering and managing bill data in a graphical application.
 * <p>
 * This class provides functionalities for:
 * <ul>
 *     <li>Allowing users to upload an image of a bill, extracting and organizing its text using AI tools.</li>
 *     <li>Manually entering and editing bill details such as item names, quantities, prices, and who ordered each item.</li>
 *     <li>Calculating the subtotal, applying tax and tip percentages, and displaying the total bill amount.</li>
 *     <li>Submitting the processed bill data for further business logic operations via a controller.</li>
 *     <li>Managing and resetting the bill table dynamically, including adding and removing rows.</li>
 * </ul>
 *
 * <p>
 * This class integrates multiple components:
 * <ul>
 *     <li>{@link BillInputController} - Handles user interactions and facilitates communication between the view and backend logic.</li>
 *     <li>{@link BillInputViewModel} - Maintains the state of the bill data and notifies the view of changes.</li>
 *     <li>{@link FileUploadController} - Supports uploading and processing image files of bills.</li>
 *     <li>{@link api.JsonTo2DArray}, {@link api.OrganizeText}, and {@link api.ImageReader} - Utilities for processing images and text data.</li>
 * </ul>
 *
 * <p>
 * Features of the user interface include:
 * <ul>
 *     <li>Interactive buttons for uploading files, adding/removing table rows, and submitting or clearing data.</li>
 *     <li>A dynamic table for entering and displaying bill items, with features for updating quantities and prices in real-time.</li>
 *     <li>Field validation with visual cues (e.g., highlighting invalid inputs) to ensure data integrity.</li>
 *     <li>Intuitive design using modern Swing components and consistent styling for an enhanced user experience.</li>
 * </ul>
 *
 * <p>
 * Implementation Details:
 * <ul>
 *     <li>Extends {@link JPanel} to be embedded in a larger application frame or card layout.</li>
 *     <li>Implements {@link ActionListener} to handle button click events.</li>
 *     <li>Implements {@link PropertyChangeListener} to respond to state changes in the associated view model.</li>
 * </ul>
 *
 * <p>
 * Usage:
 * <ol>
 *     <li>Create an instance of {@code BillInputView} with a {@link BillInputViewModel}.</li>
 *     <li>Set the appropriate controllers ({@link BillInputController}, {@link FileUploadController}).</li>
 *     <li>Embed the view into a parent container and interact with it as part of the application workflow.</li>
 * </ol>
 *
 * @version 1.0
 */
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
    private FileUploadController fileUploadController;

    /**
     * Constructs a new {@code BillInputView} instance, initializing the graphical user interface
     * for managing bill data. This includes setting up the layout, user interaction components,
     * and event listeners for user actions.
     *
     * <p>The constructor performs the following tasks:
     * <ul>
     *     <li>Configures the main layout and size of the panel.</li>
     *     <li>Sets up the header section with a title label.</li>
     *     <li>Initializes buttons for actions such as uploading an image, submitting the bill, and clearing the table.</li>
     *     <li>Creates a dynamic table for entering and displaying bill items, including default rows with interactive controls.</li>
     *     <li>Adds input fields for tax, tip, and total calculations.</li>
     *     <li>Registers the provided {@link BillInputViewModel} to listen for state changes and updates the view accordingly.</li>
     * </ul>
     * </p>
     *
     * <p>Dependencies:
     * This constructor expects a non-null {@link BillInputViewModel} instance to manage and synchronize
     * the state of the bill data.
     * </p>
     *
     * @param billInputViewModel the view model responsible for managing the state of the bill data.
     *                           It must not be {@code null}.
     */
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
        JButton clearButton = createStyledButton("Clear Bill");

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

        JButton doneButton = createStyledButton("Return Home");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel parent = (JPanel) BillInputView.this.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "Home");
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton uploadButton = createStyledButton("Upload Photo");
        imageNameField = createStyledTextField("", 15, "");
        imageNameField.setEditable(false);
        JButton submitButton = createStyledButton("Submit Bill");

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(BillInputView.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String imageName = fileChooser.getSelectedFile().getName();
                    imageNameField.setText(imageName);

                    //FileUploadUseCase
                    fileUploadController.execute(filePath);
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
            if (i == 1) {
                addRow(i, true); // Special row for row 1
            } else {
                addRow(i, false); // Regular rows for others
            }
        }


        // adds tablePanel to a scroll pane and center it
        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        add(scrollPane, BorderLayout.CENTER);

        // bottom Panel for tax tips, etc.
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // centering the bottom panel

        // buttons to manage rows
        JButton addRowButton = createStyledButton("Add Row");
        JButton removeRowButton = createStyledButton("Remove Row");

        addRowButton.setPreferredSize(new Dimension(100, 30));
        removeRowButton.setPreferredSize(new Dimension(120, 30));  // increased width for full text display

        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRowCount = tablePanel.getComponentCount() / 5 - 1; // Subtract 1 for header row
                addRow(currentRowCount + 1, false);
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
        taxField = createStyledTextField("0", 5, "");
        taxField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateTotal();
            }
        });

        // tip Field
        JLabel tipLabel = new JLabel("Tip (%)");
        tipField = createStyledTextField("0", 5, "");
        tipField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateTotal();
            }
        });

        // total Field
        JLabel totalLabel = new JLabel("Total:");
        totalField = createStyledTextField("0.00", 10, "");
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

    /**
     * Updates the bill table with the provided bill data.
     *
     * @param billInformation a 2D array containing the bill data, where each row represents an item,
     *                        including its name, quantity, price, and other relevant details.
     */
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
            JTextField itemField = createStyledTextField(dishName, 0, "");
            JTextField priceField = createStyledTextField(String.format("%.2f", basePrice * quantity), 0 , "");
            JTextField quantityField = createStyledTextField(String.valueOf(quantity),  0, "");
            JTextField orderedByField = createStyledTextField("", 0, "");


            JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JButton increaseButton = createAddButton();
            JButton decreaseButton = createMinusButton();
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



    /**
     * Updates the quantity of an item in the bill table and recalculates its price.
     *
     * @param quantityField the text field displaying the quantity of the item.
     * @param priceField    the text field displaying the price of the item.
     * @param basePrice     the base price of the item (price for a single unit).
     * @param delta         the change in quantity (positive for increase, negative for decrease).
     */
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

    /**
     * Collects the entered bill data, validates the input, and submits it for processing.
     * <p>Validates:
     * <ul>
     *     <li>Item name, price, and ordered-by fields</li>
     *     <li>Subtotal, tax, and tip percentages</li>
     * </ul>
     * Displays a warning if any validation errors are found.
     */
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

    /**
     * Clears all input fields and rows in the bill table, resetting it to the default state.
     * Also clears the stored original prices and recalculates the total to reflect the reset.
     */
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


    /**
     * Adds a new row to the bill table.
     *
     * @param rowNum  the row number to be added.
     * @param greyText whether the "Ordered by" field should display placeholder text in gray.
     */
    private void addRow(int rowNum, boolean greyText) {
        JLabel rowNumberLabel = new JLabel(String.valueOf(rowNum), SwingConstants.CENTER);
        JTextField itemField = createStyledTextField("", 0, "");
        JTextField priceField = createStyledTextField("", 0, "");
        JTextField orderedByField = createStyledTextField("", 0, "");

        JTextField quantityField = createStyledTextField("1", 0, "");  // default quantity to 1
        JButton increaseQuantityButton = createAddButton();
        JButton decreaseQuantityButton = createMinusButton();

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
                                                    if (orderedByField.getText().equals("Enter 'Me*' for items ordered by you")) {
                                                        orderedByField.setText("");
                                                        orderedByField.setForeground(Color.BLACK); // Set color to black when typing
                                                    }
                                                }

                                                @Override
                                                public void focusLost(FocusEvent e) {
                                                    if (orderedByField.getText().isEmpty()) {
                                                        orderedByField.setText("Enter 'Me*' for items ordered by you");
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


    /**
     * Updates the price field for an item based on its quantity and original price.
     *
     * @param priceField    the text field displaying the price of the item.
     * @param quantityField the text field displaying the quantity of the item.
     * @param originalPrice the original price of the item (price for a single unit).
     */
    private void updatePrice(JTextField priceField, JTextField quantityField, double originalPrice) {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            double updatedPrice = originalPrice * quantity;
            priceField.setText(String.format("%.2f", updatedPrice));
        } catch (NumberFormatException ex) {
            priceField.setText("0.00");
        }
    }


    /**
     * Calculates the total bill amount, including the tax and tip percentages.
     * <p>Uses:
     * <ul>
     *     <li>Original prices for each item</li>
     *     <li>Quantities of each item</li>
     *     <li>Tax and tip percentages entered in the fields</li>
     * </ul>
     * Updates the total field with the calculated value.
     */
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

    /**
     * Parses a percentage value from a string input.
     * <p>If the input is invalid or not a number, returns 0.</p>
     *
     * @param text the string representing a percentage value.
     * @return the parsed percentage as a double, or 0 if the input is invalid.
     */
    private double parsePercentage(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Removes the last row from the bill table if there are more than one rows present.
     * <p>Also removes the corresponding entry from the original price map
     * and recalculates the total bill.</p>
     */
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


    /**
     * Creates a styled button with a specified label.
     *
     * @param text the text to display on the button.
     * @return a {@link JButton} styled with font, color, and hover effects.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setPreferredSize(new Dimension(120, 30)); // General button size

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 230, 230)); // Light gray on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE); // Default white background
            }
        });

        return button;
    }


    /**
     * Creates a styled button for increasing the quantity of an item.
     * <p>The button is styled with:
     * <ul>
     *     <li>Light green background</li>
     *     <li>Hover effects</li>
     *     <li>Compact dimensions for consistent layout</li>
     * </ul>
     * </p>
     *
     * @return a {@link JButton} with "+" as the label and consistent styling.
     */
    private JButton createAddButton() {
        JButton button = new JButton("+");
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(200, 255, 200)); // Softer light green
        button.setForeground(Color.BLACK); // Text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setPreferredSize(new Dimension(40, 30)); // Smaller size for compact layout

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 240, 180)); // Subtler green on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 255, 200)); // Revert to softer light green
            }
        });

        return button;
    }

    /**
     * Creates a styled button for decreasing the quantity of an item.
     * <p>The button is styled with:
     * <ul>
     *     <li>Light red background</li>
     *     <li>Hover effects</li>
     *     <li>Compact dimensions for consistent layout</li>
     * </ul>
     * </p>
     *
     * @return a {@link JButton} with "-" as the label and consistent styling.
     */
    private JButton createMinusButton() {
        JButton button = new JButton("-");
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(255, 220, 220)); // Softer light red
        button.setForeground(Color.BLACK); // Text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setPreferredSize(new Dimension(40, 30)); // Smaller size for compact layout

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 200, 200)); // Subtler red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 220, 220)); // Revert to softer light red
            }
        });

        return button;
    }


    /**
     * Creates a styled text field with optional placeholder text.
     *
     * @param text           the initial text to display in the text field.
     * @param columns        the number of columns for the text field.
     * @param placeholderText optional placeholder text to display when the field is empty.
     * @return a {@link JTextField} styled with consistent font and color.
     */
    private JTextField createStyledTextField(String text, int columns, String placeholderText) {
        JTextField textField = new JTextField(text, columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Removes the default border
        textField.setMargin(new Insets(5, 5, 5, 5)); // Adds padding inside the text field
        textField.setBackground(new Color(245, 245, 245)); // Light gray background for a clean look

        // Placeholder text if provided
        if (placeholderText != null && !placeholderText.isEmpty()) {
            textField.setForeground(Color.GRAY);
            textField.setText(placeholderText);
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textField.getText().equals(placeholderText)) {
                        textField.setText("");
                        textField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (textField.getText().isEmpty()) {
                        textField.setForeground(Color.GRAY);
                        textField.setText(placeholderText);
                    }
                }
            });
        }

        return textField;
    }

    /**
     * Handles action events triggered by buttons in the view.
     * <p>Currently not implemented, as individual buttons have their own listeners.</p>
     *
     * @param evt the {@link ActionEvent} triggered by a button click.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        //not implemented message according to lab
    }


    /**
     * Responds to property changes in the {@link BillInputViewModel}.
     *
     * @param evt the event representing the change in property.
     *            The new value is expected to be a {@link BillInputState}.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final BillInputState state = (BillInputState) evt.getNewValue();
        //Populate Table
        updateTableWithBillData(state.getTableData());
        //some error message according to lab
    }

    /**
     * Retrieves the name of this view.
     *
     * @return the name of the view as a {@link String}.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the {@link BillInputController} for handling user actions in this view.
     *
     * @param billInputController the controller to be associated with this view.
     */
    public void setBillInputController(BillInputController billInputController) {
        this.billInputController = billInputController;
    }

    /**
     * Sets the {@link FileUploadController} for handling file uploads in this view.
     *
     * @param fileUploadController the controller to be associated with this view.
     */
    public void setFileUploadController(FileUploadController fileUploadController) {
        this.fileUploadController = fileUploadController;
    }

}