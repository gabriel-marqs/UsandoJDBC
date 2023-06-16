package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

import db.DB;
import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn = DB.getConnection();

			System.out.print("Press D to input a new Department, and E to input a new Employee: ");
			char option = sc.next().toLowerCase().charAt(0);

			while (option != 'd' && option != 'e') {
				System.out.println("Wrong option. Try again: ");
				System.out.print("Press D to input a new Department, and E to input a new Employee: ");
				option = sc.next().toLowerCase().charAt(0);

			}
			if (option == 'd') {
				System.out.println("In progress...");
			} else if (option == 'e') {
				
				Department department = new Department();
				int departmentCounter = department.departmentCounter();
				
				if (departmentCounter > 0) {
				
				System.out.print("How many employees do you wanna input? ");
				int count = sc.nextInt();
				
				for (int i = 0; i < count; i++) {
				
				System.out.print("Name: ");
				sc.nextLine();
				String name = sc.nextLine();
				System.out.print("E-mail: ");
				String email = sc.next();
				System.out.print("Birthday (dd/MM/yyy): ");
				String birthDate = sc.next();
				System.out.print("Base Salary: ");
				double baseSalary = sc.nextDouble();
				System.out.print("Department ID: ");
				int departmentId = sc.nextInt();

				Seller seller = new Seller(name, email, birthDate, baseSalary, departmentId);

				rs = seller.inputSeller();
				
				System.out.println();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println(seller.getName() + " Successfully added, your ID is " + id);
				}
				
				}

				} else {
					System.out.println("You need at least one department to input a employee.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeConnection();
		}

		sc.close();

	}

}
