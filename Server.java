package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Database database;

    public Server() {
        try {
            // Start the server and listen for client connections
            serverSocket = new ServerSocket(1234); // Change the port if needed
            System.out.println("Server started. Waiting for client connection...");

            // Accept a client connection
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Set up the input and output streams
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Initialize the database
            database = new Database();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveData() {
        try {
            // Receive the height and weight from the client
            int height = Integer.parseInt(in.readLine());
            int weight = Integer.parseInt(in.readLine());

            // Calculate the BMI
            double bmi = calculateBMI(height, weight);

            // Save the BMI to the database
            database.saveBMI(height, weight, bmi);
            System.out.println("BMI data saved to the database.");

            // Send the BMI back to the client
            out.println(bmi);

            // Clean up resources
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculateBMI(int height, int weight) {
        // Perform the BMI calculation
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    private void closeConnection() {
        try {
            // Close the streams and sockets
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();

            // Close the database connection
            database.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.receiveData();
    }
}
