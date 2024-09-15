package com.HMS1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem
{
	private static final String  url="jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username="hospital";
	private static final String  password="hospital";
	public static void main(String[] args)
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patient patient =new Patient(connection,sc);
			Doctor doctor = new Doctor(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1.add patient");
				System.out.println("2.view patient");
				System.out.println("3.view doctors");
				System.out.println("4.book appointment");
				System.out.println("5.exit");
				System.out.println("enter your choice");
				int choice=sc.nextInt();
				System.out.println("THANK YOU!!!");
				
				switch(choice) {
				case 1:
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					doctor.viewDoctors();
					System.out.println();
					break;
					
				case 4:
					bookAppointment(patient,doctor,connection,sc);
					System.out.println();
					break;
					
				case 5:
					return;
			    default:
			    	System.out.println("enter valid chouice!!!");
			    	break;
					
					
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner)
	{
		System.out.println("Enter patient ID");
		int patientID=scanner.nextInt();
		System.out.println("Enter doctor ID");
		int doctorID=scanner.nextInt();
		System.out.println("Enter appointment date:(yyyy-mm-dd");
		String appointmentDate=scanner.next();
		if(patient.getPatientById(patientID) && doctor.getDoctorById(doctorID))
		{
			if(checkDoctorAvailability(doctorID,appointmentDate, connection))
			{
				String appointmentQuery = "insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
				try {
					PreparedStatement ps = connection.prepareStatement(appointmentQuery);
					ps.setInt(1,patientID);
					ps.setInt(2,doctorID);
					ps.setString(3,appointmentDate);
					int rowsAffected= ps.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appointment booked");
					}else {
						System.out.println("failed to book appointment");
					}
					
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}else {
			System.out.println("Doctor not available on this date");
		}
		}
		else{
			System.out.println("Either patient or doctor doesnt exist");
		}
		}
		public static final boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection)
		{
			String query = "select count(*) from appoinmtments where doctor_id =? and appointment_date = ?";
			try {
				PreparedStatement ps=connection.prepareStatement(query);
				ps.setInt(1, doctorID);
				ps.setString(2, appointmentDate);
				ResultSet rs= ps.executeQuery();
				if(rs.next()) {
					int count=rs.getInt(1);
					if(count==0) {
						return true;
					}else {
						return false;
					}	
				}
				
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
			return false;	
	}	
}
