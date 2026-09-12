
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

public class View {
    Controller controller;
    LinkedList<String> userInput;

    public View(Controller controller) throws AWTException {
        this.controller = controller;
        userInput = new LinkedList<>();
        menu();
    }

    private void menu() throws AWTException
    {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("CHAT MEMEING");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            // Table with 2 columns
            String[] columns = {"Text"};

            DefaultTableModel model = new DefaultTableModel(columns, 1);

            JTable table = new JTable(model);

            // Allow users to edit cells
            table.setRowHeight(30);

            JScrollPane scrollPane = new JScrollPane(table);

            // Button
            JButton submitButton = new JButton("Submit");

            submitButton.addActionListener(e -> {
                // Commit whatever the user is currently typing
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }

                userInput = new LinkedList<>();

                for (int row = 0; row < table.getRowCount(); row++) {
                    System.err.println(row);
                    Object nameValue = table.getValueAt(row, 0);
                    Object textValue = table.getValueAt(row, 1);
                    System.err.println(nameValue);
                    if (nameValue == null || textValue == null) {
                        continue;
                    }

                    String name = nameValue.toString();
                    String text = textValue.toString();

                    System.out.println("Name: " + name);
                    System.out.println("Text: " + text);

                    userInput.add(name + ": " + text);
                }

                Log.debugInput(userInput, "view");

                try {
                    controller.userSubmittedInput(userInput);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            });

            // Add components
            frame.setLayout(new BorderLayout(10, 10));
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(submitButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });

    }

/*
    private void menu() throws AWTException
    {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("CHAT MEMEING");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            // Table with 2 columns
            String[] columns = {"Name", "Text"};

            DefaultTableModel model = new DefaultTableModel(columns, 5);

            JTable table = new JTable(model);

            // Allow users to edit cells
            table.setRowHeight(30);

            JScrollPane scrollPane = new JScrollPane(table);

            // Button
            JButton submitButton = new JButton("Submit");

            submitButton.addActionListener(e -> {
                // Commit whatever the user is currently typing
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }

                userInput = new LinkedList<>();

                for (int row = 0; row < table.getRowCount(); row++) {
                    System.err.println(row);
                    Object nameValue = table.getValueAt(row, 0);
                    Object textValue = table.getValueAt(row, 1);
                    System.err.println(nameValue);
                    if (nameValue == null || textValue == null) {
                        continue;
                    }

                    String name = nameValue.toString();
                    String text = textValue.toString();

                    System.out.println("Name: " + name);
                    System.out.println("Text: " + text);

                    userInput.add(name + ": " + text);
                }

                Log.debugInput(userInput, "view");

                try {
                    controller.userSubmittedInput(userInput);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            });

            // Add components
            frame.setLayout(new BorderLayout(10, 10));
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(submitButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });

    }
    */
}
