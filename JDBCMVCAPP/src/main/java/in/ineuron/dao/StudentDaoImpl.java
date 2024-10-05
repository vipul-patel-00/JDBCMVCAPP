package in.ineuron.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.ineuron.dto.Student;
import in.ineuron.util.JdbcUtil;

public class StudentDaoImpl implements IStudentDao{

	Connection connection;
	String status;
	PreparedStatement pstmt;
	
	@Override
	public String save(Student student) {
		String sqlInsertQuery = "insert into student(sname,sage,saddr) values(?,?,?)";
		
		try {
			connection = JdbcUtil.getJdbcConnection();
			if(connection!=null) {
				pstmt = connection.prepareStatement(sqlInsertQuery);	
				if(pstmt!=null) {
					pstmt.setString(1, student.getSname());
					pstmt.setInt(2, student.getSage());
					pstmt.setString(3, student.getSaddr());
				}
				if(pstmt!=null) {
					int rowsAffected = pstmt.executeUpdate();
					if(rowsAffected==1) {
						status="success";
					}
					else {
						status = "failure";
					}
				}
			}
		} catch (SQLException e) {
			status = "failure";
			e.printStackTrace();
		} catch (IOException e) {
			status = "failure";
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Student findById(Integer sid) {
		String selectQuery = "select * from student where sid=?";
		Student student = null;
		
		try {
			connection = JdbcUtil.getJdbcConnection();
			if(connection!=null) {
				pstmt = connection.prepareStatement(selectQuery);
				if(pstmt!=null) {
					pstmt.setInt(1, sid);
				}
				if(pstmt!=null) {
					ResultSet resultSet = pstmt.executeQuery();
					if(resultSet.next()) {
						student = new Student();
						student.setSid(resultSet.getInt(1));
						student.setSname(resultSet.getString(2));
						student.setSage(resultSet.getInt(3));
						student.setSaddr(resultSet.getString(4));
					}
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		return student;
	}

	@Override
	public String updateById(Student student) {
		String updateQuery = "update student set sname=?, sage=?, saddr=? where sid=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if(connection!=null) {
				pstmt = connection.prepareStatement(updateQuery);
				if(pstmt!=null) {
					pstmt.setString(1, student.getSname());
					pstmt.setInt(2, student.getSage());
					pstmt.setString(3, student.getSaddr());
					pstmt.setInt(4, student.getSid());
				}
				if(pstmt!=null) {
					int rowsAffected = pstmt.executeUpdate();
					if(rowsAffected==1) {
						status = "success";
					}
					else {
						status = "failure";
					}
				}
			}
		} catch (IOException | SQLException e) {
			status="failure";
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public String deleteById(Integer sid) {
		String deleteQuery = "delete from student where sid=?";
		try {
			Student student = findById(sid);
			if(student!=null) {
				connection = JdbcUtil.getJdbcConnection();
				if(connection!=null) {
					pstmt = connection.prepareStatement(deleteQuery);
					if(pstmt!=null) {
						pstmt.setInt(1, sid);
					}
					if(pstmt!=null) {
						int rowsAffected = pstmt.executeUpdate();
						if(rowsAffected==1) {
							status="success";
						}
						else {
							status="failure";
						}
					}
				}
			}
			else {
				status="notavailable";
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			status="failure";
		}
		return status;
	}

}
