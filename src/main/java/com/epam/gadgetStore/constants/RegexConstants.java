package com.epam.gadgetStore.constants;

public final class RegexConstants {
	
	public static final int MIN_LENGTH_CARD_HOLDER = 3;
	public static final int MAX_LENGTH_CARD_HOLDER = 26;
    public static final int MAX_LENGTH_ADDRESS = 300;

	public static final String PHONE_NUMBER_REGEX = "(?:\\+|\\d)[\\d\\-\\(\\) ]{9,}\\d";
	public static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
	public static final String NAME_REGEX = "^[a-zа-яёA-ZА-ЯЁ]+(?:[-\\s][a-zа-яёA-ZА-ЯЁ]+){0,2}$";
	public static final String PASSWORD_REGEX = "[0-9a-zA-Z!@#$%^&*]{5,45}";
	public static final String CARD_HOLDER_REGEX = "^[a-zA-Z]+(?:[-\\s][a-zA-Z]+){0,4}$";
	public static final String CARD_NUMBER_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]"
			+ "|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}"
			+ "|(?:2131|1800|35\\d{3})\\d{11})$";
	public static final String SECURITY_CODE_REGEX = "^\\d{3,4}$";

	private RegexConstants() {

	}

}
