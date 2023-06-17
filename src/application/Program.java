package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Scanner;

import db.DB;
import entities.Department;
import entities.Employee;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn = DB.getConnection();

			System.out.print("Do you want add a new Employee or Department? (E/D): ");
			char option = sc.next().toLowerCase().charAt(0);

			while (option != 'd' && option != 'e') {
				System.out.println("Wrong option. Try again: ");
				System.out.print("Press D to input a new Department, or E to input a new Employee: ");
				option = sc.next().toLowerCase().charAt(0);

			}
			if (option == 'd') {
				System.out.print("How many departments do you wanna add? ");
				int count = sc.nextInt();
				

				for (int i = 0; i < count; i++) {
					System.out.println();
					if (count > 1) {
					System.out.println(i + 1 + "º Department:");
					}
					System.out.print("Department name: ");
					sc.nextLine();
					String name = sc.nextLine();

					Department department = new Department(name);
					rs = department.addDepartment();
					department.printAddDepartment(rs);

				}
			} else if (option == 'e') {

				Department department = new Department();
				int departmentCounter = department.departmentCounter();

				if (departmentCounter > 0) {

					System.out.print("How many employees do you wanna add? ");
					int count = sc.nextInt();

					for (int i = 0; i < count; i++) {
						System.out.println();
						if (count > 1) {
						System.out.println(i + 1 + "º Employee:");
						}
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

						Employee employee = new Employee(name, email, birthDate, baseSalary, departmentId);
						employee.addEmployee();

					}
				} else {
					System.out.println("You need at least one department to input a employee.");
				}
			}

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			DB.closeConnection();
		}

		sc.close();

	}

}
