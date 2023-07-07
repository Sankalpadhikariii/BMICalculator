package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main extends JFrame {
    private JTextField heightField;
    private JTextField weightField;
    private JButton calculateButton;
    private JTextArea resultArea;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Main() {
        // Set up the GUI components
        heightField = new JTextField();
        weightField = new JTextField();
        calculateButton = new JButton("Calculate BMI");
        resultArea = new JTextArea();

        // Set up the layout
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Height (cm):"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Weight (kg):"));
        inputPanel.add(weightField);
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calculateButton);
        add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.EAST);

        // Set up the event listeners
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        // Connect to the server
        connectToServer();
    }

    private void connectToServer() {
        try {
            clientSocket = new Socket("localhost", 1234); // Change the server address and port if necessary
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateBMI() {
        // Get the height and weight from the input fields
        int height = Integer.parseInt(heightField.getText());
        int weight = Integer.parseInt(weightField.getText());

        // Check if the connection is established
        if (out != null) {
            // Send the height and weight to the server
            out.println(height);
            out.println(weight);
        } else {
            System.out.println("Connection to server not established.");
            return;
        }

        // Receive the BMI from the server
        try {
            String bmi = in.readLine();
            resultArea.setText("BMI: " + bmi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main app = new Main();
                app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.pack();
                app.setVisible(true);
            }
        });
    }
}
