package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.DB;

public class Employee {

	private String name;
	private String email;
	private String birthDate;
	private Double baseSalary;
	private Integer departmentId;

	public Employee() {

	}

	public Employee(String name, String email, String birthDate, Double baseSalary, Integer departmentId) {
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.baseSalary = baseSalary;
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public void addEmployee(Scanner sc) {

		Department department = new Department();

		if (department.departmentCounter() > 0) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			System.out.print("How many employees do you wanna add? ");
			int count = sc.nextInt();

			try {
				conn = DB.getConnection();
				for (int i = 0; i < count; i++) {
					if (count > 1) {
						System.out.println(i + 1 + "º Employee:");
					}

					collectEmployeeData(sc);

					st = conn.prepareStatement("INSERT INTO employee"
							+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " + "VALUES " + "(?, ?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);

					st.setString(1, name);
					st.setString(2, email);
					st.setDate(3, new java.sql.Date(sdf.parse(birthDate).getTime()));
					st.setDouble(4, baseSalary);
					st.setInt(5, departmentId);
					try {
						st.executeUpdate();
					} catch (SQLIntegrityConstraintViolationException e) {
						invalidDepartment(sc);
					}
					rs = st.getGeneratedKeys();

					printAddEmployee(rs);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				invalidDate(sc);
			}
		} else {
			System.out.println("You need at least one department to input a employee.");
		}
	}

	public void printAddEmployee(ResultSet rs) {
		try {
			while (rs.next()) {
				int id;
				id = rs.getInt(1);
				System.out.println(name + " was successfully added, your ID is " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean checkDepartment(Scanner sc) {
		List<Integer> list = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM department");
			while (rs.next()) {
				list.add(rs.getInt("id"));
			}

			if (!list.contains(departmentId)) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public void invalidDepartment(Scanner sc) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		System.out.println();
		System.out.println("Invalid department. Choose one of the ID's below:");
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM department");
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " - " + rs.getString("Name"));
			}
			System.out.println();
			System.out.print("Department ID: ");
			departmentId = sc.nextInt();
			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void invalidDate(Scanner sc) {
		System.out.print("Invalid date format. Birthday (dd/MM/yyyy): ");
		birthDate = sc.next();
		addEmployee(sc);
	}

	public void collectEmployeeData(Scanner sc) {
		sc.nextLine();
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.print("E-mail: ");
		email = sc.next();
		System.out.print("Birthday (dd/MM/yyy): ");
		String dateStr = sc.next();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			LocalDate date = LocalDate.parse(dateStr, formatter);
			LocalDate currentDate = LocalDate.now();

			LocalDate minValidDate = currentDate.minusYears(14);
			LocalDate maxValidDate = LocalDate.of(1920, 1, 1);

			while (date.isAfter(minValidDate) || date.isBefore(maxValidDate)) {
				System.out.println("Invalid birth date. Please enter a date between " + formatter.format(maxValidDate)
						+ " and " + formatter.format(minValidDate));
				collectEmployeeData(sc);
				return;
			}

			birthDate = formatter.format(date);

		} catch (DateTimeParseException e) {
			invalidDate(sc);
		}
		System.out.print("Base Salary: ");
		baseSalary = sc.nextDouble();
		System.out.print("Department ID: ");
		departmentId = sc.nextInt();
		while (checkDepartment(sc) == false) {
			invalidDepartment(sc);
		}
	}

	public void employeeOptions(Scanner sc) {
		System.out.println("1. list");
		System.out.println("2. add");
		System.out.println("3. remove");
		System.out.println("4. update");
		int option = sc.nextInt();

		while (option > 4) {
			System.out.println("Wrong option. Try Again.");
			System.out.println("1. list");
			System.out.println("2. add");
			System.out.println("3. remove");
			System.out.println("4. update");
			option = sc.nextInt();
		}

		executeOptions(option, sc);

	}

	public void executeOptions(int option, Scanner sc) {
		if (option == 1) {
			System.out.println("In progress...");
		}

		else if (option == 2) {
			addEmployee(sc);
		}

		else {
			updateEmployee(sc);
		}

	}

	public void updateEmployee(Scanner sc) {
		System.out.print("Employee ID: ");
		int empId = sc.nextInt();

		System.out.println("What do you wanna change?");
		System.out.println("1. Name");
		System.out.println("2. E-mail");
		System.out.println("3. Birth Date");
		System.out.println("4. Base Salary");
		System.out.println("5. Department");
		System.out.print("Option: ");
		int option = sc.nextInt();

		while (option > 5) {
			System.out.println("Wrong option. Try Again.");
			System.out.println("1. Name");
			System.out.println("2. E-mail");
			System.out.println("3. Birth Date");
			System.out.println("4. Base Salary");
			System.out.println("5. Department");
			option = sc.nextInt();
			
		}
		
		String optionName = "";
		if (option == 1) {
			optionName = "Name";
		} 
		else if (option == 2) {
			optionName = "Email";
		}
		else if (option == 3) {
			optionName = "BirthDate";
		}
		else if (option == 4) {
			optionName = "BaseSalary";
		}
		else if (option == 5) {
			optionName = "DepartmentId";
		}

		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = DB.getConnection();

			st = conn.prepareStatement("UPDATE employee " + "SET " + optionName + " = ? " + "WHERE " + "Id = ?");

			st.setInt(2, empId);

			if (option == 1) {

				System.out.print("New name: ");
				sc.nextLine();
				st.setString(1, sc.nextLine());
			}
			if (option == 2) {

				System.out.print("New e-mail: ");
				sc.nextLine();
				st.setString(1, sc.nextLine());
			}
			
			if (option == 3) {

				System.out.print("New Birth Date (dd/MM/yyyy): ");
				sc.nextLine();
				st.setString(1, sc.nextLine());
			}
			
			if (option == 4) {

				System.out.print("New base salary: ");
				sc.nextLine();
				st.setDouble(1, sc.nextDouble());
			}
			
			else if (option == 5){

				System.out.print("New ID Department: ");
				sc.nextLine();
				st.setInt(1, sc.nextInt());
			}
			
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
