package my_util;

public class EmailUtil {

	//valid email formatting
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".") && email.endsWith("@java.edu");
    }
}
