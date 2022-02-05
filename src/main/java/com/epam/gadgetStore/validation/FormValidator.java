package com.epam.gadgetStore.validation;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;

public interface FormValidator<E, K> {

	Set<K> getErrorAttributes(E e) throws IOException, ServletException;
}
