<%@ page import="ch.bbcag.gamexchange.models.User"%>
<!-- Navigation -->
<nav role="navigation" class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<button type="button" data-target="#navbarCollapse"
				data-toggle="collapse" class="navbar-toggle">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a href="index.jsp" class="navbar-brand">GameXChange</a>
		</div>
		<div id="navbarCollapse" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="">Home</a></li>
				<li><a href="">Offers</a></li>
				<li><a href="">Sell</a></li>
				<li><a href="">Help</a></li>
			</ul>
			<%		
				if (session.getAttribute("user") == null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="login.jsp">Login</a></li>
				<li><a href="register.jsp">Sign Up</a></li>
			</ul>
			<% 
				} else {
					
				User user = (User) session.getAttribute("user");

			%>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="dashboard.jsp">You are logged in as <strong><% out.println(user.getEmail()); %></strong></a></li>
				<li><a href="logout"><strong>Logout</strong></a></li>
			</ul>
			<% 
				} 			
			%>
		</div>
	</div>
</nav>