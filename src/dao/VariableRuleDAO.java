package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import edu.cmu.bean.CommonUseScript;
import edu.cmu.bean.VariableRule;

public class VariableRuleDAO {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void add(VariableRule rule) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("INSERT INTO "
					+ "variablerule (form, variable, required, rulename, description, rangefrom, rangeto, unit, invoketype, category) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?);");
			
			stmt.setString(1, rule.getForm());
			stmt.setString(2, rule.getVariableName());
			stmt.setInt(3, rule.isRequired());
			stmt.setString(4, rule.getRuleName());
			stmt.setString(5, rule.getDescription());
			stmt.setString(6, rule.getRangeFrom());
			stmt.setString(7, rule.getRangeTo());
			stmt.setString(8, rule.getUnit());
			stmt.setInt(9, rule.getInvokeType());
			stmt.setInt(10, rule.getCatetory());
			
			stmt.executeUpdate();
			
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
	
	public VariableRule findRule(String variable) {
		VariableRule r = new VariableRule();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM variablerule WHERE variable=?");
			stmt.setString(1, variable);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				r.setVariableName(result.getString(3));
				r.setRequired(result.getInt(4));
				r.setRuleName(result.getString(5));
				r.setDescription(result.getString(6));
				r.setRangeFrom(result.getString(7));
				r.setRangeTo(result.getString(8));
				r.setUnit(result.getString(9));
				r.setInvokeType(result.getInt(10));
				r.setCatetory(result.getInt(11));
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
		return r;
	}
}
