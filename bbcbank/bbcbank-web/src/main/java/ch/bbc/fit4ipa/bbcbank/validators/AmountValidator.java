package ch.bbc.fit4ipa.bbcbank.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;

import ch.bbc.fit4ipa.bbcbank.exceptions.CustomValidatorException;

@FacesValidator("ch.bbc.fit4ipa.bbcbank.validators.AmountValidator")
public class AmountValidator extends BaseValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws CustomValidatorException {
		double amount = Double.parseDouble(value.toString());
		
		if (amount > 999999.0 || amount < 0.0) 
			throw new CustomValidatorException(createMessage("Der Betrag muss zwischen 0 und 999'999 liegen."));
		
		if (!checkPositionsAfterComma(amount))
			throw new CustomValidatorException(createMessage("Sie können beim Betrag nicht mehr als zwei Stellen nach dem Komma haben."));
	}
	
	/**
	 * Returns false if given amount has more than two digits after comma. Therefore
	 * given amount gets parsed to a string and counts numbers after "." sign.
	 * 
	 * @param  amount
	 * @return boolean
	 */
	public boolean checkPositionsAfterComma(double amount) {
		String text = Double.toString(Math.abs(amount));
		return (text.length() - text.indexOf('.') - 1) <= 2;
	}

}
