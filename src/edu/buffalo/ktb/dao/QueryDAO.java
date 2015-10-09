package edu.buffalo.ktb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

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
	
	public List<List<Integer>> getResultQueryFour(String goId, String dsName) {
		
		try {
			List<Integer> resultSubQuery1 = new ArrayList<Integer>();
			List<Integer> resultSubQuery2 = new ArrayList<Integer>();
			connection = DBManager.getInstance().getConnection();
			String sql = Queries.QUERY_FOUR_A;
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, goId);
			pstmt.setString(2, dsName);
			rs = pstmt.executeQuery();
			rs.setFetchSize(5000);
			while(rs.next()) {
				resultSubQuery1.add(rs.getInt(1));
			}
			rs.close();
			
			sql = Queries.QUERY_FOUR_B;
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, goId);
			pstmt.setString(2, dsName);
			rs = pstmt.executeQuery();
			rs.setFetchSize(5000);
			while(rs.next()) {
				resultSubQuery2.add(rs.getInt(1));
			}
			
			List<List<Integer>> resultList = new ArrayList<List<Integer>>();
			resultList.add(resultSubQuery1);
			resultList.add(resultSubQuery2);
			return resultList;
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	public List<List<Integer>> getResultQueryFive(String goId, List<String> dsNames) {
		
		try {
			connection = DBManager.getInstance().getConnection();
			String sql = Queries.QUERY_FIVE;
			pstmt = connection.prepareStatement(sql);
			
			List<List<Integer>> resultList = new ArrayList<List<Integer>>();
			List<Integer> resultSubQuery;
			for(String dsName: dsNames) {
				resultSubQuery = new ArrayList<Integer>();
				pstmt.setString(1, goId);
				pstmt.setString(2, dsName.toUpperCase());
				rs = pstmt.executeQuery();
				rs.setFetchSize(4000);
				while(rs.next()) {
					resultSubQuery.add(rs.getInt(1));
				}
				rs.close();
				resultList.add(resultSubQuery);
			}

			return resultList;
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	/*
	 * Harwani
	 */
	
	public double[] getResultQuerySixPart1(String goId) {
	    Map<String,List<Double>> patientExpressionMapALL=new HashMap<String,List<Double>>();
	    try {
            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_SIX_ALL;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, goId);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String patientId=rs.getString(1);
                Double expression=Double.valueOf(rs.getInt(2));
                if(patientExpressionMapALL.containsKey(patientId)){
                    List<Double> expressionList=patientExpressionMapALL.get(patientId);
                    expressionList.add(expression);
                }
                else{
                    List<Double> expressionList=new ArrayList<Double>();
                    expressionList.add(expression);
                    patientExpressionMapALL.put(patientId, expressionList);
                }
                System.out.println(rs.getString(1));
                System.out.println(rs.getInt(2));
            }
            double correlationALL=calculateJoinAndCorrelationALL(patientExpressionMapALL);
            double correlationALLAML=getResultQuerySixPart2(goId,patientExpressionMapALL);
            double[] correlation=new double[2];
            correlation[0]=correlationALL;
            correlation[1]=correlationALLAML;
            return correlation;
        } catch(Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }
	    return null;
	   
	}
	
	
	public double getResultQuerySixPart2(String goId,Map<String,List<Double>> patientExpressionMapALL) {
        Map<String,List<Double>> patientExpressionMapAML=new HashMap<String,List<Double>>();
        try {
            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_SIX_AML;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, goId);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                String patientId=rs.getString(1);
                Double expression=Double.valueOf(rs.getInt(2));
                if(patientExpressionMapAML.containsKey(patientId)){
                    List<Double> expressionList=patientExpressionMapAML.get(patientId);
                    expressionList.add(expression);
                }
                else{
                    List<Double> expressionList=new ArrayList<Double>();
                    expressionList.add(expression);
                    patientExpressionMapAML.put(patientId, expressionList);
                }
                System.out.println(rs.getString(1));
                System.out.println(rs.getInt(2));
            }
            return calculateJoinAndCorrelationAML(patientExpressionMapALL,patientExpressionMapAML);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }
        return 0.0;
    }
	
	public double calculateJoinAndCorrelationALL(Map<String,List<Double>> patientExpressionMap){
	    List<String> patientIds = new ArrayList<>(patientExpressionMap.keySet());
	    List<Double> correlationList = new ArrayList<Double>();
	    for(int i=0;i<patientExpressionMap.size();i++){
	        for(int j=i+1;j<patientExpressionMap.size();j++){
	            List<Double> patient1=patientExpressionMap.get(patientIds.get(i));
	            List<Double> patient2=patientExpressionMap.get(patientIds.get(j));
	            double[] patient1Array=convertDoubleListToArray(patient1);
	            double[] patient2Array=convertDoubleListToArray(patient2);
	            PearsonsCorrelation pearsonsCorrelation=new PearsonsCorrelation();
	            double correlation=pearsonsCorrelation.correlation(patient1Array, patient2Array);
	            correlationList.add(correlation);
	        }
	    }
	    return calculateAverage(correlationList);
	}
	
	
	
	public double calculateJoinAndCorrelationAML(Map<String,List<Double>> patientExpressionMapALL,Map<String,List<Double>> patientExpressionMapAML){
        List<String> patientIdsALL = new ArrayList<>(patientExpressionMapALL.keySet());
        List<String> patientIdsAML = new ArrayList<>(patientExpressionMapAML.keySet());
        List<Double> correlationList = new ArrayList<Double>();
        for(int i=0;i<patientExpressionMapALL.size();i++){
            for(int j=0;j<patientExpressionMapAML.size();j++){
                List<Double> patient1=patientExpressionMapALL.get(patientIdsALL.get(i));
                List<Double> patient2=patientExpressionMapAML.get(patientIdsAML.get(j));
                double[] patient1Array=convertDoubleListToArray(patient1);
                double[] patient2Array=convertDoubleListToArray(patient2);
                PearsonsCorrelation pearsonsCorrelation=new PearsonsCorrelation();
                double correlation=pearsonsCorrelation.correlation(patient1Array, patient2Array);
                correlationList.add(correlation);
            }
        }
        return calculateAverage(correlationList);
    }
	
	public double[] convertDoubleListToArray(List<Double> list){
	    double[] array=new double[list.size()];
	    for(int i=0;i<list.size();i++){
	        array[i]=list.get(i);
	    }
	    return array;
	}
	
	
	public double calculateAverage(List<Double> correlationList){
	    double sum=0.0;
	    for(double d:correlationList){
	        sum+=d;
	    }
	    return sum/correlationList.size();
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
