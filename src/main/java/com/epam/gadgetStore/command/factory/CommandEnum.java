package com.epam.gadgetStore.command.factory;

import static com.epam.gadgetStore.enums.UserRole.*;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.command.impl.*;
import com.epam.gadgetStore.command.impl.admin.*;
import com.epam.gadgetStore.enums.UserRole;

public enum CommandEnum {
	
	LIST_CUSTOMERS(new GetCustomerListPageCommand(), ADMIN),
	VIEW_CUSTOMER_DETAILS(new ViewCustomerDetailsCommand(), ADMIN),
	UPDATE_CUSTOMER_STATUS(new UpdateCustomerStatusCommand(), ADMIN),
	SHOW_ADMIN_PROFILE(new ShowAdminProfileCommand(), ADMIN),
	EDIT_ADMIN_PROFILE(new GetAdminEditProfilePageCommand(), ADMIN),
	UPDATE_ADMIN_PROFILE(new UpdateAdminProfileCommand(), ADMIN),
	LIST_CATEGORY(new GetCategoryListPageCommand(), ADMIN),
	NEW_CATEGORY(new GetNewCategoryPageCommand(), ADMIN), 
	EDIT_CATEGORY(new GetEditCategoryPageCommand(), ADMIN),
	SAVE_CATEGORY(new SaveCategoryCommand(), ADMIN),
	DELETE_CATEGORY(new DeleteCategoryCommand(), ADMIN),
	LIST_PRODUCTS(new GetProductListPageCommand(), ADMIN),
	NEW_PRODUCT(new GetNewProductPageCommand(), ADMIN),
	EDIT_PRODUCT(new GetEditProductPageCommand(), ADMIN),
	SAVE_PRODUCT(new SaveProductCommand(), ADMIN),
	DELETE_PRODUCT(new DeleteProductCommand(), ADMIN),
	LIST_ORDERS(new GetOrderListPageCommand(), ADMIN),
	VIEW_ORDER(new ViewOrderCommand(), ADMIN),
	UPDATE_ORDER (new UpdateOrderCommand(), ADMIN),
	GET_ADMIN_PAGE(new GetAdminPageCommand(), ADMIN),
	LOGOUT(new LogoutCommand(), GUEST),
	CHANGE_LANGUAGE(new ChangeLanguageCommand(), GUEST),
	SHOW_REGISTER_FORM(new ShowCustomerRegisterFormCommand(), GUEST),
	REGISTER_CUSTOMER(new RegisterCustomerCommand(), GUEST),
	SHOW_CUSTOMER_LOGIN_FORM(new ShowCustomerLoginFormCommand(), GUEST),
	CUSTOMER_LOGIN(new LoginCommand(), GUEST),
	SHOW_CUSTOMER_PROFILE(new ShowCustomerProfileCommand(), CUSTOMER),
	EDIT_CUSTOMER_PROFILE(new GetCustomerEditProfilePageCommand(), CUSTOMER),
	UPDATE_CUSTOMER_PROFILE(new UpdateCustomerProfileCommand(), CUSTOMER),
	VIEW_SHOPPING_CART(new ViewShoppingCartCommand(), GUEST),
	VIEW_PRODUCTS_BY_CATEGORY(new ViewProductsByCategoryCommand(), GUEST),
	VIEW_PRODUCT(new ViewProductCommand(), GUEST),
	ADD_TO_CART(new AddToCartCommand(), GUEST),
	REMOVE_FROM_CART(new RemoveFromCartCommand(), GUEST),
	UPDATE_CART(new UpdateCartCommand(), GUEST),
	CLEAR_CART(new ClearCartCommand(), GUEST),
	CHECKOUT(new GetCheckoutPageCommand(), GUEST),
	PLACE_ORDER(new PlaceOrderCommand(), CUSTOMER),
	VIEW_ORDERS_HISTORY (new ViewOrdersHistoryCommand(), CUSTOMER),
	VIEW_ORDER_DETAILS(new ViewOrderDetailsCommand(), CUSTOMER),
	SEARCH(new SearchCommand(), GUEST),
	HOME(new GetHomePageCommand(), GUEST);
	
	 private Command command;
	 private UserRole userRole;
	 

	    CommandEnum(Command command, UserRole userRole) {
	        this.command = command;
	        this.userRole = userRole;
	    }

	    public Command getCommand() {
	        return command;
	    }
	    
	    public UserRole getUserRole() {
	        return userRole;
	    }

}
