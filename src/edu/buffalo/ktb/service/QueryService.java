package edu.buffalo.ktb.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.inference.TTest;

import edu.buffalo.ktb.bean.Patient;
import edu.buffalo.ktb.dao.QueryDAO;

public class QueryService {
	
	QueryDAO queryDAO = new QueryDAO();
	
	public List<Patient> getPatients() {
		
		List<Patient> patientList = queryDAO.getPatients();
		
		if(patientList != null) {
			return patientList;
		} else {
			return(new ArrayList<Patient>());
		}
	}
	
	public Map<String, Integer> getResultQueryOne(String description, String name, String type) {
		
		Map<String, Integer> resultMap = queryDAO.getResultQueryOne(description, name, type);
		
		if(resultMap != null) {
			return resultMap;
		} else {
			return (new HashMap<String, Integer>());
		}
	}
	
	public List<String> getResultQueryTwo(String description) {

        List<String> resultList = queryDAO.getResultQueryTwo(description);
        if(resultList != null) {
            return resultList;
        } else {
            return (new ArrayList<String>());
        }
    }
	
	public List<Integer> getResultQueryThree(String clId, String muId, String dsName) {
		
		List<Integer> resultList = queryDAO.getResultQueryThree(clId, muId, dsName);
		if(resultList != null) {
			return resultList;
		} else {
			return (new ArrayList<Integer>());
		}
	}
	
	public void getResultQueryFour(String goId, String dsName) {
		List<List<Integer>> resultList = queryDAO.getResultQueryFour(goId, dsName);
		List<Integer> patientsWithALL = resultList.get(0);
		List<Integer> patientsWithoutALL = resultList.get(1);
		
		TTest ttest = new TTest();
		
		double[] list1 = new double[patientsWithALL.size()];
		//patientsWithALL.toArray(list1);
		int i = 0;
		for(Integer exp: patientsWithALL) {
			list1[i++] = exp.doubleValue();
		}
		double[] list2 = new double[patientsWithoutALL.size()];
		//patientsWithALL.toArray(list1);
		i = 0;
		for(Integer exp: patientsWithoutALL) {
			list2[i++] = exp.doubleValue();
		}
		double tvalue = ttest.homoscedasticT(list1, list2);
		System.out.println("T-stat: " + tvalue);
	}

}
