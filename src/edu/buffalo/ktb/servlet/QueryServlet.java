package edu.buffalo.ktb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.buffalo.ktb.bean.Patient;
import edu.buffalo.ktb.service.QueryService;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QueryService queryService = new QueryService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Patient> patientList = queryService.getPatients();
		System.out.println(patientList.size() + "\n");
		String queryNo=request.getParameter("queryNo");
		
		switch (queryNo) {
        case "1":
            String description = request.getParameter("description");
            String name = request.getParameter("name");
            String type = request.getParameter("type");
            Map<String, Integer> resultQueryOne = queryService.getResultQueryOne(description, name, type);
            Set<String> keys = resultQueryOne.keySet();
            for(String key: keys) {
                System.out.println(key + ": " + resultQueryOne.get(key));
            }
            break;
        case "2":
            String description1=request.getParameter("description2");
            List<String> resultQueryTwo=queryService.getResultQueryTwo(description1);
            System.out.println("Total number of results"+resultQueryTwo.size());
            System.out.println("Drug Types:-->\n");
            for(String str:resultQueryTwo){
                System.out.println(str);
            }
            break;
            
        case "4":
        	double tstat = queryService.getResultQueryFour("", "");
        	System.out.println("T-stat: " + tstat);
        	break;
        	
        case "5":
        	String goId = request.getParameter("goId");
        	String dsNameOne = request.getParameter("dsNameOne");
        	String dsNameTwo = request.getParameter("dsNameTwo");
        	String dsNameThree = request.getParameter("dsNameThree");
        	String dsNameFour = request.getParameter("dsNameFour");
        	double fstat = queryService.getResultQueryFive(goId, dsNameOne, dsNameTwo, dsNameThree, dsNameFour);
        	System.out.println("F-stat:" + fstat);
        	break;
        	
        default:
            break;
        }
		
		
	}

}
