<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	
		<title>Login</title>
		
		<!-- Styles -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" href="css/style.css">
		
	</head>
	<body>
	
		<jsp:include page="includes/menue.jsp" />
	
		<div class="container">
	
			<h1>Sign In</h1>
		
			<form method="post" action="/GameXChangeLowlevel/login">
			
			  	<div class="form-group">
			    	<label for="useremail">Email address</label>
			    	<input type="email" class="form-control" id="useremail" name="useremail" required>
			    	<input type="hidden">
			 	 </div>
			 	 
			 	 <div class="form-group">
			    	<label for="userpassword">Password</label>
			    	<input type="password" class="form-control" id="userpassword" name="userpassword" required>
			 	 </div>
			 	 
			 	 <%
		         if(request.getAttribute("error") != null) {
		         %>
		         <div class="alert alert-danger">
			         <p>
			         	<%= request.getAttribute("error") %>
			         </p>
		         </div>
		         <%     
		         } 
		         %>

			 	 
			 	 <input type="submit" value="Sign In" class="btn btn-primary btn-right">
			 	 
			</form>
		
		</div>
	
	</body>
</html>