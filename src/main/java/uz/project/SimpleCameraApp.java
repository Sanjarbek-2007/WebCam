package uz.project;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SimpleCameraApp {

    private JFrame frame;
    private Webcam webcam;
    private JButton captureButton, exitButton;

    public SimpleCameraApp() {
        // Initialize the webcam
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        // Create the main frame
        frame = new JFrame("Camera Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        // Create webcam panel to display video feed
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true); // Show FPS on the panel
        webcamPanel.setFillArea(true);
        frame.add(webcamPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        captureButton = new JButton("Capture Photo");
        exitButton = new JButton("Exit");
        buttonPanel.add(captureButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capturePhoto();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webcam.close(); // Release the webcam
                frame.dispose();
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    private void capturePhoto() {
        try {
            // Capture an image from the webcam
            BufferedImage image = webcam.getImage();

            // Save the image to a file
            String filename = "captured_photo.png";
            ImageIO.write(image, "PNG", new File(filename));
            JOptionPane.showMessageDialog(frame, "Photo saved: " + filename);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Failed to capture photo: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleCameraApp::new);
    }
}
