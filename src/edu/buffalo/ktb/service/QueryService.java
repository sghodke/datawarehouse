package edu.buffalo.ktb.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			return(new HashMap<String, Integer>());
		}
	}
	
public List<String> getResultQueryTwo(String description) {

        List<String> resultString = queryDAO.getResultQueryTwo(description);
        if(resultString != null) {
            return resultString;
        } else {
            return(new ArrayList<String>());
        }
    }
	

}
