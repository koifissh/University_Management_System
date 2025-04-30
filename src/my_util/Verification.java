package my_util;

import javax.swing.JOptionPane;

public class Verification {

	//validate input fields
	public static boolean validateInputFields(String email, String password) {
	    // Check for empty fields
	    if (email.isEmpty() || password.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Error! There is an empty field", 
	                "Empty Field", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    // Email format validation
	    if (!EmailUtil.isValidEmail(email)) {
	        JOptionPane.showMessageDialog(null, "Error! Enter a valid email address", 
	                "Invalid Email", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    return true;
	}
	
	
	
	 public static boolean validateFields(String firstName, String lastName, String email) {
	        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
	            JOptionPane.showMessageDialog(
	                null,
	                "Error! First name, last name, and email must be filled in.",
	                "Empty Field",
	                JOptionPane.ERROR_MESSAGE
	            );
	            return false;
	        }
	        return true;
	    }
}

