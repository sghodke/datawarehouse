package edu.buffalo.ktb.dao;

import java.nio.Buffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.inference.TTest;

import edu.buffalo.ktb.db.DBManager;
import edu.buffalo.ktb.util.Queries;

public class QueryDAO {

    private Connection connection;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public Map<String, Integer> getResultQueryOne(String description,
            String name, String type) {

        try {
            Map<String, Integer> resultMap = new HashMap<String, Integer>();

            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_ONE;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, description);
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                resultMap.put(rs.getString(1), rs.getInt(2));
            }

            return resultMap;

        } catch (Exception e) {
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

            while (rs.next()) {
                resultList.add(rs.getString(1));
            }

            return resultList;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }

        return null;
    }

    public List<Integer> getResultQueryThree(String clId, String muId,
            String dsName) {

        try {
            List<Integer> resultList = new ArrayList<Integer>();
            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_THREE;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clId);
            pstmt.setString(2, muId);
            pstmt.setString(3, dsName);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                resultList.add(rs.getInt(1));
            }

            return resultList;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public List<List<Integer>> getResultQueryFour(String goId,
            String dsName) {

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
            while (rs.next()) {
                resultSubQuery1.add(rs.getInt(1));
            }
            rs.close();

            sql = Queries.QUERY_FOUR_B;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, goId);
            pstmt.setString(2, dsName);
            rs = pstmt.executeQuery();
            rs.setFetchSize(5000);
            while (rs.next()) {
                resultSubQuery2.add(rs.getInt(1));
            }

            List<List<Integer>> resultList = new ArrayList<List<Integer>>();
            resultList.add(resultSubQuery1);
            resultList.add(resultSubQuery2);
            return resultList;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public List<List<Integer>> getResultQueryFive(String goId,
            List<String> dsNames) {

        try {
            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_FIVE;
            pstmt = connection.prepareStatement(sql);

            List<List<Integer>> resultList = new ArrayList<List<Integer>>();
            List<Integer> resultSubQuery;
            for (String dsName : dsNames) {
                resultSubQuery = new ArrayList<Integer>();
                pstmt.setString(1, goId);
                pstmt.setString(2, dsName.toUpperCase());
                rs = pstmt.executeQuery();
                rs.setFetchSize(4000);
                while (rs.next()) {
                    resultSubQuery.add(rs.getInt(1));
                }
                rs.close();
                resultList.add(resultSubQuery);
            }

            return resultList;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }

        return null;
    }

    public List<Map<String, List<Integer>>> getResultPart3QueryOne(
            String dsName) {

        try {
            Map<String, List<Integer>> diseaseExpMap = new HashMap<String, List<Integer>>();
            Map<String, List<Integer>> notDiseaseExpMap = new HashMap<String, List<Integer>>();
            List<Integer> expList = null;

            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_PART3_ONE_A;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, dsName);
            rs = pstmt.executeQuery();
            rs.setFetchSize(5000);

            String geneId = null;
            String lastGeneId = "";
            while (rs.next()) {
                geneId = rs.getString(1);
                int exp = rs.getInt(2);
                if (!geneId.equals(lastGeneId)) {
                    if (expList != null && expList.size() != 0) {
                        diseaseExpMap.put(lastGeneId, expList);
                    }
                    expList = new ArrayList<Integer>();
                    lastGeneId = geneId;
                }
                if (expList != null) {
                    expList.add(exp);
                }
            }

            if (geneId != null && expList != null
                    && expList.size() != 0) {
                diseaseExpMap.put(geneId, expList);
            }

            sql = Queries.QUERY_PART3_ONE_B;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, dsName);
            rs = pstmt.executeQuery();
            rs.setFetchSize(5000);

            expList = null;
            geneId = null;
            lastGeneId = "";
            while (rs.next()) {
                geneId = rs.getString(1);
                int exp = rs.getInt(2);
                if (!geneId.equals(lastGeneId)) {
                    if (expList != null && expList.size() != 0) {
                        notDiseaseExpMap.put(lastGeneId, expList);
                    }
                    expList = new ArrayList<Integer>();
                    lastGeneId = geneId;
                }
                if (expList != null) {
                    expList.add(exp);
                }
            }

            if (geneId != null && expList != null
                    && expList.size() != 0) {
                notDiseaseExpMap.put(geneId, expList);
            }

            List<Map<String, List<Integer>>> resultList = new ArrayList<Map<String, List<Integer>>>();
            resultList.add(diseaseExpMap);
            resultList.add(notDiseaseExpMap);
            return resultList;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    /*
     * Harwani
     */

    public double[] getResultQuerySixPart1(String goId,
            String disease1, String disease2) {
        Map<String, List<Double>> patientExpressionMapALL = new HashMap<String, List<Double>>();
        try {
            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_SIX_ALL;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, disease1);
            pstmt.setString(2, goId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String patientId = rs.getString(1);
                Double expression = Double.valueOf(rs.getInt(2));
                if (patientExpressionMapALL.containsKey(patientId)) {
                    List<Double> expressionList = patientExpressionMapALL
                            .get(patientId);
                    expressionList.add(expression);
                } else {
                    List<Double> expressionList = new ArrayList<Double>();
                    expressionList.add(expression);
                    patientExpressionMapALL.put(patientId,
                            expressionList);
                }
            }
            double correlationALL = calculateJoinAndCorrelationALL(
                    patientExpressionMapALL);
            double correlationALLAML = getResultQuerySixPart2(goId,
                    patientExpressionMapALL, disease2);
            double[] correlation = new double[2];
            correlation[0] = correlationALL;
            correlation[1] = correlationALLAML;
            return correlation;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }
        return null;

    }

    public double getResultQuerySixPart2(String goId,
            Map<String, List<Double>> patientExpressionMapALL,
            String disease2) {
        Map<String, List<Double>> patientExpressionMapAML = new HashMap<String, List<Double>>();
        try {
            connection = DBManager.getInstance().getConnection();
            String sql = Queries.QUERY_SIX_AML;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, disease2);
            pstmt.setString(2, goId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String patientId = rs.getString(1);
                Double expression = Double.valueOf(rs.getInt(2));
                if (patientExpressionMapAML.containsKey(patientId)) {
                    List<Double> expressionList = patientExpressionMapAML
                            .get(patientId);
                    expressionList.add(expression);
                } else {
                    List<Double> expressionList = new ArrayList<Double>();
                    expressionList.add(expression);
                    patientExpressionMapAML.put(patientId,
                            expressionList);
                }
            }
            return calculateJoinAndCorrelationAML(
                    patientExpressionMapALL, patientExpressionMapAML);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }
        return 0.0;
    }

    public double calculateJoinAndCorrelationALL(
            Map<String, List<Double>> patientExpressionMap) {
        List<String> patientIds = new ArrayList<>(
                patientExpressionMap.keySet());
        List<Double> correlationList = new ArrayList<Double>();
        for (int i = 0; i < patientExpressionMap.size(); i++) {
            for (int j = i + 1; j < patientExpressionMap
                    .size(); j++) {
                List<Double> patient1 = patientExpressionMap
                        .get(patientIds.get(i));
                List<Double> patient2 = patientExpressionMap
                        .get(patientIds.get(j));
                double[] patient1Array = convertDoubleListToArray(
                        patient1);
                double[] patient2Array = convertDoubleListToArray(
                        patient2);
                PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
                double correlation = pearsonsCorrelation
                        .correlation(patient1Array, patient2Array);
                correlationList.add(correlation);
            }
        }
        return calculateAverage(correlationList);
    }

    public double calculateJoinAndCorrelationAML(
            Map<String, List<Double>> patientExpressionMapALL,
            Map<String, List<Double>> patientExpressionMapAML) {
        List<String> patientIdsALL = new ArrayList<>(
                patientExpressionMapALL.keySet());
        List<String> patientIdsAML = new ArrayList<>(
                patientExpressionMapAML.keySet());
        List<Double> correlationList = new ArrayList<Double>();
        for (int i = 0; i < patientExpressionMapALL.size(); i++) {
            for (int j = 0; j < patientExpressionMapAML.size(); j++) {
                List<Double> patient1 = patientExpressionMapALL
                        .get(patientIdsALL.get(i));
                List<Double> patient2 = patientExpressionMapAML
                        .get(patientIdsAML.get(j));
                double[] patient1Array = convertDoubleListToArray(
                        patient1);
                double[] patient2Array = convertDoubleListToArray(
                        patient2);
                PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
                double correlation = pearsonsCorrelation
                        .correlation(patient1Array, patient2Array);
                correlationList.add(correlation);
            }
        }
        return calculateAverage(correlationList);
    }

    public double[] convertDoubleListToArray(List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public double calculateAverage(List<Double> correlationList) {
        double sum = 0.0;
        for (double d : correlationList) {
            sum += d;
        }
        return sum / correlationList.size();
    }

    public String getResultQueryPart3B(String informativeGenes,
            String disease) {

        // buffer for results
        StringBuffer stringBuffer = new StringBuffer();

        // Map for patients having all disease
        Map<String, Map<String, Integer>> patientGeneExpressionMapWithALL = new HashMap<String, Map<String, Integer>>();
        // Map for patients not having all disease
        Map<String, Map<String, Integer>> patientGeneExpressionMapWithNotALL = new HashMap<String, Map<String, Integer>>();
        try {
            // section of patients having all disease
            connection = DBManager.getInstance().getConnection();
            String sql = "select dg.P_ID,pr.U_ID,mf.EXP from MICROARRAY_FACT mf, DISEASE ds, DIAGNOSIS dg, CLINICAL_SAMPLE cs,PROBE pr"
                    + " where mf.S_ID=cs.S_ID and ds.DS_ID=dg.DS_ID and dg.P_ID=cs.P_ID and mf.PB_ID=pr.PB_ID  and ds.NAME=? and mf.PB_ID in ("
                    + " select pr.PB_ID from PROBE pr,GENE_FACT gf where gf.U_ID=pr.U_ID) and pr.U_ID in ("
                    + informativeGenes
                    + ") order by dg.P_ID, pr.U_ID";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, disease);
            rs = pstmt.executeQuery();
            rs.setFetchSize(5000);
            while (rs.next()) {
                String patientId = rs.getString(1);
                String uId = rs.getString(2);
                int expression = rs.getInt(3);
                if (patientGeneExpressionMapWithALL
                        .containsKey(patientId)) {
                    Map<String, Integer> geneExpressionMap = patientGeneExpressionMapWithALL
                            .get(patientId);
                    geneExpressionMap.put(uId, expression);
                } else {
                    Map<String, Integer> geneExpressionMap = new HashMap<String, Integer>();
                    geneExpressionMap.put(uId, expression);
                    patientGeneExpressionMapWithALL.put(patientId,
                            geneExpressionMap);
                }
            }
            rs.close();
            // section of patients not having all disease
            sql = "select dg.P_ID,pr.U_ID,mf.EXP from MICROARRAY_FACT mf, DISEASE ds, DIAGNOSIS dg, CLINICAL_SAMPLE cs,PROBE pr"
                    + " where mf.S_ID=cs.S_ID and ds.DS_ID=dg.DS_ID and dg.P_ID=cs.P_ID and mf.PB_ID=pr.PB_ID  and ds.NAME!=? and mf.PB_ID in ("
                    + " select pr.PB_ID from PROBE pr,GENE_FACT gf where gf.U_ID=pr.U_ID) and pr.U_ID in ("
                    + informativeGenes
                    + ") order by dg.P_ID, pr.U_ID";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, disease);
            rs = pstmt.executeQuery();
            rs.setFetchSize(5000);
            while (rs.next()) {
                String patientId = rs.getString(1);
                String uId = rs.getString(2);
                int expression = rs.getInt(3);
                if (patientGeneExpressionMapWithNotALL
                        .containsKey(patientId)) {
                    Map<String, Integer> geneExpressionMap = patientGeneExpressionMapWithNotALL
                            .get(patientId);
                    geneExpressionMap.put(uId, expression);
                } else {
                    Map<String, Integer> geneExpressionMap = new HashMap<String, Integer>();
                    geneExpressionMap.put(uId, expression);
                    patientGeneExpressionMapWithNotALL.put(patientId,
                            geneExpressionMap);
                }
            }
            rs.close();
            // /test patient section
            sql = "select * from TEST_SAMPLE ts where ts.U_ID in ("
                    + informativeGenes + ") order by ts.U_ID";
            pstmt = connection.prepareStatement(sql);
            // pstmt.setString(1, informativeGenes);
            rs = pstmt.executeQuery();
            rs.setFetchSize(5000);

            List<Map<String, Map<String, Integer>>> mapList = new ArrayList<Map<String, Map<String, Integer>>>();
            for (int i = 0; i < 5; i++) {
                Map<String, Map<String, Integer>> patientGeneExpressionMap = new HashMap<String, Map<String, Integer>>();
                patientGeneExpressionMap.put("test" + i,
                        new HashMap<String, Integer>());
                mapList.add(patientGeneExpressionMap);
            }
            Map<String, Integer> GeneExpressionMap1 = null;
            Map<String, Integer> GeneExpressionMap2 = null;
            Map<String, Integer> GeneExpressionMap3 = null;
            Map<String, Integer> GeneExpressionMap4 = null;
            Map<String, Integer> GeneExpressionMap5 = null;
            while (rs.next()) {
                String uId = rs.getString(1);
                int exp1 = rs.getInt(2);
                int exp2 = rs.getInt(3);
                int exp3 = rs.getInt(4);
                int exp4 = rs.getInt(5);
                int exp5 = rs.getInt(6);
                GeneExpressionMap1 = mapList.get(0).get("test0");
                GeneExpressionMap1.put(uId, exp1);

                GeneExpressionMap2 = mapList.get(1).get("test1");
                GeneExpressionMap2.put(uId, exp2);

                GeneExpressionMap3 = mapList.get(2).get("test2");
                GeneExpressionMap3.put(uId, exp3);

                GeneExpressionMap4 = mapList.get(3).get("test3");
                GeneExpressionMap4.put(uId, exp4);

                GeneExpressionMap5 = mapList.get(4).get("test4");
                GeneExpressionMap5.put(uId, exp5);

            }
            rs.close();
            // /creating the double array of five test patients
            List<double[]> patientArrayList = new ArrayList<double[]>();
            for (int i = 0; i < 5; i++) {
                double[] patientArray = new double[mapList.get(i)
                        .get("test" + i).keySet().size()];
                int k = 0;
                for (String uId : mapList.get(i).get("test" + i)
                        .keySet()) {
                    patientArray[k] = Double.valueOf(
                            mapList.get(i).get("test" + i).get(uId));
                    k++;
                }
                patientArrayList.add(patientArray);
            }

            List<double[]> raArrayList = new ArrayList<double[]>();
            List<double[]> rbArrayList = new ArrayList<double[]>();

            // calculating correlation of each patient with group of all
            // patients this will give array of ra's(correlation) for five
            // patients
            for (int i = 0; i < patientArrayList.size(); i++) {
                double[] correlationArray = new double[patientGeneExpressionMapWithALL
                        .size()];
                int k = 0;
                for (String patientId : patientGeneExpressionMapWithALL
                        .keySet()) {

                    Map<String, Integer> geneExpressionMap = patientGeneExpressionMapWithALL
                            .get(patientId);

                    double[] trainingPatientArray = new double[geneExpressionMap
                            .size()];
                    int h = 0;
                    for (String geneId : geneExpressionMap.keySet()) {
                        trainingPatientArray[h] = geneExpressionMap
                                .get(geneId);
                        h++;
                    }
                    PearsonsCorrelation correlation = new PearsonsCorrelation();
                    double corr = correlation.correlation(
                            patientArrayList.get(i),
                            trainingPatientArray);
                    correlationArray[k] = corr;
                    k++;
                }
                raArrayList.add(correlationArray);
            }
            // calculating correlation of each patient with group of not all
            // patients this will give array of rb's(correlation) for five
            // patients
            for (int j = 0; j < patientArrayList.size(); j++) {

                double[] correlationArrayB = null;
                for (String patientId : patientGeneExpressionMapWithNotALL
                        .keySet()) {
                    int k = 0;
                    Map<String, Integer> geneExpressionMap = patientGeneExpressionMapWithNotALL
                            .get(patientId);
                    correlationArrayB = new double[patientGeneExpressionMapWithNotALL
                            .size()];
                    double[] trainingPatientArray = new double[geneExpressionMap
                            .size()];
                    int h = 0;
                    for (String geneId : geneExpressionMap.keySet()) {
                        trainingPatientArray[h] = geneExpressionMap
                                .get(geneId);
                        h++;
                    }
                    PearsonsCorrelation correlation = new PearsonsCorrelation();
                    double corr = correlation.correlation(
                            patientArrayList.get(j),
                            trainingPatientArray);
                    correlationArrayB[k] = corr;
                    k++;
                }
                rbArrayList.add(correlationArrayB);
            }
            int p = 0, q = 0;
            List<String> answersList = new ArrayList<String>();
            List<Double> tValueList = new ArrayList<Double>();
            while (p < raArrayList.size() && q < rbArrayList.size()) {
                double[] a = raArrayList.get(p);
                double[] b = rbArrayList.get(q);
                TTest ttTest = new TTest();
                double ans = ttTest.tTest(a, b);
                tValueList.add(ans);
                if (ans < 0.01) {
                    answersList.add("ALL");
                } else {
                    answersList.add("NOT ALL");
                }
                p++;
                q++;
            }
            int u = 0;
            for (double d : tValueList) {
                stringBuffer.append("The t-value for patient-" + u);
                stringBuffer.append(d);
                stringBuffer.append("\n");
                u++;
            }
            int y = 0;
            for (String str : answersList) {
                stringBuffer.append("The patient no-" + u
                        + " is classified as: ");
                stringBuffer.append(str);
                stringBuffer.append("\n");
                y++;
            }
            return stringBuffer.toString();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }
        return "";
    }

    /**
     * close the resources
     */
    private void close() {

        try {
            DBManager.getInstance().releaseResources(connection,
                    pstmt, rs);

        } catch (Exception e) {

        }
    }
}
