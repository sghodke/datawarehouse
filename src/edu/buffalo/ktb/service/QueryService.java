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

    public String getResultQueryOne(String description, String name,
            String type) {

        Map<String, Integer> resultMap = queryDAO
                .getResultQueryOne(description, name, type);
        StringBuffer buffer = new StringBuffer();
        for (String key : resultMap.keySet()) {
            buffer.append(key + ": " + resultMap.get(key));
            buffer.append("\n");
        }
        return buffer.toString();

    }

    public String getResultQueryTwo(String description) {
        StringBuffer buffer = new StringBuffer();
        List<String> resultList = queryDAO
                .getResultQueryTwo(description);
        buffer.append("\nTotal number of Drug of Type:" + description
                + " are " + resultList.size());
        buffer.append("\nDrug Types:-->\n");
        for (String str : resultList) {
            System.out.println(str);
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public String getResultQueryThree(String clId, String muId,
            String dsName) {

        List<Integer> resultList = queryDAO.getResultQueryThree(clId,
                muId, dsName);
        StringBuffer buffer = new StringBuffer();
        buffer.append("\nTotal number of results: " + resultList.size());
        buffer.append("\n");
        buffer.append("mRNA values:-->\n");
        for (int i : resultList) {
            buffer.append(i);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public String getResultQueryFour(String goId, String dsName) {
        StringBuffer buffer = new StringBuffer();
        List<List<Integer>> resultList = queryDAO
                .getResultQueryFour(goId, dsName);
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
        buffer.append("\nT Stat value is--> " + tstat);
        return buffer.toString();
    }

    public String getResultQueryFive(String goId, String dsNameOne,
            String dsNameTwo, String dsNameThree, String dsNameFour) {
        StringBuffer buffer = new StringBuffer();
        List<String> dsNames = new ArrayList<String>();
        dsNames.add(dsNameOne);
        dsNames.add(dsNameTwo);
        dsNames.add(dsNameThree);
        dsNames.add(dsNameFour);

        List<List<Integer>> resultList = queryDAO
                .getResultQueryFive(goId, dsNames);
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
        buffer.append("\nF Stat value is--> " + fstat);
        return buffer.toString();
    }

    public String getResultQueryPart3A(String diseaseName) {
        StringBuffer buffer = new StringBuffer();
        List<Map<String, List<Integer>>> resultList = queryDAO
                .getResultPart3QueryOne(diseaseName);

        Map<String, List<Integer>> diseaseGeneMap;
        Map<String, List<Integer>> notDiseaseGeneMap;
        if (resultList == null || resultList.size() <= 1) {
            return "";
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
        for(String geneStr:geneTStats.keySet()){
            buffer.append("\nGene: "+geneStr+", "+"T-value: "+geneTStats.get(geneStr));
        }
        buffer.append("\n\nTotal info genes: " + infoGenes.size());
        buffer.append("\n");
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (String gene : infoGenes) {
            sb.append(prefix + "'" + gene + "'");
            prefix = ",";
        }
        String informativeGenes = sb.toString();
        buffer.append("\n\nInformative Genes: \n");
        for(String infoGene : infoGenes){
            buffer.append(infoGene);
            buffer.append("\n");
        }
        String result = getResultQueryPart3B(informativeGenes,
                diseaseName);
        buffer.append(result);
        return buffer.toString();
    }

    public String getResultQueryPart3B(String informativeGenes,
            String disease) {
        String result = queryDAO
                .getResultQueryPart3B(informativeGenes, disease);
        return result;
    }

    public String getResultQuerySix(String goId, String disease1,
            String disease2) {
        StringBuffer buffer = new StringBuffer();
        double[] correlation = queryDAO.getResultQuerySixPart1(goId,
                disease1, disease2);
        buffer.append(
                "\nCorrelation between two patients with ALL is --> "
                        + correlation[0]);
        buffer.append("\n");
        buffer.append(
                "Correlation between two patients with ALL and AML is --> "
                        + correlation[1]);
        return buffer.toString();
    }

}
