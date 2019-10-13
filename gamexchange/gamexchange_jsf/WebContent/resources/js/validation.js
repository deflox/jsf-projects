/**
 * Provides some JavaScript validation to your form elements. 
 * 
 * You can add one or more of the below classes to your input elements, to
 * get the validation to work. Currently only works with one form.
 * 
 * required: Field must be filled out.
 * email:    Field must contain a email in a valid format.
 * password & passwordRepeat: 
 * Add this two classes to two password fields and it will
 * compare both passwords to each other, if they match.
 * 
 * Results will be printed in a container with id "errors"
 * 
 */

jQuery(document).ready(function () {
			
	$("form").submit(function(e){
    	    	
		// Prevent the form to be submitted
	    e.preventDefault();
	    			
	    // Sets the required variables.
	    var errors = 0;
	    var errortext = "<ul>";
	    
	    // Validation for required fields.
	    if ($(".required").length) {
	    	if (!checkIfEmpty()) {
		    	errors++;
		    	errortext += "<li>Please fill out every required input. </li>";
		    }
	    }
	    
	    // Email validation
	    if ($(".email").length) {
	    	if (!validateEmail($(".email").val())) {
		    	errors++;
		    	errortext += "<li>One or more of the provided emails is not correct.</li>";
		    }
	    }
	    
	    // Validation if password are matching
	    if ($(".password").length && $(".passwordRepeat").length) {
	    	if ($(".password").val() != $(".passwordRepeat").val()) {
				errors++;
				errortext += "<li>The password do not match</li>";
			}
	    }
	    
	    // Print the summary in case there are errors. Otherwise submit the form
	    if (errors == 0) {
	    	$(this).unbind('submit').submit();
	    } else {
	    	$("#errors").html("Following errors occured: <br />" + errortext + "</ul>");
	    	$("#errors").fadeIn();
	    }
    
    });
			
});
		
/**
 * Maps through all fields which contains "required" and check if they're empty.
 * 
 * @returns {Boolean} False, if one field was empty.
 */
function checkIfEmpty() {
   	var empty = true;
   	$(".required").map(function() { 
   		if($(this).val() == "") { 
   			empty = false;
   		} 
   	});
   	return empty;
}

/**
 * Maps through all fields which contains "email" and check if they contain a valid email string.
 * 
 * @returns {Boolean} False, if one field is not an valid email.
 */
function checkEmails() {
	var allValid = email;
   	$(".email").map(function() { 
   		if(!validateEmail($(this).val())) { 
   			allValid = false;;
   		} 
   	});
   	return allValid;
}
	    
/**
 * Tests an email with a regex, to make sure it is in a valid format.
 * 
 * @param email: String you want to check. 
 * @returns {Boolean} If testing was successful.
 */
function validateEmail(email) {
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return re.test(email);
}