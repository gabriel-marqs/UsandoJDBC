package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			conn = DB.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO seller" 
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, name);
			st.setString(2, email);
			st.setDate(3, new java.sql.Date(sdf.parse(birthDate).getTime()));
			st.setDouble(4, baseSalary);
			st.setInt(5, departmentId);

			st.executeUpdate();
			rs = st.getGeneratedKeys();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public void printAddEmployee(ResultSet rs) {
			try {
				while (rs.next()) {
					int id;
				id = rs.getInt(1);
				System.out.println(name + " Successfully added, your ID is " + id);
			}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}


