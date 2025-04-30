package app;

import controller.AuthController;
import model.User;
import view.AuthView;

public class Main {

	public static void main(String[] args) {
		//create our view, model, and controller which starts at the login screen
		AuthView view = new AuthView();
        User model = new User();
        new AuthController(view, model);
        
        //display the login screen
        view.setVisible(true);
	}

}
