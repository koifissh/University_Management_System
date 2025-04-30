package model;

public class User {

	// fields
	private int id;
	private String first_name;
	private String last_name;

	private String email;
	private String password;
	private String role_type;

	public User() {
	};

	public User(int id, String firstName, String lastName, String email, String password, String roleType) {
		this.id = id;
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.password = password;
		this.role_type = roleType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
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

	public String getRoleType() {
		return role_type;
	}

	public void setRoleType(String role_type) {
		this.role_type = role_type;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", firstName='" + first_name + '\'' + ", lastName='" + last_name + '\''
				+ ", email='" + email + '\'' + ", roleType='" + role_type + '\'' + '}';
	}

}