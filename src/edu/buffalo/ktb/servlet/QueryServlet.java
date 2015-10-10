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

		String diseaseDescription, diseaseName, diseaseType, clusterId, measureUnitId, goId;
		String queryId = request.getParameter("button_id");
		System.out.println(queryId);
		
		switch(queryId){
		case "query1": 
			diseaseDescription = request.getParameter("description_query1");
			diseaseName = request.getParameter("name_query1");
			diseaseType = request.getParameter("type_query1");
			String resultQueryOne = queryService.getResultQueryOne(diseaseDescription, diseaseName, diseaseType);
			request.setAttribute("queryResult", resultQueryOne);
			break;
		case "query2": 
			diseaseDescription=request.getParameter("description_query2");
			String resultQueryTwo=queryService.getResultQueryTwo(diseaseDescription);
			request.setAttribute("queryResult", resultQueryTwo);
			break;
		case "query3": 
			diseaseName = request.getParameter("name_query3");
			clusterId = request.getParameter("cluster_id_query3");
			measureUnitId = request.getParameter("measure_unid_id_query3");
			String resultQueryThree=queryService.getResultQueryThree(clusterId, measureUnitId, diseaseName);
			request.setAttribute("queryResult", resultQueryThree);
			break;
		case "query4": 
			diseaseName = request.getParameter("name_query4");
			goId = request.getParameter("go_id_query4");
			String resultQueryFour = queryService.getResultQueryFour(goId, diseaseName);
			request.setAttribute("queryResult", resultQueryFour);
			break;
		case "query5": 
			String diseaseNameOne = request.getParameter("name1_query5");
			String diseaseNameTwo = request.getParameter("name2_query5");
			String diseaseNameThree = request.getParameter("name3_query5");
			String diseaseNameFour = request.getParameter("name3_query5");
			goId = request.getParameter("go_id_query5");
			String resultQueryFive = queryService.getResultQueryFive(goId, diseaseNameOne, diseaseNameTwo, diseaseNameThree, diseaseNameFour);
			request.setAttribute("queryResult", resultQueryFive);
			break;
		case "query6": 
			goId = request.getParameter("go_id_query6");
			diseaseNameOne = request.getParameter("name1_query6");
			diseaseNameTwo = request.getParameter("name2_query6");
			String correlation=queryService.getResultQuerySix(goId, diseaseNameOne, diseaseNameTwo);
			request.setAttribute("queryResult", correlation);
			break;
		case "query3A": 
			diseaseName = request.getParameter("name_query3A");
			String resultExpressions = queryService.getResultQueryPart3A(diseaseName);
			request.setAttribute("queryResult", resultExpressions);
			break;
			
		}
		request.getRequestDispatcher("/Home.jsp").forward(request, response);		


	}

}
