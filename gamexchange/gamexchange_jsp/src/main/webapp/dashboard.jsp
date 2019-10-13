<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ch.bbcag.gamexchange.models.User"%>
<jsp:include page="includes/auth.jsp" />
<!DOCTYPE html>
<html>
<head>

<title>Dashboard</title>

<!-- Styles -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="js/jquery.min.js"></script>
<link rel="stylesheet" href="css/sweetalert.css">
<script src="js/sweetalert.min.js"></script>
<script>
	$(document).ready(function(){
	    $("#delete-acc").click(function(){
	    	swal({   
	    		title: "Are you sure?",   
	    		text:  "You will delete your entire account. This can't be undone.",   
	    		type:  "warning",   
	    		showCancelButton: true,   
	    		confirmButtonColor: "#DD6B55",   
	    		confirmButtonText: "Yes, delete my account!",   
	    		closeOnConfirm: false 
	    		}, function() {   
	    		swal("Deleted!", "Your account was successfully deleted!", "success"); 
	    		});
	    });
	});
</script>

</head>
<body>

	<jsp:include page="includes/menue.jsp" />

	<%
	User user = (User) session.getAttribute("user");
	%>

	<div class="container">
	
		<h1 class="center marginBottom20">Welcome back <% out.println(user.getFirstname() + " " + user.getLastname()); %></h1>
	
		<div class="row">
			<a href="sales.jsp">
				<div class="col-md-3 dashicons">
					<h2>Sales</h2>	
					<i class="fa fa-usd fa-5x"></i>
				</div>
			</a>
			<a href="purchases.jsp">
				<div class="col-md-3 dashicons">
					<h2>Purchases</h2>	
					<i class="fa fa-usd fa-5x"></i>
				</div>
			</a>
			<a href="edit-user.jsp">
				<div class="col-md-3 dashicons">
					<h2>Edit user data</h2>
					<i class="fa fa-cog fa-5x"></i>
				</div>
			</a>
			<a href="edit-password.jsp">
				<div class="col-md-3 dashicons">
					<h2>Change password</h2>
					<i class="fa fa-lock fa-5x"></i>
				</div>
			</a>
		</div>
		
		<div class="row">
			<div class="col-md-9">
				<h2>You have <span style="color: #3498db">454$</span> left. <button class="btn btn-primary">Get more</button></h2>
			</div>
			<div class="col-md-3">
				<h2><button class="btn btn-danger btn-right" id="delete-acc"><i class="fa fa-trash-o"></i> Delete account</button></h2>
			</div>
		</div>
	
	</div>

</body>
</html>