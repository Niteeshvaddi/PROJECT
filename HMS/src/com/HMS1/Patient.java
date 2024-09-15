package com.HMS1;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.Scanner;

	public class Patient 
	{
	    private Connection connection;
	    private Scanner scanner;
	    
	    public Patient(Connection connection, Scanner scanner)
	    {
	        this.connection = connection;
	        this.scanner = scanner;
	    }
	    
	    public void addPatient() 
	    {
	        System.out.println("Enter patient ID:");
	        int id = scanner.nextInt();
	        scanner.nextLine(); // Consume newline
	        System.out.println("Enter patient name:");
	        String name = scanner.nextLine();
	        System.out.println("Enter patient age:");
	        int age = scanner.nextInt();
	        System.out.println("Enter patient gender:");
	        String gender = scanner.next();
	        
	        String query = "INSERT INTO patient (id, name, age, gender) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement ps = connection.prepareStatement(query)) 
	        {
	            ps.setInt(1, id);
	            ps.setString(2, name);
	            ps.setInt(3, age);
	            ps.setString(4, gender);
	            int affectedRows = ps.executeUpdate();
	            if (affectedRows > 0) 
	            {
	                System.out.println("Patient added successfully!");
	            }
	            else 
	            {
	                System.out.println("Failed to add patient.");
	            }
	        } 
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	    
	    public void viewPatients() 
	    {
	        String query = "SELECT * FROM Patient";
	        try (PreparedStatement ps = connection.prepareStatement(query);
	             ResultSet rs = ps.executeQuery()) 
	        {
	            System.out.println("Patients:");
	            System.out.println("ID, Name, Age, Gender");
	            while (rs.next()) 
	            {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                int age = rs.getInt("age");
	                String gender = rs.getString("gender");
	                System.out.println(id + ", " + name + ", " + age + ", " + gender);
	            }
	        } 
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	    
	    public boolean getPatientById(int id) 
	    {
	        String query = "SELECT * FROM patient WHERE id = ?";
	        try (PreparedStatement ps = connection.prepareStatement(query)) 
	        {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) 
	            {
	                return rs.next();
	            }
	        } 
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	        }
	        return false;
	    }
	}



