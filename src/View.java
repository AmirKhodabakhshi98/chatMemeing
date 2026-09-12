import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class View {

    Controller controller;
    LinkedList<String> userInput;
    LinkedList<KeyCombo> keyCombos;

    public View(Controller controller) throws AWTException {
        this.controller = controller;
        userInput = new LinkedList<>();
        keyCombos = new LinkedList<>();
        menu();
    }

    private void menu() throws AWTException {

        SwingUtilities.invokeLater(() -> {


            JFrame frame = new JFrame("CHAT MEMEING");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //think 2/3 enough
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            Rectangle screenBounds = ge.getMaximumWindowBounds();

            int windowWidth = (int) (screenBounds.width * 0.66);

            int windowHeight = (int) (screenBounds.height * 0.66);

            frame.setSize(windowWidth, windowHeight);

            //centr
            int x = screenBounds.x + (screenBounds.width - windowWidth) / 2;

            int y = screenBounds.y + (screenBounds.height - windowHeight) / 2;

            frame.setLocation(x, y);


            //användarinstrk

            JTextArea instructionsArea = new JTextArea(UserInstruction.message);

            instructionsArea.setLineWrap(true);
            instructionsArea.setWrapStyleWord(true);

            instructionsArea.setFont(new Font("SansSerif", Font.ITALIC, 16));

            instructionsArea.setForeground(Color.DARK_GRAY);

            instructionsArea.setBackground(new Color(0xF0F0F0));

            instructionsArea.setMargin(new Insets(12, 12, 12, 12));

            instructionsArea.setRows(7);

            JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);

            instructionsScrollPane.setPreferredSize(new Dimension(0, 220));


            //byta delay grejre

            JPanel delayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel delayLabel = new JLabel("Delay before typing (seconds):");

            SpinnerNumberModel delayModel = new SpinnerNumberModel(5, 0, 60, 1);

            JSpinner delaySpinner = new JSpinner(delayModel);

            ((JSpinner.DefaultEditor) delaySpinner.getEditor()).getTextField().setColumns(3);

            delayPanel.add(delayLabel);
            delayPanel.add(delaySpinner);


            JPanel topPanel = new JPanel();

            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

            topPanel.add(instructionsScrollPane);
            topPanel.add(delayPanel);


            //input styuff

            JTextArea textArea = new JTextArea();

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            textArea.setFont(new Font("SansSerif", Font.PLAIN, 18));

            textArea.setMargin(new Insets(10, 10, 10, 10));


            textArea.addKeyListener(new KeyAdapter() {

                int keyCode;

                List<Integer> modifierKeyCodes = new ArrayList<>();

                @Override
                public void keyPressed(KeyEvent e) {

                    keyCode = e.getKeyCode();

                    modifierKeyCodes = new ArrayList<>();

                    if (e.isShiftDown()) {
                        modifierKeyCodes.add(KeyEvent.VK_SHIFT);
                    }

                    if (e.isControlDown()) {
                        modifierKeyCodes.add(KeyEvent.VK_CONTROL);
                    }

                    if (e.isAltDown()) {
                        modifierKeyCodes.add(KeyEvent.VK_ALT);
                    }

                    if (e.isAltGraphDown()) {
                        modifierKeyCodes.add(KeyEvent.VK_ALT_GRAPH);
                    }


                    // deleting not allowed, resety isntead

                    if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_HOME || keyCode == KeyEvent.VK_END || keyCode == KeyEvent.VK_PAGE_UP || keyCode == KeyEvent.VK_PAGE_DOWN) {

                        e.consume();
                        return;
                    }


                    //copy paste prevention

                    if (e.isControlDown()) {

                        e.consume();
                    }
                }


                @Override
                public void keyTyped(KeyEvent e) {

                    char c = e.getKeyChar();


                    if (c == KeyEvent.CHAR_UNDEFINED || (Character.isISOControl(c) && c != '\n')) {

                        return;
                    }


                    KeyCombo combo = new KeyCombo(keyCode, modifierKeyCodes);

                    keyCombos.add(combo);

                    System.out.println(combo);
                }
            });


            // no changing places in text
            textArea.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {

                    textArea.setCaretPosition(textArea.getDocument().getLength());
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                    textArea.setCaretPosition(textArea.getDocument().getLength());
                }
            });


            textArea.addCaretListener(e -> {

                int end = textArea.getDocument().getLength();

                if (textArea.getCaretPosition() != end) {

                    SwingUtilities.invokeLater(() -> textArea.setCaretPosition(textArea.getDocument().getLength()));
                }
            });


            JScrollPane scrollPane = new JScrollPane(textArea);


            JButton clearButton = new JButton("Clear");

            JButton submitButton = new JButton("Submit");


            clearButton.addActionListener(e -> {


                textArea.setText("");


                keyCombos = new LinkedList<>();

                userInput = new LinkedList<>();


                textArea.requestFocusInWindow();
            });


            submitButton.addActionListener(e -> {

                String text = textArea.getText();

                userInput = new LinkedList<>();

                userInput.add(text);

                Log.debugInput(userInput, "view");


                LinkedList<KeyCombo> combosToSend = new LinkedList<>(keyCombos);


                int delaySeconds = (Integer) delaySpinner.getValue();


                new Thread(() -> {

                    try {

                        controller.userSubmittedInput(userInput, combosToSend, delaySeconds);

                    } catch (AWTException ex) {

                        ex.printStackTrace();
                    }

                }).start();


                keyCombos = new LinkedList<>();

                textArea.setText("");

                textArea.requestFocusInWindow();
            });


            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            buttonPanel.add(clearButton);
            buttonPanel.add(submitButton);


            frame.setLayout(new BorderLayout(10, 10));

            frame.add(topPanel, BorderLayout.NORTH);

            frame.add(scrollPane, BorderLayout.CENTER);

            frame.add(buttonPanel, BorderLayout.SOUTH);


            frame.setVisible(true);

            textArea.requestFocusInWindow();
        });
    }
}

