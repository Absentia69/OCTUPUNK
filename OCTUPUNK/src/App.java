import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    public static void main(String[] args) {
        JFrame frame = new JFrame("OCTOPUNK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 1024);
        frame.setLayout(new BorderLayout());

        // Icons
        ImageIcon gearIcon = resizeIcon(new ImageIcon("Images/settings.png"), 20, 20);
        ImageIcon speakerIcon = resizeIcon(new ImageIcon("Images/speaker.png"), 20, 20);
        ImageIcon musicNoteIcon = resizeIcon(new ImageIcon("Images/music.png"), 20, 20);

        // Top Panel with Icons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton gearButton = new JButton(gearIcon);
        JButton speakerButton = new JButton(speakerIcon);
        JButton musicNoteButton = new JButton(musicNoteIcon);
        topPanel.add(gearButton);
        topPanel.add(speakerButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // Adding music note to top-left corner
        frame.add(musicNoteButton, BorderLayout.NORTH);

        // Center Panel with Game Name and Buttons
        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel gameNameLabel = new JLabel("OCTOPUNK", SwingConstants.CENTER);
        centerPanel.add(gameNameLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 buttons aligned vertically
        JButton button1 = new JButton("NEW GAME");
        JButton button2 = new JButton("CONTINUE");
        JButton button3 = new JButton("LEVELS");
        JButton button4 = new JButton("GUIDE");

        // Resizing buttons
        Dimension buttonSize = new Dimension(150, 40);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);

        button1.addActionListener(getButtonActionListener("NEW GAME"));
        button2.addActionListener(getButtonActionListener("CONTINUE"));
        button3.addActionListener(getButtonActionListener("LEVELS"));
        button4.addActionListener(getButtonActionListener("GUIDE"));

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel with Credits
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton creditsButton = new JButton("Credits");
        creditsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action for credits button
                System.out.println("Credits clicked");
            }
        });
        bottomPanel.add(creditsButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    // Method to create ActionListeners for buttons
    private static ActionListener getButtonActionListener(String buttonText) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action for the button
                System.out.println(buttonText + " clicked");
            }
        };
    }
}
