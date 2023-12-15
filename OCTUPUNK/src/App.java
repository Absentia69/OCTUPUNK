import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class App {

    private static Clip clip;
    public static void main(String[] args) {

    JFrame frame = new JFrame("OCTOPUNK");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1080, 720);
    frame.setLayout(new BorderLayout());

    // Icons
    ImageIcon gearIcon = resizeIcon(new ImageIcon("Images/settings.png"), 20, 20);
    ImageIcon speakerIcon = resizeIcon(new ImageIcon("Images/speaker.png"), 20, 20);
    ImageIcon speakerOffIcon = resizeIcon(new ImageIcon("Images/speaker_off.png"), 20, 20);
    ImageIcon musicNoteIcon = resizeIcon(new ImageIcon("Images/music.png"), 20, 20);

    // Top Panel with Icons
    JPanel topPanel = new JPanel(new BorderLayout());

    // Panel for settings and speaker buttons (aligned to the right)
    JPanel settingsSpeakerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton gearButton = new JButton(gearIcon);
    JButton speakerButton = new JButton(speakerIcon);
    settingsSpeakerPanel.add(gearButton);
    settingsSpeakerPanel.add(speakerButton);

    // Panel for musicNote button (aligned to the left)
    JPanel musicNotePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton musicNoteButton = new JButton(musicNoteIcon);
    musicNotePanel.add(musicNoteButton);

    topPanel.add(settingsSpeakerPanel, BorderLayout.EAST);
    topPanel.add(musicNotePanel, BorderLayout.WEST);

    frame.add(topPanel, BorderLayout.NORTH);

    // Center Panel with Game Name and Buttons
    JPanel centerPanel = new JPanel(new BorderLayout());
    JLabel gameNameLabel = new JLabel("OCTOPUNK", SwingConstants.CENTER);
    centerPanel.add(gameNameLabel, BorderLayout.NORTH);

    // Panel for the buttons (occupying the whole row)
    JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 buttons aligned vertically
    
    // Adjusting buttons to smaller size

    JButton button1 = new JButton("NEW GAME");
    JButton button2 = new JButton("CONTINUE");
    JButton button3 = new JButton("LEVELS");
    JButton button4 = new JButton("GUIDE");
    
    resizeButton(button4, 120, 40);
    resizeButton(button3, 120, 40);
    resizeButton(button2, 120, 40);
    resizeButton(button1, 120, 40);

    // Adding action listeners to buttons
    button1.addActionListener(getButtonActionListener("NEW GAME"));
    button2.addActionListener(getButtonActionListener("CONTINUE"));
    button3.addActionListener(getButtonActionListener("LEVELS"));
    button4.addActionListener(getButtonActionListener("GUIDE"));

    // Adding buttons to the panel
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

    // Get a random WAV file from the Music directory and play it
    String randomWavFile = getRandomWavFile("Main Menu Music");
    if (randomWavFile != null) {
        playSound(randomWavFile, 0.1f); // Adjust volume here 
    }

    musicNoteButton.addActionListener(e -> {
        changeSong();
    });

    // ActionListener for speakerButton
    speakerButton.addActionListener(new ActionListener() {
    boolean isMuted = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isMuted) {
            changeVolume(0.0f);
            speakerButton.setIcon(speakerOffIcon); // Set icon to speaker off
        } else {
            changeVolume(0.1f);
            speakerButton.setIcon(speakerIcon); // Set icon to speaker
        }
        isMuted = !isMuted; // Toggle mute state
    }
});

// Inside your ActionListener for the "Credits" button
creditsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Create a dialog
        JDialog creditsDialog = new JDialog();
        creditsDialog.setTitle("Credits");

        // Create a JTextArea for displaying names
        JTextArea namesArea = new JTextArea("MERAH Mohamed Lamine\nAsma");
        namesArea.setEditable(false); // Make it non-editable
        namesArea.setMargin(new Insets(10, 10, 10, 10)); // Add some margin for better appearance

        // Add the JTextArea to the dialog's content pane
        creditsDialog.getContentPane().add(namesArea);

        // Set dialog properties
        creditsDialog.pack();
        creditsDialog.setLocationRelativeTo(frame); // Set dialog position relative to main frame
        creditsDialog.setVisible(true);
    }
});

// Inside your ActionListener for the "Settings" button
gearButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
       // Create a dialog
        JDialog settingsDialog = new JDialog();
        settingsDialog.setTitle("Settings");

        // Create buttons for video, audio, and controls
        JButton videoButton = new JButton();
        ImageIcon monitorIcon = resizeIcon(new ImageIcon("Images/monitor.png"), 20, 20);
        videoButton.setIcon(monitorIcon);
        videoButton.setText("Video");

        JButton audioButton = new JButton();
        ImageIcon stereoIcon = resizeIcon(new ImageIcon("Images/stereo.png"), 20, 20);
        audioButton.setIcon(stereoIcon);
        audioButton.setText("Audio");

        JButton controlsButton = new JButton();
        ImageIcon keyboardIcon = resizeIcon(new ImageIcon("Images/keyboard.png"), 20, 20);
        controlsButton.setIcon(keyboardIcon);
        controlsButton.setText("Controls");

        // Set button properties
        videoButton.setPreferredSize(new Dimension(200, 60)); // Adjust button size as needed
        audioButton.setPreferredSize(new Dimension(200, 60));
        controlsButton.setPreferredSize(new Dimension(200, 60));

        // Add action listeners to buttons (adjust the action accordingly)
        videoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle video settings action
                System.out.println("Video settings clicked");
            }
        });

        audioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle audio settings action
                System.out.println("Audio settings clicked");
            }
        });

        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle controls settings action
                System.out.println("Controls settings clicked");
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 buttons vertically

        videoButton.setSize(new Dimension(20, 20));
        // Add buttons to the panel
        buttonsPanel.add(videoButton);
        buttonsPanel.add(audioButton);
        buttonsPanel.add(controlsButton);

        // Add the panel to the dialog's content pane
        settingsDialog.getContentPane().add(buttonsPanel);

        // Set dialog properties
        settingsDialog.pack();
        settingsDialog.setLocationRelativeTo(frame); // Set dialog position relative to main frame
        settingsDialog.setVisible(true);
    }
});



    frame.setVisible(true);
}


    //Resize icons
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    //resize buttons
    private static void resizeButton(JButton button, int width, int height) {
        Dimension buttonSize = new Dimension(width, height);
        button.setPreferredSize(buttonSize);
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

    //Selecting a random song
    private static String getRandomWavFile(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

        if (files != null && files.length > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(files.length) ;
            System.out.println("Now playing:" + files[randomIndex]);
            return files[randomIndex].getAbsolutePath();
        } else {
            System.out.println("No WAV files found in the directory: " + directoryPath);
            return null;
        }
    }

    // Updated method to play sound with volume adjustment
    private static void playSound(String filePath, float volume) {
    try {
        File soundFile = new File(filePath);
        if (!soundFile.exists()) {
            System.out.println("File does not exist: " + filePath);
            return;
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

        AudioFormat format = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);

        clip.open(audioInputStream);

        // Check if MASTER_GAIN control type is supported
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            // Adjust volume using FloatControl
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            
            float dB = volume == 0.0f ? Float.NEGATIVE_INFINITY : (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        } else {
            System.out.println("Master Gain control not supported.");
        }

        // Add listener to play next song when the current song ends
        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP && volume != 0.0f) {
                changeSong();
            }
        });

        clip.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    // Method to change the currently playing song to a new random one
    private static void changeSong() {
        String newSongFilePath = getRandomWavFile("Main Menu Music");
        if (newSongFilePath != null) {
            clip.stop();
            clip.close();
            playSound(newSongFilePath, 0.1f);
        }
    }

    //Delay Issue
    private static void changeVolume(float volume) {
    if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        // Calculate the dB value for the given volume
        float dB = volume == 0.0f ? Float.NEGATIVE_INFINITY : (float) (Math.log(volume) / Math.log(10.0) * 20.0);

        try {
            gainControl.setValue(dB);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid volume value: " + volume);
        }
    } else {
        System.out.println("Volume control not supported or clip is null.");
    }
}


}

