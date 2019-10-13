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
		
		<script>	    
			jQuery(document).ready(function (ö) {
	    		
	    		var options = {};
	    	    options.ui = {
	    	        container: "#pwd-container",
	    	        viewports: {
	    	            progress: ".pwstrength_viewport_progress"
	    	        }
	    	    };
	    	    
	    		$('#newpassword').pwstrength(options);
	    		
	    	});
		</script>
		
	</head>
	<body>
	
		<jsp:include page="includes/menue.jsp" />
	
		<div class="container">
	
			<h1>Change your password</h1>
				
			<form method="post" action="/GameXChangeLowlevel/passwordedit">
		
			  	<div class="form-group">
			    	<label for="currentpassword">Enter your current password</label>
			    	<input type="password" class="form-control" id="currentpassword" name="currentpassword" required>
			 	 </div>
			 	 
			 	 <div class="row">
					<div class="col-md-6">
						<div class="form-group">
					    	<label for="newpassword">New password</label>
					    	<input type="password" class="form-control" id="newpassword" name="newpassword" required>
					 	</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
					    	<label for="newpasswordrepeat">Repeat new password</label>
					    	<input type="password" class="form-control" id="newpasswordrepeat" name="newpasswordrepeat" required>
					 	</div>
					</div>
				</div>
			 	  
			 	<input type="submit" value="Save" class="btn btn-primary btn-right">
			 	 
			</form>
		
		</div>
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	</body>
</html>