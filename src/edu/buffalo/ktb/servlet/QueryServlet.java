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

		String deseaseDescription, deseaseName, deseaseType, clusterId, measureUnitId, goId;

		if (request.getParameter("button_query1") != null) {
			deseaseDescription = request.getParameter("description_query1");
			deseaseName = request.getParameter("name_query1");
			deseaseType = request.getParameter("type_query1");
			Map<String, Integer> resultQueryOne = queryService.getResultQueryOne(deseaseDescription, deseaseName, deseaseType);
			Set<String> keys = resultQueryOne.keySet();
			for(String key: keys) {
				System.out.println(key + ": " + resultQueryOne.get(key));
			}
		} else if (request.getParameter("button_query2") != null) {
			deseaseDescription=request.getParameter("description_query2");
			List<String> resultQueryTwo=queryService.getResultQueryTwo(deseaseDescription);
			System.out.println("Total number of results: "+resultQueryTwo.size());
			System.out.println("Drug Types:-->\n");
			for(String str:resultQueryTwo){
				System.out.println(str);
			}
		} else if (request.getParameter("button_query3") != null) {
			deseaseName = request.getParameter("name_query3");
			clusterId = request.getParameter("cluster_id_query3");
			measureUnitId = request.getParameter("measure_unid_id_query3");
			List<Integer> resultQueryThree=queryService.getResultQueryThree(clusterId, measureUnitId, deseaseName);
			System.out.println("Total number of results: "+resultQueryThree.size());
			System.out.println("mRNA values:-->\n");
			for(int i:resultQueryThree){
				System.out.println(i);
			}
		} else if (request.getParameter("button_query4") != null){
			deseaseName = request.getParameter("name_query4");
			goId = request.getParameter("go_id_query4");
			Double resultQueryFour = queryService.getResultQueryFour(goId, deseaseName);
			System.out.println("T Stat " + resultQueryFour);
		} else if(request.getParameter("button_query5") != null){
			String deseaseNameOne = request.getParameter("name1_query5");
			String deseaseNameTwo = request.getParameter("name2_query5");
			String deseaseNameThree = request.getParameter("name3_query5");
			String deseaseNameFour = request.getParameter("name3_query5");
			goId = request.getParameter("go_id_query5");
			Double resultQueryFive = queryService.getResultQueryFive(goId, deseaseNameOne, deseaseNameTwo, deseaseNameThree, deseaseNameFour);
			System.out.println("F Stat " + resultQueryFive);
		} else if(request.getParameter("button_query6") != null) { 
			goId = request.getParameter("go_id_query6");
			double[] correlation=queryService.getResultQuerySix(goId);
			if(correlation!=null){
				System.out.println("Correlation between two patients with ALL is-->"+correlation[0]);
				System.out.println("Correlation between two patients with ALL and AML is-->"+correlation[1]);
			}
		}
	}

}
