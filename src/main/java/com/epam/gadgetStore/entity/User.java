package com.epam.gadgetStore.entity;

public class User extends Entity {
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String password;
	private boolean isAdmin;
	private boolean isBlocked;
	
	public User() {
		super();
	}
	public User(String firstName, String lastName, String email, String password, boolean isAdmin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	public User(long id) {
		super(id);
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	

	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
	        return true;
	    }
		
	    if (obj == null || obj.getClass() != this.getClass()) {
	        return false;
	    }
	    
	    User otherUser = (User) obj;
	    if (getId() == null) {
			if (otherUser.getId() != null)
				return false;
		} else if (!getId().equals(otherUser.getId()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password
				+ ", isAdmin=" + isAdmin + "]";	
}
	
}
