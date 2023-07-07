package com.company;
// ClientSock.java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSock {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 1234); // Change the server address and port if necessary
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Read the height and weight from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter height (cm): ");
            String height = reader.readLine();
            System.out.print("Enter weight (kg): ");
            String weight = reader.readLine();

            // Send the height and weight to the server
            out.println(height);
            out.println(weight);

            // Receive the BMI from the server
            String bmi = in.readLine();
            System.out.println("BMI: " + bmi);

            // Close the connections
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
