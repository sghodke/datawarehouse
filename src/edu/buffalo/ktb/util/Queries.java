package edu.buffalo.ktb.util;

public class Queries {
    
    public static final String QUERY_ONE = "select d.DESCRIPTION, count(*) FROM DISEASE d inner join DIAGNOSIS dg on d.DS_ID=dg.DS_ID and d.DESCRIPTION=? group by d.DESCRIPTION"
            + " UNION select d.NAME, count(*) from DISEASE d inner join DIAGNOSIS dg on d.DS_ID=dg.DS_ID and d.NAME=? group by d.NAME"
            + " UNION select d.TYPE, count(*) from DISEASE d inner join DIAGNOSIS dg on d.DS_ID=dg.DS_ID and d.TYPE=? group by d.TYPE";

    public static final String QUERY_TWO = "select distinct d.TYPE from DRUG d join DRUG_USE du on d.DR_ID=du.DR_ID and (du.P_ID in "
            + "(select dg.P_ID from DIAGNOSIS dg join DISEASE ds on dg.DS_ID=ds.DS_ID and ds.DESCRIPTION=?))";

    public static final String QUERY_THREE = "select mf.EXP from MICROARRAY_FACT mf where mf.PB_ID in (" 
    		+ "select pr.PB_ID from PROBE pr, GENE_FACT gf where pr.U_ID=gf.U_ID and gf.CL_ID=? and pr.PB_ID in ("
    		+ "select mf.PB_ID from MICROARRAY_FACT mf where mf.E_ID in (select mf.E_ID from MICROARRAY_FACT mf where mf.MU_ID=? GROUP BY mf.E_ID))) "
    		+ "and mf.S_ID in ("
    		+ "select distinct cs.S_ID from MICROARRAY_FACT mf,DIAGNOSIS dg, DISEASE ds, CLINICAL_SAMPLE cs "
    		+ "where mf.S_ID=cs.S_ID and dg.DS_ID=ds.DS_ID and dg.P_ID=cs.P_ID and ds.DS_ID in ("
    		+ "select  ds.DS_ID from DISEASE ds where ds.NAME=? GROUP BY ds.DS_ID))";

    public static final String QUERY_FOUR_A = "select mf.EXP from MICROARRAY_FACT mf where mf.PB_ID in ("
			+ "select pr.PB_ID from PROBE pr JOIN GENE_FACT gf on pr.U_ID = gf.U_ID and gf.GO_ID = ?) "
			+ "and mf.S_ID in ("
			+ "select cs.s_id from CLINICAL_SAMPLE cs JOIN PATIENT p on cs.P_ID = p.P_ID and p.P_ID in ("
			+ "select dg.P_ID from DIAGNOSIS dg JOIN DISEASE ds on dg.DS_ID = ds.DS_ID and ds.NAME = ?))";
    
    public static final String QUERY_FOUR_B = "select mf.EXP from MICROARRAY_FACT mf where mf.PB_ID in ("
			+ "select pr.PB_ID from PROBE pr JOIN GENE_FACT gf on pr.U_ID = gf.U_ID and gf.GO_ID = ?) "
			+ "and mf.S_ID in ("
			+ "select cs.s_id from CLINICAL_SAMPLE cs JOIN PATIENT p on cs.P_ID = p.P_ID and p.P_ID in ("
			+ "select dg.P_ID from DIAGNOSIS dg JOIN DISEASE ds on dg.DS_ID = ds.DS_ID and ds.NAME != ?))";
    
    public static final String QUERY_FIVE = "select mf.EXP from MICROARRAY_FACT mf where mf.PB_ID in ("
			+ "select pr.PB_ID from PROBE pr JOIN GENE_FACT gf on pr.U_ID = gf.U_ID and gf.GO_ID = ?) "
			+ "and mf.S_ID in ("
			+ "select cs.s_id from CLINICAL_SAMPLE cs JOIN PATIENT p on cs.P_ID = p.P_ID and p.P_ID in ("
			+ "select dg.P_ID from DIAGNOSIS dg JOIN DISEASE ds on dg.DS_ID = ds.DS_ID and UPPER(ds.NAME) = ?))";

    public static final String QUERY_SIX_ALL = "select cs.P_ID,mf.EXP from MICROARRAY_FACT mf, DISEASE ds, DIAGNOSIS dg, CLINICAL_SAMPLE cs"+
            " where mf.S_ID=cs.S_ID and ds.DS_ID=dg.DS_ID and dg.P_ID=cs.P_ID and ds.NAME=? and mf.PB_ID in ("+
            " select pr.PB_ID from PROBE pr,GENE_FACT gf where gf.U_ID=pr.U_ID and gf.GO_ID=?)";

    public static final String QUERY_SIX_AML = "select cs.P_ID,mf.EXP from MICROARRAY_FACT mf, DISEASE ds, DIAGNOSIS dg, CLINICAL_SAMPLE cs"+
        " where mf.S_ID=cs.S_ID and ds.DS_ID=dg.DS_ID and dg.P_ID=cs.P_ID and ds.NAME=? and mf.PB_ID in ("+
        " select pr.PB_ID from PROBE pr,GENE_FACT gf where gf.U_ID=pr.U_ID and gf.GO_ID=?)";
    
    public static final String QUERY_PART3_ONE_A = "select pr.U_ID, mf.EXP from MICROARRAY_FACT mf JOIN PROBE pr on mf.PB_ID = pr.PB_ID "
    		+ "where mf.S_ID in ("
    		+ "select cs.s_id from CLINICAL_SAMPLE cs JOIN PATIENT p on cs.P_ID = p.P_ID and p.P_ID in ("
    		+ "select dg.P_ID from DIAGNOSIS dg JOIN DISEASE ds on dg.DS_ID = ds.DS_ID and ds.NAME = ?))"
    		+ "order by pr.U_ID";
    
    public static final String QUERY_PART3_ONE_B = "select pr.U_ID, mf.EXP from MICROARRAY_FACT mf JOIN PROBE pr on mf.PB_ID = pr.PB_ID "
    		+ "where mf.S_ID in ("
    		+ "select cs.s_id from CLINICAL_SAMPLE cs JOIN PATIENT p on cs.P_ID = p.P_ID and p.P_ID in ("
    		+ "select dg.P_ID from DIAGNOSIS dg JOIN DISEASE ds on dg.DS_ID = ds.DS_ID and ds.NAME != ?))"
    		+ "order by pr.U_ID";
    
    public static final String QUERY_THIRD_2_ALL = "select dg.P_ID,pr.U_ID,mf.EXP from MICROARRAY_FACT mf, DISEASE ds, DIAGNOSIS dg, CLINICAL_SAMPLE cs,PROBE pr" 
    		+" where mf.S_ID=cs.S_ID and ds.DS_ID=dg.DS_ID and dg.P_ID=cs.P_ID and mf.PB_ID=pr.PB_ID  and ds.NAME='ALL' and mf.PB_ID in ("
            +" select pr.PB_ID from PROBE pr,GENE_FACT gf where gf.U_ID=pr.U_ID) and pr.U_ID in (?) order by dg.P_ID, pr.U_ID";
   
    public static final String QUERY_THIRD_2_NOT_ALL = "select dg.P_ID,pr.U_ID,mf.EXP from MICROARRAY_FACT mf, DISEASE ds, DIAGNOSIS dg, CLINICAL_SAMPLE cs,PROBE pr" 
            +" where mf.S_ID=cs.S_ID and ds.DS_ID=dg.DS_ID and dg.P_ID=cs.P_ID and mf.PB_ID=pr.PB_ID  and ds.NAME<>'ALL' and mf.PB_ID in ("
            +" select pr.PB_ID from PROBE pr,GENE_FACT gf where gf.U_ID=pr.U_ID) and pr.U_ID in (?) order by dg.P_ID, pr.U_ID";
   
	public static final String QUERY_THIRD_2_TEST_PATIENT = "select * from TEST_SAMPLE ts where ts.U_ID in (?) order by ts.U_ID";
    
}
