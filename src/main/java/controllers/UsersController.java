package controllers;

import org.mongodb.morphia.Morphia;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsersController {
	
	Morphia morphia;
	
	public UsersController(Morphia morphia){
		this.morphia = morphia;
	}
	
	public ModelAndView upload(Request request, Response response){
		
		
		
		
		return null;
	}

}
