package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import db.DB;

public class Department {

	private String name;

	public Department() {

	}

	public Department(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResultSet addDepartment(Scanner sc) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		collectDepartmentData(sc);
		
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO department" + "(Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, name);

			st.executeUpdate();
			rs = st.getGeneratedKeys();
			
			printAddDepartment(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			DB.closeConnection();
		}

		return rs;
	}

	public void printAddDepartment(ResultSet rs) {
		try {
			while (rs.next()) {
				int id;
				id = rs.getInt(1);
				System.out.println("Department " + name + " are successfully added, your ID is " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer departmentCounter() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int counter = 0;

		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from department");

			while (rs.next()) {
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return counter;

	}
	
	public void collectDepartmentData(Scanner sc) {
		System.out.print("Department name: ");
		sc.nextLine();
		name = sc.nextLine();
	}


}
