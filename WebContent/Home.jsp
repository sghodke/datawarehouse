<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	function searchClick(obj, s) {
		document.getElementById("button_id").value=s;
		document.MainForm.submit();
		return false;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Data-warehouse Home</title>
</head>
<body>
<div style="float: left; width: 53%; height: 100%; word-wrap: break-word; overflow: hidden; padding:20px">
<form action="QueryServlet" id="testQuery" method="post" name="MainForm">
		<input type="text" name="button_id" id="button_id" hidden="true" />
		<b>Queries: </b>
		<br><br>
	<b>Query Template II.1: </b>
	List the number of patients who had &lt;disease description&gt;, &lt;disease type&gt; and &lt;disease name&gt;, separately.<br>
		Description: <input type="text" name="description_query1" />
		Type: <input type="text" name="type_query1" />
		Name: <input type="text" name="name_query1" />         
		<br><br><input type="button" value="Get Patients" onclick="searchClick(this, 'query1'); return false;" /> 
		
		
		<br><br><b>Query Template II.2: </b>
		List the types of drugs which have been applied to patients with &lt;disease description&gt;.
		<br>Description: <input type="text" name="description_query2" />
		<br><br><input type="button" value="Get Drug Types" onclick="searchClick(this, 'query2'); return false;" />
			
		
	    <br><br><b>Query Template II.3: </b>
		For each sample of  patients with &lt;disease name&gt;, list the mRNA values (expression) of probes in &lt;cluster id&gt; for each experiment with &lt;measure unit id&gt;.
		<br>Name: <input type="text" name="name_query3" />
		Cluster Id: <input type="text" name="cluster_id_query3" />
		Measure Unit Id: <input type="text" name="measure_unid_id_query3" />
		<br><br><input type="button" value="Get mRNA Values" onclick="searchClick(this, 'query3'); return false;" />
		
		<br><br><b>Query Template II.4: </b>
		For probes belonging to GO with id = &lt;go_id&gt;, calculate the t statistics of the expression values between patients with &lt;disease name&gt; and patients without &lt;disease name&gt;.
		<br>Name: <input type="text" name="name_query4" />
		Go Id: <input type="text" name="go_id_query4" />
		<br><br><input type="button" value="Get T Statistics" onclick="searchClick(this, 'query4'); return false;" />
		<br>
		
	
	    <br><b>Query Template II.5: </b>
		For probes belonging to GO with id=&lt;go_id&gt;, calculate the F statistics of the expression values among patients with &lt;disease name1&gt;, &lt;disease name2&gt;, &lt;disease name3&gt; and &lt;disease name4&gt;.
		<br>Name 1: <input type="text" name="name1_query5" />
		Name 2: <input type="text" name="name2_query5" />
		Name 3: <input type="text" name="name3_query5" />
		<br>Name 4: <input type="text" name="name4_query5" />
		Go Id: <input type="text" name="go_id_query5" />
		<br><br><input type="button" value="Get F Statistics" onclick="searchClick(this, 'query5'); return false;" />	
		
		<br><br><b>Query Template II.6: </b>
		For probes belonging to GO with id=&lt;go_id&gt;, calculate the average correlation of the expression values between two patients with “ALL”, and calculate the average correlation of the expression values between one “ALL” patient and one “AML” patient.
		<br>
		Go Id: <input type="text" name="go_id_query6" />
		Name 1: <input type="text" name="name1_query6" />
		Name 2: <input type="text" name="name2_query6" />
		<br><br><input type="button" value="Get Average Correlation" onclick="searchClick(this, 'query6'); return false;" />
		
		<br><br><b>Query Template III: </b>
		Given a specific disease, find the informative genes. AND Use informative genes to classify a new patient .
		<br>
		Name: <input type="text" name="name_query3A" />
		<br><br><input type="button" value="Get Genes" onclick="searchClick(this, 'query3A'); return false;" /><br>	
	
	</form>
		
</div>

<div style="float: right; width: 40%; height: 100%; word-wrap: break-word; padding:20px">
<%
			String queryResult = (String)request.getAttribute("queryResult");
			
		%>
		
		<b>Result:</b>
		<textarea rows="50" cols="75" readonly="readonly" >
		<% 
			if(queryResult != null)
				out.println(queryResult);
		%>
		</textarea>
</div>
	
</body>

</html>