package com.stgcodes.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			String sql = "select * from student order by last_name";

			statement = connection.createStatement();

			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");

				Student student = new Student(id, firstName, lastName, email);

				students.add(student);
			}

			return students;
		} finally {
			close(connection, statement, resultSet);
		}
	}
	
	public Student getStudent(String studentId) throws Exception {
		Student student = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int id;
		
		try {
			id = Integer.parseInt(studentId);
			
			connection = dataSource.getConnection();
			
			String sql = "select * from student where id=?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				
				student = new Student(id, firstName, lastName, email);
			} else {
				throw new Exception("Could not find student ID: " + id);
			}
			
			return student;
		} finally {
			close(connection, statement, resultSet);
		}
	}
	
	public void addStudent(Student student) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "insert into student "
					+ "(first_name, last_name, email) "
					+ "values (?, ?, ?)";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1,  student.getFirstName());
			statement.setString(2,  student.getLastName());
			statement.setString(3,  student.getEmail());
			
			statement.execute();
		} finally {
			close(connection, statement, null);
		}
	}
	
	public void updateStudent(Student student) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "update student "
					+ "set first_name=?, last_name=?, email=? "
					+ "where id=?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.setInt(4, student.getId());
			
			statement.execute();
		} finally {
			close(connection, statement, null);
		}
		
	}

	private void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
