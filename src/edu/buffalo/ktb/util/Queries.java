package edu.buffalo.ktb.util;

public class Queries {
    
    public static String Query1="select d.DESCRIPTION, count(*) FROM DISEASE d inner join DIAGNOSIS dg on d.DS_ID=dg.DS_ID and d.DESCRIPTION=? group by d.DESCRIPTION"
            + " UNION select d.NAME, count(*) from DISEASE d inner join DIAGNOSIS dg on d.DS_ID=dg.DS_ID and d.NAME=? group by d.NAME"
            + " UNION select d.TYPE, count(*) from DISEASE d inner join DIAGNOSIS dg on d.DS_ID=dg.DS_ID and d.TYPE=? group by d.TYPE";

    public static String Query2="select distinct d.TYPE from DRUG d join DRUG_USE du on d.DR_ID=du.DR_ID and (du.P_ID in "
            + "(select dg.P_ID from DIAGNOSIS dg join DISEASE ds on dg.DS_ID=ds.DS_ID and ds.DESCRIPTION=?))";



}
