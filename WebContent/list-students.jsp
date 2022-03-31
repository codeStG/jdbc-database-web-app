<%@ page import="java.util.*, com.stgcodes.web.jdbc.*" %>
<!DOCTYPE html>

<html>

<head>
	<title>Student Tracker App</title>
</head>

<%
	List<Student> students = (List<Student>) request.getAttribute("STUDENTS");
%>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>FooBar University</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
			<table>
				<tr> 
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				</tr>
				
				<% for(Student student: students) { %>
					<tr>
						<td> <%= student.getFirstName() %> </td>
						<td> <%= student.getLastName() %> </td>
						<td> <%= student.getEmail() %> </td>
					</tr>
				<% } %>
			</table>
		</div>
	</div>

</body>

</html>