package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

import db.DB;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		PreparedStatement st = null;

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

				ResultSet rs = seller.inputSeller();

				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! ID = " + id);
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
