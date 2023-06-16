package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class InputSeller {

	public Integer inputSeller(String name, String email, String birthDate, Double baseSalary, int departmentId) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Connection conn = null;
				PreparedStatement st = null;
				int rowsAffected = 0;
				try {
				
				conn = DB.getConnection();
				st = conn.prepareStatement(
				"INSERT INTO seller"
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+ "VALUES"
				+ "(?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
				
				st.setString(1, name);
				st.setString(2, email);
				st.setDate(3, new java.sql.Date(sdf.parse(birthDate).getTime()));
				st.setDouble(4, baseSalary);
				st.setInt(5, departmentId);
				
				rowsAffected = st.executeUpdate();
		
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return rowsAffected;
	}

}
