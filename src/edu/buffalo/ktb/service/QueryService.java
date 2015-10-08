package edu.buffalo.ktb.service;


import java.util.ArrayList;
import java.util.List;

import edu.buffalo.ktb.bean.Patient;
import edu.buffalo.ktb.dao.QueryDAO;

public class QueryService {
	
	QueryDAO queryDAO = new QueryDAO();
	
	public List<Patient> getPatients() {
		
		List<Patient> patientList;
		patientList = queryDAO.getPatients();
		
		if(patientList != null) {
			return patientList;
		} else {
			return(new ArrayList<Patient>());
		}
	}
	

}
