package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import edu.cmu.bean.Variable;

public class VariableDAO {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Variable findById(String varName, String crfName) {
		Variable var = new Variable();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM variable WHERE name=? AND crf=?");
			stmt.setString(1, varName);
			stmt.setString(2, crfName);
			ResultSet result = stmt.executeQuery();
			
			if (result.next()) {
				var.setId(result.getInt("id"));
				var.setName(result.getString("name"));
				var.setForm(result.getString("crf"));
				var.setQuestion(result.getString("question"));
				var.setType(result.getString("type"));
				var.setDefined(result.getInt("defined"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return var;
	}
	
	public void updateSet(Variable var) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("UPDATE variable SET defined=1 WHERE id=?");
			
			stmt.setInt(1, var.getId());
			
			stmt.executeUpdate();
			//System.out.print(tmp);
			//System.out.println("Update successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error");
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}