<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	
		<title>Register</title>
		
		<!-- Styles -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" href="css/style.css">
		
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
		<script type="text/javascript" src="js/pwstrength.js"></script>
		
	</head>
	<body>
	
		<jsp:include page="includes/menue.jsp" />
	
		<div class="container">
	
			<h1>Delete account</h1>
			<p>You're about to delete your account! You will not be able to log in again after this process.</p>	
				
			<form method="post" action="/GameXChangeLowlevel/passwordedit">
		
			  	<div class="form-group">
			    	<label for="currentpassword">Enter your current password to confirm deletion of account</label>
			    	<input type="password" class="form-control" id="currentpassword" name="currentpassword" required>
			 	 </div>
			 	  
			 	<input type="submit" value="Delete" class="btn btn-primary btn-danger">
			 	 
			</form>
		
		</div>
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
	
	</body>
</html>