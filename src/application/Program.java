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
			int option = selectSection(sc);

			if (option == 1) {

				Department department = new Department();
				department.departmentOptions(sc);
				
			} else if (option == 2) {

				Employee employee = new Employee();
				employee.employeeOptions(sc);
			}

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			DB.closeConnection();
		}

		sc.close();

	}

	static int selectSection(Scanner sc) {
		System.out.println("Choose a option:");
		System.out.println("1. Department");
		System.out.println("2. Employee");
		int option = sc.nextInt();
		while (option != 1 && option != 2) {
			System.out.println("Wrong option. Try again. ");
			System.out.println("1. Department");
			System.out.println("2. Employee");
			option = sc.nextInt();
		}
		System.out.println();
		return option;

	}

}
