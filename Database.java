package com.company;
import java.sql.*;

public class Database {
    private Connection connection;
    private PreparedStatement insertStatement;

    public Database() {
        try {
            // Set up the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "username", "password");

            // Prepare the SQL statement for inserting data
            String insertQuery = "INSERT INTO bmi_data (height, weight, bmi) VALUES (?, ?, ?)";
            insertStatement = connection.prepareStatement(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveBMI(int height, int weight, double bmi) {
        try {
            // Set the parameter values in the prepared statement
            insertStatement.setInt(1, height);
            insertStatement.setInt(2, weight);
            insertStatement.setDouble(3, bmi);

            // Execute the insert statement
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            // Close the prepared statement and database connection
            insertStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Database database = new Database();
        // Example usage: save BMI data to the database
        int height = 180;
        int weight = 75;
        double bmi = calculateBMI(height, weight);
        database.saveBMI(height, weight, bmi);
        database.closeConnection();
    }

    private static double calculateBMI(int height, int weight) {
        // Perform the BMI calculation
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }
}
