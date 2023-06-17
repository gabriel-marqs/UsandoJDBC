package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public ResultSet addEmployee() {
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO employee" + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, name);
			st.setString(2, email);
			st.setDate(3, new java.sql.Date(sdf.parse(birthDate).getTime()));
			st.setDouble(4, baseSalary);
			st.setInt(5, departmentId);
			try {
			st.executeUpdate();
			} catch (SQLIntegrityConstraintViolationException e) {
				invalidDepartment();
			}
			rs = st.getGeneratedKeys();

			printAddEmployee(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			invalidDate();
		} 
		finally {
			sc.close();
		}
		return rs;
		
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

	public void checkDepartment() {
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
				invalidDepartment();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void invalidDepartment() {
		Scanner sc = new Scanner(System.in);
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
			addEmployee();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sc.close();
	}
	
	public void invalidDate() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Invalid date format. Birthday (dd/MM/yyyy): ");
		birthDate = sc.next();
		addEmployee();
		sc.close();
	} 

}
