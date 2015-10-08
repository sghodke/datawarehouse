package edu.buffalo.ktb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.buffalo.ktb.bean.Patient;
import edu.buffalo.ktb.db.DBManager;

public class QueryDAO {

	private Connection connection;
	private Statement stmt;
	//private PreparedStatement pstmt;
	private ResultSet rs;
	
	public List<Patient> getPatients() {
		
		try {
			List<Patient> patientList = new ArrayList<Patient>();
			String patientId, patientName;
			Patient patient;
			
			connection = DBManager.getInstance().getConnection();
			String sql = "Select * FROM PATIENT";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				patientId = rs.getString("p_id");
				patientName = rs.getString("name");
				patient = new Patient();
				patient.setPatientId(patientId);
				patient.setPatientName(patientName);
				patientList.add(patient);
			}
			
			return patientList;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			close();
		}
		
		return null;
	}
	
	/**
	 * close the resources
	 */
	private void close() {
		try {
			/*if(rs != null) {
				rs.close();
			}
			if (connection != null) {
				connection.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}*/
			DBManager.getInstance().releaseResources(connection, stmt, rs);
			
		} catch (Exception e) {
			
		}
	}
}
