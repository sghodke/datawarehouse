package edu.buffalo.ktb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.inference.OneWayAnova;
import org.apache.commons.math3.stat.inference.TTest;

import edu.buffalo.ktb.dao.QueryDAO;

public class QueryService {

	QueryDAO queryDAO = new QueryDAO();

	public Map<String, Integer> getResultQueryOne(String description, String name, String type) {

		Map<String, Integer> resultMap = queryDAO.getResultQueryOne(description, name, type);

		if (resultMap != null) {
			return resultMap;
		} else {
			return (new HashMap<String, Integer>());
		}
	}

	public List<String> getResultQueryTwo(String description) {

		List<String> resultList = queryDAO.getResultQueryTwo(description);
		if (resultList != null) {
			return resultList;
		} else {
			return (new ArrayList<String>());
		}
	}

	public List<Integer> getResultQueryThree(String clId, String muId, String dsName) {

		List<Integer> resultList = queryDAO.getResultQueryThree(clId, muId, dsName);
		if (resultList != null) {
			return resultList;
		} else {
			return (new ArrayList<Integer>());
		}
	}

	public double getResultQueryFour(String goId, String dsName) {
		List<List<Integer>> resultList = queryDAO.getResultQueryFour(goId, dsName);
		List<Integer> patientsWithALL = resultList.get(0);
		List<Integer> patientsWithoutALL = resultList.get(1);

		TTest ttest = new TTest();

		double[] list1 = new double[patientsWithALL.size()];
		int i = 0;
		for (Integer exp : patientsWithALL) {
			list1[i++] = exp.doubleValue();
		}

		double[] list2 = new double[patientsWithoutALL.size()];
		i = 0;
		for (Integer exp : patientsWithoutALL) {
			list2[i++] = exp.doubleValue();
		}

		double tstat = ttest.homoscedasticT(list1, list2);
		return tstat;
	}

	public double getResultQueryFive(String goId, String dsNameOne, String dsNameTwo, String dsNameThree,
			String dsNameFour) {
		List<String> dsNames = new ArrayList<String>();
		dsNames.add(dsNameOne);
		dsNames.add(dsNameTwo);
		dsNames.add(dsNameThree);
		dsNames.add(dsNameFour);

		List<List<Integer>> resultList = queryDAO.getResultQueryFive(goId, dsNames);
		Collection<double[]> categoryData = new ArrayList<double[]>();

		double[] list;
		for (List<Integer> expList : resultList) {
			list = new double[expList.size()];
			int i = 0;
			for (Integer exp : expList) {
				list[i++] = exp.doubleValue();
			}
			categoryData.add(list);
		}

		OneWayAnova onw = new OneWayAnova();
		double fstat = onw.anovaFValue(categoryData);
		return fstat;
	}

	public void getResultQueryPart3A() {
		List<Map<String, List<Integer>>> resultList = queryDAO.getResultPart3QueryOne("");

		Map<String, List<Integer>> diseaseGeneMap;
		Map<String, List<Integer>> notDiseaseGeneMap;
		if (resultList == null || resultList.size() <= 1) {
			return;
		}

		TTest ttest = new TTest();
		List<String> infoGenes = new ArrayList<String>();
		diseaseGeneMap = resultList.get(0);
		notDiseaseGeneMap = resultList.get(1);

		Set<String> geneSet = diseaseGeneMap.keySet();
		Map<String, Double> geneTStats = new HashMap<String, Double>();
		for (String gene : geneSet) {
			List<Integer> posExp = diseaseGeneMap.get(gene);
			List<Integer> negExp = notDiseaseGeneMap.get(gene);

			double[] posList = new double[posExp.size()];
			int i = 0;
			for (Integer exp : posExp) {
				posList[i++] = exp.doubleValue();
			}
			double[] negList = new double[negExp.size()];
			i = 0;
			for (Integer exp : negExp) {
				negList[i++] = exp.doubleValue();
			}

			double pstat = ttest.homoscedasticTTest(posList, negList);
			double tstat = ttest.homoscedasticT(posList, negList);
			geneTStats.put(gene, tstat);
			if (pstat < 0.01) {
				infoGenes.add(gene);
			}
		}
		System.out.println("\nTotal info genes: " + infoGenes.size());

		StringBuilder sb = new StringBuilder();
		String prefix = "";
		for (String gene : infoGenes) {
			sb.append(prefix + "'" + gene + "'");
			prefix = ",";
		}
		String informativeGenes = sb.toString();
		System.out.println("\n##############################\n");
		getResultQueryPart3B(informativeGenes);

	}

	public void getResultQueryPart3B(String informativeGenes) {
		queryDAO.getResultQueryPart3B(informativeGenes);
	}

	public double[] getResultQuerySix(String goId) {
		double[] correlation = queryDAO.getResultQuerySixPart1(goId);
		return correlation;
	}

}
