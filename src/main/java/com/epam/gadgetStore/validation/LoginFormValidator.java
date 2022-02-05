package com.epam.gadgetStore.validation;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.RegexConstants.*;

import java.util.HashSet;
import java.util.Set;

import com.epam.gadgetStore.dto.LoginDTO;

public class LoginFormValidator implements FormValidator<LoginDTO, String> {
	
	private static LoginFormValidator instance = new LoginFormValidator();
	
	private LoginFormValidator() {
		
	}

	@Override
	public Set<String> getErrorAttributes(LoginDTO loginDTO) {
		Set<String> errorAttributes = new HashSet<>();
		
		if (!loginDTO.getEmail().matches(EMAIL_REGEX)) 
			errorAttributes.add(WRONG_EMAIL_ATTRIBUTE);
		
		if (!loginDTO.getPassword().matches(PASSWORD_REGEX))
			errorAttributes.add(WRONG_PASSWORD_ATTRIBUTE);
		
		return errorAttributes;
	}
	
	public static LoginFormValidator getInstance() {
		if (instance == null) {
            instance = new LoginFormValidator();
        }
        return instance;
	}

}
