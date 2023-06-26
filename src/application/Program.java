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
			char option = selectSection(sc);
			Department department = new Department();

			if (option == '1') {

				department.addDepartment(sc);

			} else if (option == '2') {

				if (department.departmentCounter() > 0) {

					System.out.print("How many employees do you wanna add? ");
					int count = sc.nextInt();

					for (int i = 0; i < count; i++) {
						if (count > 1) {
							System.out.println(i + 1 + "º Employee:");
						}

						Employee employee = new Employee();
						employee.collectEmployeeData(sc);
						employee.addEmployee(sc);

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

	static char selectSection(Scanner sc) {
		System.out.println("Choose a option:");
		System.out.println("1. Department");
		System.out.println("2. Employee");
		char option = sc.next().toLowerCase().charAt(0);
		while (option != '1' && option != '2') {
			System.out.println("Wrong option. Try again. ");
			System.out.println("1. Department");
			System.out.println("2. Employee");
			option = sc.next().toLowerCase().charAt(0);
		}
		System.out.println();
		return option;

	}

}
