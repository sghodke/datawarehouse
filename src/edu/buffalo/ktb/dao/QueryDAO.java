package edu.buffalo.ktb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.buffalo.ktb.bean.Patient;
import edu.buffalo.ktb.db.DBManager;
import edu.buffalo.ktb.util.Queries;

public class QueryDAO {

	private Connection connection;
	private Statement stmt;
	private PreparedStatement pstmt;
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
	
	public Map<String, Integer> getResultQueryOne(String description, String name, String type) {
		
		try {
			Map<String, Integer> resultMap = new HashMap<String, Integer>();
			
			connection = DBManager.getInstance().getConnection();
			String sql=Queries.QUERY_ONE;
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, description);
			pstmt.setString(2, name);
			pstmt.setString(3, type);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				resultMap.put(rs.getString(1), rs.getInt(2));
			}
			
			return resultMap;
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			close();
		}
		
		return null;
	}
	
	public List<String> getResultQueryTwo(String description) {
		
		try {
			List<String> resultList = new ArrayList<String>();
			connection = DBManager.getInstance().getConnection();
			String sql = Queries.QUERY_TWO;
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, description);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				resultList.add(rs.getString(1));
			}
			
			return resultList;
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} finally {
			close();
		}
		
		return null;
	}
	
	public List<Integer> getResultQueryThree(String clId, String muId, String dsName) {
		
		try {
			List<Integer> resultList = new ArrayList<Integer>();
			connection = DBManager.getInstance().getConnection();
			String sql = Queries.QUERY_THREE;
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, clId);
			pstmt.setString(2, muId);
			pstmt.setString(3, dsName);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				resultList.add(rs.getInt(1));
			}
			
			return resultList;
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	
	/**
	 * close the resources
	 */
	private void close() {
	
		try {
			DBManager.getInstance().releaseResources(connection, pstmt, rs);
			
		} catch (Exception e) {
			
		}
	}
}
