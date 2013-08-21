package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.w3c.dom.UserDataHandler;

import edu.cmu.bean.CommonUseScript;

public class CommonUseDAO {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<CommonUseScript> findall() {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<CommonUseScript> scripts = new ArrayList<CommonUseScript>();
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM commonuse");
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				CommonUseScript tmp = new CommonUseScript();
				tmp.setId(result.getString(1));
				tmp.setName(result.getString(2));
				tmp.setScript(result.getString(3));
				scripts.add(tmp);
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
		return scripts;
	}
	
	public CommonUseScript findById(String id) {
		CommonUseScript script = new CommonUseScript();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM commonuse WHERE id=?");
			stmt.setString(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				script.setId(result.getString(1));
				script.setName(result.getString(2));
				script.setScript(result.getString(3));
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
		return script;
	}
	
	public void add(CommonUseScript c) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("INSERT INTO "
					+ "commonuse (scriptname, script) "
					+ "VALUES (?,?);");
			
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getScript());
			
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
	
	public void delete(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("DELETE FROM commonuse WHERE id=?");
			stmt.setInt(1, id);
			stmt.execute();
			
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
