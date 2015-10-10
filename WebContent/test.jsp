<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test</title>
</head>
<body>
	<form action="QueryServlet" id="testQuery" method="post">
	<b>Query Template 1: </b>
	List the number of patients who had &lt;disease description&gt;, &lt;disease type&gt; and &lt;disease name&gt;, separately.<br>
	
		Description: <input type="text" name="description_query1" />
		Type: <input type="text" name="type_query1" />
		Name: <input type="text" name="name_query1" />         
		<input type="submit" name="button_query1" value="Get Patient List" />
		
		<br><br><b>Query Template 2: </b>
		List the types of drugs which have been applied to patients with &lt;disease description&gt;.
		<br>Description: <input type="text" name="description_query2" />
		<input type="submit" name="button_query2" value="Get Drug Types" /><br>	
		
	    <br><br><b>Query Template 3: </b>
		For each sample of  patients with &lt;disease name&gt;, list the mRNA values (expression) of probes in &lt;cluster id&gt; for each experiment with &lt;measure unit id&gt;.
		<br>Name: <input type="text" name="name_query3" />
		Cluster Id: <input type="text" name="cluster_id_query3" />
		Measure Unit Id: <input type="text" name="measure_unid_id_query3" />
		<input type="submit" name="button_query3" value="Get mRNA Values" />
		
		<br><br><b>Query Template 4: </b>
		For probes belonging to GO with id = &lt;go_id&gt;, calculate the t statistics of the expression values between patients with &lt;disease name&gt; and patients without &lt;disease name&gt;.
		<br>Name: <input type="text" name="name_query4" />
		Go Id: <input type="text" name="go_id_query4" />
		<input type="submit" name="button_query4" value="Get T Statistics" /><br>
		
	    <br><b>Query Template 5: </b>
		For probes belonging to GO with id=&lt;go_id&gt;, calculate the F statistics of the expression values among patients with &lt;disease name1&gt;, &lt;disease name2&gt;, &lt;disease name3&gt; and &lt;disease name4&gt;.
		<br>Name 1: <input type="text" name="name1_query5" />
		Name 2: <input type="text" name="name2_query5" />
		Name 3: <input type="text" name="name3_query5" />
		Name 4: <input type="text" name="name4_query5" />
		Go Id: <input type="text" name="go_id_query5" />
		<input type="submit" name="button_query5" value="Get F Statistics" /><br>	
		
		<br><b>Query Template 6: </b>
		For probes belonging to GO with id=&lt;go_id&gt;, calculate the average correlation of the expression values between two patients with “ALL”, and calculate the average correlation of the expression values between one “ALL” patient and one “AML” patient.
		<br>
		Go Id: <input type="text" name="go_id_query6" />
		<input type="submit" name="button_query6" value="Get Average Correlation" /><br>	
	
	</form>
		
</body>

</html>