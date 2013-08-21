package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import edu.cmu.bean.CRF;
import edu.cmu.bean.Variable;

public class CRFDAO {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<CRF> findCRF() {
		List<CRF> crfs = new ArrayList<CRF>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM forms");
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				CRF crf = new CRF();
				crf.setId(result.getInt(1));
				crf.setName(result.getString(2));
				crf.setFullname(result.getString(3));
				crfs.add(crf);
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
		return crfs;
	}
	
	public List<Variable> getFormVariables(String formName) {
		List<Variable> vlist = new ArrayList<Variable>();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM variable WHERE crf=?");
			stmt.setString(1, formName);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				Variable v = new Variable();
				v.setId(result.getInt(1));
				v.setForm(result.getString(2));
				v.setName(result.getString(3));
				v.setQuestion(result.getString(4));
				v.setType(result.getString(5));
				v.setDefined(result.getInt(6));
				vlist.add(v);
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
		return vlist;
	}
	
	public void saveScript(String crfName, String script) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("UPDATE forms SET script=? WHERE name=?;");
			
			stmt.setString(1, script);
			stmt.setString(2, crfName);
			
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
	
	public CRF getFormbyName(String formName) {
		CRF crf = new CRF();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM forms WHERE name=?");
			stmt.setString(1, formName);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				crf.setId(result.getInt(1));
				crf.setName(result.getString(2));
				crf.setFullname(result.getString(3));
				crf.setScript(result.getString(4));
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
		return crf;
	}
}
