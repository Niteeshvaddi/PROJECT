package com.HMS1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor 
{
    private Connection connection;
    
    public Doctor(Connection connection)
    {
        this.connection = connection;
    }
    
    public void viewDoctors() 
    {
        String query = "SELECT * FROM doctor";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) 
        {
            System.out.println("Doctors:");
            System.out.println("ID, Name, Specialization");
            
            while (rs.next()) 
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                // Print each doctor's details
                System.out.println(id + ", " + name + ", " + specialization);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) 
    {
        String query = "SELECT * FROM doctor WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) 
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) 
            {
                return rs.next(); // Returns true if a record is found
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return false; // Returns false if no record is found or an error occurs
    }
}
