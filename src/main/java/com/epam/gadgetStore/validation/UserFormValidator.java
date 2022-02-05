package com.epam.gadgetStore.validation;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.RegexConstants.*;

import java.util.HashSet;
import java.util.Set;

import com.epam.gadgetStore.dao.UserDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.entity.User;

public class UserFormValidator implements FormValidator<User, String> {

	private static UserFormValidator instance = new UserFormValidator();

	private UserFormValidator() {

	}

	@Override
	public Set<String> getErrorAttributes(User user) {
		Set<String> errorAttributes = new HashSet<>();
		UserDAO USER_DAO = (UserDAO) DAOFactory.USER_DAO.getDAO();

		if (!user.getEmail().matches(EMAIL_REGEX))
			errorAttributes.add(WRONG_EMAIL_ATTRIBUTE);

		if (!user.getPhoneNumber().matches(PHONE_NUMBER_REGEX))
			errorAttributes.add(WRONG_PHONE_ATTRIBUTE);

		if (!user.getFirstName().matches(NAME_REGEX))
			errorAttributes.add(WRONG_FIRSTNAME_ATTRIBUTE);

		if (!user.getLastName().matches(NAME_REGEX))
			errorAttributes.add(WRONG_LASTNAME_ATTRIBUTE);

		if (!user.getPassword().matches(PASSWORD_REGEX))
			errorAttributes.add(WRONG_PASSWORD_ATTRIBUTE);

		User userByEmail = USER_DAO.findByEmail(user.getEmail());

		if (userByEmail != null && !user.equals(userByEmail))
			errorAttributes.add(DUPLICATE_EMAIL_ATTRIBUTE);

		User userByPhone = USER_DAO.findByPhone(user.getPhoneNumber());

		if (userByPhone != null && !user.equals(userByPhone))
			errorAttributes.add(DUPLICATE_PHONE_ATTRIBUTE);

		return errorAttributes;
	}

	public static UserFormValidator getInstance() {
		if (instance == null) {
			instance = new UserFormValidator();
		}
		return instance;
	}
}
