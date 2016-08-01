package controllers;


import org.mongodb.morphia.Morphia;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PagesController {
	
	Morphia morphia;
	
	public PagesController(Morphia morphia){
		this.morphia = morphia;
	}
	
	public ModelAndView home(Request request, Response response){
		
		return new ModelAndView(null, "home.hbs");
	}

}
