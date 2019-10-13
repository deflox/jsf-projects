<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ch.bbcag.gamexchange.models.User"%>
<!DOCTYPE html>
<html>
	<head>
	
		<title>Edit User</title>
		
		<!-- Styles -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" href="css/style.css">
		
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    	<script type="text/javascript" src="js/pwstrength.js"></script>
    	<script>
	    	jQuery(document).ready(function (ö) {
	    	
	    		$("form").submit(function(e){
	    			e.preventDefault();
	    			
	    			var useremail = $("#useremail").val();
	    			var userpassword = $("#userpassword").val();
	    			var userpasswordrepeat = $("#userpasswordrepeat").val();
	    			
	    			var firstname = $("#firstname").val();
	    			var lastname = $("#lastname").val();
	    			var street = $("#street").val();
	    			var streetnumber = $("#streetnumber").val();
	    			var city = $("#city").val();
	    			
	    			var error = 0;
	    			var errortext = "<ul>"; 
	    			
	    			var allElements = [useremail,firstname,lastname,street,streetnumber,city];
	    			
	    			if (!checkIfElementsAreEmpty(allElements)) {
	    				error++;
	    				errortext += "<li>Please fill out every required input</li>";
	    			}
	    			
	    			if (!validateEmail(useremail)) {
	    				error++;
	    				errortext += "<li>The entered E-Mail-Adress is not correct</li>";
	    			}
	    			
	    			if (error > 0) {
	    				$("#errorwarn").html("Following errors occured: <br>" + errortext + "</ul>");
	    				$("#errorwarn").fadeIn();
	    			} else {
	    				$(this).unbind('submit').submit();
	    			}
	    			
	    		});
	    		
	    		function validateEmail(email) {
	                var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	                return re.test(email);
	            }
	    		
	    		function checkIfElementsAreEmpty(allElements) {
	    			for(var i = 0; i < allElements.length; i++){
	    				if (allElements[i] == "") {
	    					return false;
	    				}
	    			}
	    			return true;
	    		}
	    		
	    	});
	    	
    	</script>
		
	</head>
	<body>
	
		<jsp:include page="includes/menue.jsp" />
	
		<%
			
		User user = (User) session.getAttribute("user");
		
		%>
	
		<div class="container">
	
			<h1>Edit your account</h1>
				
			<div class="alert alert-danger" id="errorwarn" style="display: none" role="alert"></div>
				
			<form method="post" action="/GameXChangeLowlevel/useredit">
		
				<h2>Account information</h2>
		
			  	<div class="form-group">
			    	<label for="useremail">Email address</label>
			    	<input type="email" class="form-control" id="useremail" name="useremail" value="<% out.println(user.getEmail()); %>" required>
			 	 </div>
				
				<div id="pwd-container"><div class="pwstrength_viewport_progress"></div></div>

				<h2>Personal information</h2>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
					    	<label for="firstname">Firstname</label>
					    	<input type="text" class="form-control" id="firstname" name="firstname" value="<% out.println(user.getFirstname()); %>" required>
					 	</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
					    	<label for="lastname">Lastname</label>
					    	<input type="text" class="form-control" id="lastname" name="lastname" value="<% out.println(user.getLastname()); %>" required>
					 	</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-10">
						<div class="form-group">
					    	<label for="street">Street</label>
					    	<input type="text" class="form-control" id="street" name="street" value="<% out.println(user.getStreet()); %>" required>
					 	</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
					    	<label for="streetnumber">Number</label>
					    	<input type="text" class="form-control" id="streetnumber" name="streetnumber" value="<% out.println(user.getStreetnumber()); %>" required>
					 	</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
					    	<label for="city">City</label>
					    	<input type="text" class="form-control" id="city" name="city" value="<% out.println(user.getCity()); %>" required>
					 	</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
					    	<label for="postalcode">Postal code</label>
					    	<input type="text" class="form-control" id="postalcode" name="postalcode" value="<% out.println(user.getPostalcode()); %>" required>
					 	</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
					    	<div class="form-group">
						      	<label for="country">Country:</label>
						      	<select class="form-control" id="country" name="country">
						        	<option value="AF" <% if (user.getCountry().equals("AF")) out.println("selected"); %>>Afghanistan</option>
									<option value="AX" <% if (user.getCountry().equals("AX")) out.println("selected"); %>>Åland Islands</option>
									<option value="AL" <% if (user.getCountry().equals("AL")) out.println("selected"); %>>Albania</option>
									<option value="DZ" <% if (user.getCountry().equals("DZ")) out.println("selected"); %>>Algeria</option>
									<option value="AS" <% if (user.getCountry().equals("AS")) out.println("selected"); %>>American Samoa</option>
									<option value="AD" <% if (user.getCountry().equals("AD")) out.println("selected"); %>>Andorra</option>
									<option value="AO" <% if (user.getCountry().equals("AO")) out.println("selected"); %>>Angola</option>
									<option value="AI" <% if (user.getCountry().equals("AI")) out.println("selected"); %>>Anguilla</option>
									<option value="AQ" <% if (user.getCountry().equals("AQ")) out.println("selected"); %>>Antarctica</option>
									<option value="AG" <% if (user.getCountry().equals("AG")) out.println("selected"); %>>Antigua and Barbuda</option>
									<option value="AR" <% if (user.getCountry().equals("AR")) out.println("selected"); %>>Argentina</option>
									<option value="AM" <% if (user.getCountry().equals("AM")) out.println("selected"); %>>Armenia</option>
									<option value="AW" <% if (user.getCountry().equals("AW")) out.println("selected"); %>>Aruba</option>
									<option value="AU" <% if (user.getCountry().equals("AU")) out.println("selected"); %>>Australia</option>
									<option value="AT" <% if (user.getCountry().equals("AT")) out.println("selected"); %>>Austria</option>
									<option value="AZ" <% if (user.getCountry().equals("AZ")) out.println("selected"); %>>Azerbaijan</option>
									<option value="BS" <% if (user.getCountry().equals("BS")) out.println("selected"); %>>Bahamas</option>
									<option value="BH" <% if (user.getCountry().equals("BH")) out.println("selected"); %>>Bahrain</option>
									<option value="BD" <% if (user.getCountry().equals("BD")) out.println("selected"); %>>Bangladesh</option>
									<option value="BB" <% if (user.getCountry().equals("BB")) out.println("selected"); %>>Barbados</option>
									<option value="BY" <% if (user.getCountry().equals("BY")) out.println("selected"); %>>Belarus</option>
									<option value="BE" <% if (user.getCountry().equals("BE")) out.println("selected"); %>>Belgium</option>
									<option value="BZ" <% if (user.getCountry().equals("BZ")) out.println("selected"); %>>Belize</option>
									<option value="BJ" <% if (user.getCountry().equals("BJ")) out.println("selected"); %>>Benin</option>
									<option value="BM" <% if (user.getCountry().equals("BM")) out.println("selected"); %>>Bermuda</option>
									<option value="BT" <% if (user.getCountry().equals("BT")) out.println("selected"); %>>Bhutan</option>
									<option value="BO" <% if (user.getCountry().equals("BO")) out.println("selected"); %>>Bolivia, Plurinational State of</option>
									<option value="BQ" <% if (user.getCountry().equals("BQ")) out.println("selected"); %>>Bonaire, Sint Eustatius and Saba</option>
									<option value="BA" <% if (user.getCountry().equals("BA")) out.println("selected"); %>>Bosnia and Herzegovina</option>
									<option value="BW" <% if (user.getCountry().equals("BW")) out.println("selected"); %>>Botswana</option>
									<option value="BV" <% if (user.getCountry().equals("BV")) out.println("selected"); %>>Bouvet Island</option>
									<option value="BR" <% if (user.getCountry().equals("BR")) out.println("selected"); %>>Brazil</option>
									<option value="IO" <% if (user.getCountry().equals("IO")) out.println("selected"); %>>British Indian Ocean Territory</option>
									<option value="BN" <% if (user.getCountry().equals("BN")) out.println("selected"); %>>Brunei Darussalam</option>
									<option value="BG" <% if (user.getCountry().equals("BG")) out.println("selected"); %>>Bulgaria</option>
									<option value="BF" <% if (user.getCountry().equals("BF")) out.println("selected"); %>>Burkina Faso</option>
									<option value="BI" <% if (user.getCountry().equals("BI")) out.println("selected"); %>>Burundi</option>
									<option value="KH" <% if (user.getCountry().equals("KH")) out.println("selected"); %>>Cambodia</option>
									<option value="CM" <% if (user.getCountry().equals("CM")) out.println("selected"); %>>Cameroon</option>
									<option value="CA" <% if (user.getCountry().equals("CA")) out.println("selected"); %>>Canada</option>
									<option value="CV" <% if (user.getCountry().equals("CV")) out.println("selected"); %>>Cape Verde</option>
									<option value="KY" <% if (user.getCountry().equals("KY")) out.println("selected"); %>>Cayman Islands</option>
									<option value="CF" <% if (user.getCountry().equals("CF")) out.println("selected"); %>>Central African Republic</option>
									<option value="TD" <% if (user.getCountry().equals("TD")) out.println("selected"); %>>Chad</option>
									<option value="CL" <% if (user.getCountry().equals("CL")) out.println("selected"); %>>Chile</option>
									<option value="CN" <% if (user.getCountry().equals("CN")) out.println("selected"); %>>China</option>
									<option value="CX" <% if (user.getCountry().equals("CX")) out.println("selected"); %>>Christmas Island</option>
									<option value="CC" <% if (user.getCountry().equals("CC")) out.println("selected"); %>>Cocos (Keeling) Islands</option>
									<option value="CO" <% if (user.getCountry().equals("CO")) out.println("selected"); %>>Colombia</option>
									<option value="KM" <% if (user.getCountry().equals("KM")) out.println("selected"); %>>Comoros</option>
									<option value="CG" <% if (user.getCountry().equals("CG")) out.println("selected"); %>>Congo</option>
									<option value="CD" <% if (user.getCountry().equals("CD")) out.println("selected"); %>>Congo, the Democratic Republic of the</option>
									<option value="CK" <% if (user.getCountry().equals("CK")) out.println("selected"); %>>Cook Islands</option>
									<option value="CR" <% if (user.getCountry().equals("CR")) out.println("selected"); %>>Costa Rica</option>
									<option value="CI" <% if (user.getCountry().equals("CI")) out.println("selected"); %>>Côte d'Ivoire</option>
									<option value="HR" <% if (user.getCountry().equals("HR")) out.println("selected"); %>>Croatia</option>
									<option value="CU" <% if (user.getCountry().equals("CU")) out.println("selected"); %>>Cuba</option>
									<option value="CW" <% if (user.getCountry().equals("CW")) out.println("selected"); %>>Curaçao</option>
									<option value="CY" <% if (user.getCountry().equals("CY")) out.println("selected"); %>>Cyprus</option>
									<option value="CZ" <% if (user.getCountry().equals("CZ")) out.println("selected"); %>>Czech Republic</option>
									<option value="DK" <% if (user.getCountry().equals("DK")) out.println("selected"); %>>Denmark</option>
									<option value="DJ" <% if (user.getCountry().equals("DJ")) out.println("selected"); %>>Djibouti</option>
									<option value="DM" <% if (user.getCountry().equals("DM")) out.println("selected"); %>>Dominica</option>
									<option value="DO" <% if (user.getCountry().equals("DO")) out.println("selected"); %>>Dominican Republic</option>
									<option value="EC" <% if (user.getCountry().equals("EC")) out.println("selected"); %>>Ecuador</option>
									<option value="EG" <% if (user.getCountry().equals("EG")) out.println("selected"); %>>Egypt</option>
									<option value="SV" <% if (user.getCountry().equals("SV")) out.println("selected"); %>>El Salvador</option>
									<option value="GQ" <% if (user.getCountry().equals("GQ")) out.println("selected"); %>>Equatorial Guinea</option>
									<option value="ER" <% if (user.getCountry().equals("ER")) out.println("selected"); %>>Eritrea</option>
									<option value="EE" <% if (user.getCountry().equals("EE")) out.println("selected"); %>>Estonia</option>
									<option value="ET" <% if (user.getCountry().equals("ET")) out.println("selected"); %>>Ethiopia</option>
									<option value="FK" <% if (user.getCountry().equals("FK")) out.println("selected"); %>>Falkland Islands (Malvinas)</option>
									<option value="FO" <% if (user.getCountry().equals("FO")) out.println("selected"); %>>Faroe Islands</option>
									<option value="FJ" <% if (user.getCountry().equals("FJ")) out.println("selected"); %>>Fiji</option>
									<option value="FI" <% if (user.getCountry().equals("FI")) out.println("selected"); %>>Finland</option>
									<option value="FR" <% if (user.getCountry().equals("FR")) out.println("selected"); %>>France</option>
									<option value="GF" <% if (user.getCountry().equals("GF")) out.println("selected"); %>>French Guiana</option>
									<option value="PF" <% if (user.getCountry().equals("PF")) out.println("selected"); %>>French Polynesia</option>
									<option value="TF" <% if (user.getCountry().equals("TF")) out.println("selected"); %>>French Southern Territories</option>
									<option value="GA" <% if (user.getCountry().equals("GA")) out.println("selected"); %>>Gabon</option>
									<option value="GM" <% if (user.getCountry().equals("GM")) out.println("selected"); %>>Gambia</option>
									<option value="GE" <% if (user.getCountry().equals("GE")) out.println("selected"); %>>Georgia</option>
									<option value="DE" <% if (user.getCountry().equals("DE")) out.println("selected"); %>>Germany</option>
									<option value="GH" <% if (user.getCountry().equals("GH")) out.println("selected"); %>>Ghana</option>
									<option value="GI" <% if (user.getCountry().equals("GI")) out.println("selected"); %>>Gibraltar</option>
									<option value="GR" <% if (user.getCountry().equals("GR")) out.println("selected"); %>>Greece</option>
									<option value="GL" <% if (user.getCountry().equals("GL")) out.println("selected"); %>>Greenland</option>
									<option value="GD" <% if (user.getCountry().equals("GD")) out.println("selected"); %>>Grenada</option>
									<option value="GP" <% if (user.getCountry().equals("GP")) out.println("selected"); %>>Guadeloupe</option>
									<option value="GU" <% if (user.getCountry().equals("GU")) out.println("selected"); %>>Guam</option>
									<option value="GT" <% if (user.getCountry().equals("GT")) out.println("selected"); %>>Guatemala</option>
									<option value="GG" <% if (user.getCountry().equals("GG")) out.println("selected"); %>>Guernsey</option>
									<option value="GN" <% if (user.getCountry().equals("GN")) out.println("selected"); %>>Guinea</option>
									<option value="GW" <% if (user.getCountry().equals("GW")) out.println("selected"); %>>Guinea-Bissau</option>
									<option value="GY" <% if (user.getCountry().equals("GY")) out.println("selected"); %>>Guyana</option>
									<option value="HT" <% if (user.getCountry().equals("HT")) out.println("selected"); %>>Haiti</option>
									<option value="HM" <% if (user.getCountry().equals("HM")) out.println("selected"); %>>Heard Island and McDonald Islands</option>
									<option value="VA" <% if (user.getCountry().equals("VA")) out.println("selected"); %>>Holy See (Vatican City State)</option>
									<option value="HN" <% if (user.getCountry().equals("HN")) out.println("selected"); %>>Honduras</option>
									<option value="HK" <% if (user.getCountry().equals("HK")) out.println("selected"); %>>Hong Kong</option>
									<option value="HU" <% if (user.getCountry().equals("HU")) out.println("selected"); %>>Hungary</option>
									<option value="IS" <% if (user.getCountry().equals("IS")) out.println("selected"); %>>Iceland</option>
									<option value="IN" <% if (user.getCountry().equals("IN")) out.println("selected"); %>>India</option>
									<option value="ID" <% if (user.getCountry().equals("ID")) out.println("selected"); %>>Indonesia</option>
									<option value="IR" <% if (user.getCountry().equals("IR")) out.println("selected"); %>>Iran, Islamic Republic of</option>
									<option value="IQ" <% if (user.getCountry().equals("IQ")) out.println("selected"); %>>Iraq</option>
									<option value="IE" <% if (user.getCountry().equals("IE")) out.println("selected"); %>>Ireland</option>
									<option value="IM" <% if (user.getCountry().equals("IM")) out.println("selected"); %>>Isle of Man</option>
									<option value="IL" <% if (user.getCountry().equals("IL")) out.println("selected"); %>>Israel</option>
									<option value="IT" <% if (user.getCountry().equals("IT")) out.println("selected"); %>>Italy</option>
									<option value="JM" <% if (user.getCountry().equals("JM")) out.println("selected"); %>>Jamaica</option>
									<option value="JP" <% if (user.getCountry().equals("JP")) out.println("selected"); %>>Japan</option>
									<option value="JE" <% if (user.getCountry().equals("JE")) out.println("selected"); %>>Jersey</option>
									<option value="JO" <% if (user.getCountry().equals("JO")) out.println("selected"); %>>Jordan</option>
									<option value="KZ" <% if (user.getCountry().equals("KZ")) out.println("selected"); %>>Kazakhstan</option>
									<option value="KE" <% if (user.getCountry().equals("KE")) out.println("selected"); %>>Kenya</option>
									<option value="KI" <% if (user.getCountry().equals("KI")) out.println("selected"); %>>Kiribati</option>
									<option value="KP" <% if (user.getCountry().equals("KP")) out.println("selected"); %>>Korea, Democratic People's Republic of</option>
									<option value="KR" <% if (user.getCountry().equals("KR")) out.println("selected"); %>>Korea, Republic of</option>
									<option value="KW" <% if (user.getCountry().equals("KW")) out.println("selected"); %>>Kuwait</option>
									<option value="KG" <% if (user.getCountry().equals("KG")) out.println("selected"); %>>Kyrgyzstan</option>
									<option value="LA" <% if (user.getCountry().equals("LA")) out.println("selected"); %>>Lao People's Democratic Republic</option>
									<option value="LV" <% if (user.getCountry().equals("LV")) out.println("selected"); %>>Latvia</option>
									<option value="LB" <% if (user.getCountry().equals("LB")) out.println("selected"); %>>Lebanon</option>
									<option value="LS" <% if (user.getCountry().equals("LS")) out.println("selected"); %>>Lesotho</option>
									<option value="LR" <% if (user.getCountry().equals("LR")) out.println("selected"); %>>Liberia</option>
									<option value="LY" <% if (user.getCountry().equals("LY")) out.println("selected"); %>>Libya</option>
									<option value="LI" <% if (user.getCountry().equals("LI")) out.println("selected"); %>>Liechtenstein</option>
									<option value="LT" <% if (user.getCountry().equals("LT")) out.println("selected"); %>>Lithuania</option>
									<option value="LU" <% if (user.getCountry().equals("LU")) out.println("selected"); %>>Luxembourg</option>
									<option value="MO" <% if (user.getCountry().equals("MO")) out.println("selected"); %>>Macao</option>
									<option value="MK" <% if (user.getCountry().equals("MK")) out.println("selected"); %>>Macedonia, the former Yugoslav Republic of</option>
									<option value="MG" <% if (user.getCountry().equals("MG")) out.println("selected"); %>>Madagascar</option>
									<option value="MW" <% if (user.getCountry().equals("MW")) out.println("selected"); %>>Malawi</option>
									<option value="MY" <% if (user.getCountry().equals("MY")) out.println("selected"); %>>Malaysia</option>
									<option value="MV" <% if (user.getCountry().equals("MV")) out.println("selected"); %>>Maldives</option>
									<option value="ML" <% if (user.getCountry().equals("ML")) out.println("selected"); %>>Mali</option>
									<option value="MT" <% if (user.getCountry().equals("MT")) out.println("selected"); %>>Malta</option>
									<option value="MH" <% if (user.getCountry().equals("MH")) out.println("selected"); %>>Marshall Islands</option>
									<option value="MQ" <% if (user.getCountry().equals("MQ")) out.println("selected"); %>>Martinique</option>
									<option value="MR" <% if (user.getCountry().equals("MR")) out.println("selected"); %>>Mauritania</option>
									<option value="MU" <% if (user.getCountry().equals("MU")) out.println("selected"); %>>Mauritius</option>
									<option value="YT" <% if (user.getCountry().equals("YT")) out.println("selected"); %>>Mayotte</option>
									<option value="MX" <% if (user.getCountry().equals("MX")) out.println("selected"); %>>Mexico</option>
									<option value="FM" <% if (user.getCountry().equals("FM")) out.println("selected"); %>>Micronesia, Federated States of</option>
									<option value="MD" <% if (user.getCountry().equals("MD")) out.println("selected"); %>>Moldova, Republic of</option>
									<option value="MC" <% if (user.getCountry().equals("MC")) out.println("selected"); %>>Monaco</option>
									<option value="MN" <% if (user.getCountry().equals("MN")) out.println("selected"); %>>Mongolia</option>
									<option value="ME" <% if (user.getCountry().equals("ME")) out.println("selected"); %>>Montenegro</option>
									<option value="MS" <% if (user.getCountry().equals("MS")) out.println("selected"); %>>Montserrat</option>
									<option value="MA" <% if (user.getCountry().equals("MA")) out.println("selected"); %>>Morocco</option>
									<option value="MZ" <% if (user.getCountry().equals("MZ")) out.println("selected"); %>>Mozambique</option>
									<option value="MM" <% if (user.getCountry().equals("MM")) out.println("selected"); %>>Myanmar</option>
									<option value="NA" <% if (user.getCountry().equals("NA")) out.println("selected"); %>>Namibia</option>
									<option value="NR" <% if (user.getCountry().equals("NR")) out.println("selected"); %>>Nauru</option>
									<option value="NP" <% if (user.getCountry().equals("NP")) out.println("selected"); %>>Nepal</option>
									<option value="NL" <% if (user.getCountry().equals("NL")) out.println("selected"); %>>Netherlands</option>
									<option value="NC" <% if (user.getCountry().equals("NC")) out.println("selected"); %>>New Caledonia</option>
									<option value="NZ" <% if (user.getCountry().equals("NZ")) out.println("selected"); %>>New Zealand</option>
									<option value="NI" <% if (user.getCountry().equals("NI")) out.println("selected"); %>>Nicaragua</option>
									<option value="NE" <% if (user.getCountry().equals("NE")) out.println("selected"); %>>Niger</option>
									<option value="NG" <% if (user.getCountry().equals("NG")) out.println("selected"); %>>Nigeria</option>
									<option value="NU" <% if (user.getCountry().equals("NU")) out.println("selected"); %>>Niue</option>
									<option value="NF" <% if (user.getCountry().equals("NF")) out.println("selected"); %>>Norfolk Island</option>
									<option value="MP" <% if (user.getCountry().equals("MP")) out.println("selected"); %>>Northern Mariana Islands</option>
									<option value="NO" <% if (user.getCountry().equals("NO")) out.println("selected"); %>>Norway</option>
									<option value="OM" <% if (user.getCountry().equals("OM")) out.println("selected"); %>>Oman</option>
									<option value="PK" <% if (user.getCountry().equals("PK")) out.println("selected"); %>>Pakistan</option>
									<option value="PW" <% if (user.getCountry().equals("PW")) out.println("selected"); %>>Palau</option>
									<option value="PS" <% if (user.getCountry().equals("PS")) out.println("selected"); %>>Palestinian Territory, Occupied</option>
									<option value="PA" <% if (user.getCountry().equals("PA")) out.println("selected"); %>>Panama</option>
									<option value="PG" <% if (user.getCountry().equals("PG")) out.println("selected"); %>>Papua New Guinea</option>
									<option value="PY" <% if (user.getCountry().equals("PY")) out.println("selected"); %>>Paraguay</option>
									<option value="PE" <% if (user.getCountry().equals("PE")) out.println("selected"); %>>Peru</option>
									<option value="PH" <% if (user.getCountry().equals("PH")) out.println("selected"); %>>Philippines</option>
									<option value="PN" <% if (user.getCountry().equals("PN")) out.println("selected"); %>>Pitcairn</option>
									<option value="PL" <% if (user.getCountry().equals("PL")) out.println("selected"); %>>Poland</option>
									<option value="PT" <% if (user.getCountry().equals("PT")) out.println("selected"); %>>Portugal</option>
									<option value="PR" <% if (user.getCountry().equals("PR")) out.println("selected"); %>>Puerto Rico</option>
									<option value="QA" <% if (user.getCountry().equals("QA")) out.println("selected"); %>>Qatar</option>
									<option value="RE" <% if (user.getCountry().equals("RE")) out.println("selected"); %>>Réunion</option>
									<option value="RO" <% if (user.getCountry().equals("RO")) out.println("selected"); %>>Romania</option>
									<option value="RU" <% if (user.getCountry().equals("RU")) out.println("selected"); %>>Russian Federation</option>
									<option value="RW" <% if (user.getCountry().equals("RW")) out.println("selected"); %>>Rwanda</option>
									<option value="BL" <% if (user.getCountry().equals("BL")) out.println("selected"); %>>Saint Barthélemy</option>
									<option value="SH" <% if (user.getCountry().equals("SH")) out.println("selected"); %>>Saint Helena, Ascension and Tristan da Cunha</option>
									<option value="KN" <% if (user.getCountry().equals("KN")) out.println("selected"); %>>Saint Kitts and Nevis</option>
									<option value="LC" <% if (user.getCountry().equals("LC")) out.println("selected"); %>>Saint Lucia</option>
									<option value="MF" <% if (user.getCountry().equals("MF")) out.println("selected"); %>>Saint Martin (French part)</option>
									<option value="PM" <% if (user.getCountry().equals("PM")) out.println("selected"); %>>Saint Pierre and Miquelon</option>
									<option value="VC" <% if (user.getCountry().equals("VC")) out.println("selected"); %>>Saint Vincent and the Grenadines</option>
									<option value="WS" <% if (user.getCountry().equals("WS")) out.println("selected"); %>>Samoa</option>
									<option value="SM" <% if (user.getCountry().equals("SM")) out.println("selected"); %>>San Marino</option>
									<option value="ST" <% if (user.getCountry().equals("ST")) out.println("selected"); %>>Sao Tome and Principe</option>
									<option value="SA" <% if (user.getCountry().equals("SA")) out.println("selected"); %>>Saudi Arabia</option>
									<option value="SN" <% if (user.getCountry().equals("SN")) out.println("selected"); %>>Senegal</option>
									<option value="RS" <% if (user.getCountry().equals("RS")) out.println("selected"); %>>Serbia</option>
									<option value="SC" <% if (user.getCountry().equals("SC")) out.println("selected"); %>>Seychelles</option>
									<option value="SL" <% if (user.getCountry().equals("SL")) out.println("selected"); %>>Sierra Leone</option>
									<option value="SG" <% if (user.getCountry().equals("SG")) out.println("selected"); %>>Singapore</option>
									<option value="SX" <% if (user.getCountry().equals("SX")) out.println("selected"); %>>Sint Maarten (Dutch part)</option>
									<option value="SK" <% if (user.getCountry().equals("SK")) out.println("selected"); %>>Slovakia</option>
									<option value="SI" <% if (user.getCountry().equals("SI")) out.println("selected"); %>>Slovenia</option>
									<option value="SB" <% if (user.getCountry().equals("SB")) out.println("selected"); %>>Solomon Islands</option>
									<option value="SO" <% if (user.getCountry().equals("SO")) out.println("selected"); %>>Somalia</option>
									<option value="ZA" <% if (user.getCountry().equals("ZA")) out.println("selected"); %>>South Africa</option>
									<option value="GS" <% if (user.getCountry().equals("GS")) out.println("selected"); %>>South Georgia and the South Sandwich Islands</option>
									<option value="SS" <% if (user.getCountry().equals("SS")) out.println("selected"); %>>South Sudan</option>
									<option value="ES" <% if (user.getCountry().equals("ES")) out.println("selected"); %>>Spain</option>
									<option value="LK" <% if (user.getCountry().equals("LK")) out.println("selected"); %>>Sri Lanka</option>
									<option value="SD" <% if (user.getCountry().equals("SD")) out.println("selected"); %>>Sudan</option>
									<option value="SR" <% if (user.getCountry().equals("SR")) out.println("selected"); %>>Suriname</option>
									<option value="SJ" <% if (user.getCountry().equals("SJ")) out.println("selected"); %>>Svalbard and Jan Mayen</option>
									<option value="SZ" <% if (user.getCountry().equals("SZ")) out.println("selected"); %>>Swaziland</option>
									<option value="SE" <% if (user.getCountry().equals("SE")) out.println("selected"); %>>Sweden</option>
									<option value="CH" <% if (user.getCountry().equals("CH")) out.println("selected"); %>>Switzerland</option>
									<option value="SY" <% if (user.getCountry().equals("SY")) out.println("selected"); %>>Syrian Arab Republic</option>
									<option value="TW" <% if (user.getCountry().equals("TW")) out.println("selected"); %>>Taiwan, Province of China</option>
									<option value="TJ" <% if (user.getCountry().equals("TJ")) out.println("selected"); %>>Tajikistan</option>
									<option value="TZ" <% if (user.getCountry().equals("TZ")) out.println("selected"); %>>Tanzania, United Republic of</option>
									<option value="TH" <% if (user.getCountry().equals("TH")) out.println("selected"); %>>Thailand</option>
									<option value="TL" <% if (user.getCountry().equals("TL")) out.println("selected"); %>>Timor-Leste</option>
									<option value="TG" <% if (user.getCountry().equals("TG")) out.println("selected"); %>>Togo</option>
									<option value="TK" <% if (user.getCountry().equals("TK")) out.println("selected"); %>>Tokelau</option>
									<option value="TO" <% if (user.getCountry().equals("TO")) out.println("selected"); %>>Tonga</option>
									<option value="TT" <% if (user.getCountry().equals("TT")) out.println("selected"); %>>Trinidad and Tobago</option>
									<option value="TN" <% if (user.getCountry().equals("TN")) out.println("selected"); %>>Tunisia</option>
									<option value="TR" <% if (user.getCountry().equals("TR")) out.println("selected"); %>>Turkey</option>
									<option value="TM" <% if (user.getCountry().equals("TM")) out.println("selected"); %>>Turkmenistan</option>
									<option value="TC" <% if (user.getCountry().equals("TC")) out.println("selected"); %>>Turks and Caicos Islands</option>
									<option value="TV" <% if (user.getCountry().equals("TV")) out.println("selected"); %>>Tuvalu</option>
									<option value="UG" <% if (user.getCountry().equals("UG")) out.println("selected"); %>>Uganda</option>
									<option value="UA" <% if (user.getCountry().equals("UA")) out.println("selected"); %>>Ukraine</option>
									<option value="AE" <% if (user.getCountry().equals("AE")) out.println("selected"); %>>United Arab Emirates</option>
									<option value="GB" <% if (user.getCountry().equals("GB")) out.println("selected"); %>>United Kingdom</option>
									<option value="US" <% if (user.getCountry().equals("US")) out.println("selected"); %>>United States</option>
									<option value="UM" <% if (user.getCountry().equals("UM")) out.println("selected"); %>>United States Minor Outlying Islands</option>
									<option value="UY" <% if (user.getCountry().equals("UY")) out.println("selected"); %>>Uruguay</option>
									<option value="UZ" <% if (user.getCountry().equals("UZ")) out.println("selected"); %>>Uzbekistan</option>
									<option value="VU" <% if (user.getCountry().equals("VU")) out.println("selected"); %>>Vanuatu</option>
									<option value="VE" <% if (user.getCountry().equals("VE")) out.println("selected"); %>>Venezuela, Bolivarian Republic of</option>
									<option value="VN" <% if (user.getCountry().equals("VN")) out.println("selected"); %>>Viet Nam</option>
									<option value="VG" <% if (user.getCountry().equals("VG")) out.println("selected"); %>>Virgin Islands, British</option>
									<option value="VI" <% if (user.getCountry().equals("VI")) out.println("selected"); %>>Virgin Islands, U.S.</option>
									<option value="WF" <% if (user.getCountry().equals("WF")) out.println("selected"); %>>Wallis and Futuna</option>
									<option value="EH" <% if (user.getCountry().equals("EH")) out.println("selected"); %>>Western Sahara</option>
									<option value="YE" <% if (user.getCountry().equals("YE")) out.println("selected"); %>>Yemen</option>
									<option value="ZM" <% if (user.getCountry().equals("ZM")) out.println("selected"); %>>Zambia</option>
									<option value="ZW" <% if (user.getCountry().equals("ZW")) out.println("selected"); %>>Zimbabwe</option>
						      	</select>
					 		</div>
						</div>
					</div>
			 	 </div>
			 	  
			 	 <input type="submit" value="Save" class="btn btn-primary btn-right">
			 	 
			</form>
		
		</div>
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
	
	</body>
</html>