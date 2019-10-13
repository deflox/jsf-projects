/**
 *
 * Provides validation for forms.
 *
 * Goes through all form elements (type text and password) and check if they're empty.
 *
 * To check for valid email, add class 'validate-email' to your form element.
 * To check if two passwords are matching, add pw1 to the first one and pw2 to the second. (The
 * error will be displayed under pw1)
 *
 * The error will be displayed in a span element. Script searches for a span-element in the
 * parent div.
 *
 * @Author Leo Rudin
 *
 */

$(document).ready(function () {

    $("form").submit(function(e){

        // Check if empty
        $(this).find('input[type=text], input[type=password], input[type=number]').each(function() {
            if($(this).val() == "" || $(this).val() == 0) {
                $(this).parent().find('span').html('This field is required.');
                e.preventDefault();
            } else {
                $(this).parent().find('span').html('');
            }
        });

        // Check email
        $(this).find('.validate-email').each(function() {
            if ($(this).val() != "") {
                if (!validateEmail($(this).val())) {
                    $(this).parent().find('span').html('Please provide a valid email.');
                    e.preventDefault();
                }
            }
        });

        // Check if passwords match
        var pw1 = $(this).find('.pw1');
        var pw2 = $(this).find('.pw2');

        if (pw1.length !== 0 && pw2.length !== 0) {
            if (pw1.val() != "" && pw2.val() != "") {
                if (pw1.val() != pw2.val()) {
                    pw1.parent().find('span').html('The both passwords do not match');
                    pw2.parent().find('span').html('');
                    e.preventDefault();
                } else if (!validatePassword(pw1.val())) {
                    pw1.parent().find('span').html('The password has to contain at least 8 chars, numbers and, capital letters');
                    pw2.parent().find('span').html('');
                    e.preventDefault();
                }
            }
        }

    });

});

/**
 * Validates an email with a regex.
 *
 * @param email:      The email to be validated.
 * @returns {boolean} True if validation was successful
 */
function validateEmail(email) {
    var re = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z.]{2,5}$/i;
    return re.test(email);
}

/**
 * Validates password. It has to contain at least 8 chars, numbers and capital letters
 *
 * @param password The password to be validated.
 */
function validatePassword(password) {
    var re = /^((?=.*[A-Z])(?=.*\d)\S{8,})$/i;
    return re.test(password);
}

/**
 * Validator helper for email check
 */
