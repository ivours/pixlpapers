package main;

import static spark.Spark.*;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;
import main.Bootstrap;

import org.eclipse.jetty.util.Jetty;
import org.mongodb.morphia.Morphia;

import persistence.UsersRepository;
import persistence.WallpapersRepository;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;

import controllers.PagesController;
import controllers.UsersController;
import controllers.WallpapersController;
import domain.User;
import domain.Wallpaper;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {

	public static void main(String[] args) {

		System.out.println("Iniciando servidor");

		Morphia morphia = new Morphia();
		MongoClient client = new MongoClient();

		morphia.map(User.class);
		morphia.map(Wallpaper.class);

		UsersRepository usersRepository = new UsersRepository(
				morphia.createDatastore(client, "users"));
		WallpapersRepository wallpapersRepository = new WallpapersRepository(
				morphia.createDatastore(client, "wallpapers"));
		
		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
		
		PagesController pagesController = new PagesController(morphia);
		WallpapersController wallpapersController = new WallpapersController(wallpapersRepository);
		
		Bootstrap bootstrap = new Bootstrap();
		
	    staticFileLocation("/public");
	    
	    port(getHerokuAssignedPort());
	    
	    get("/", (request, response) -> {
	        response.redirect("/wallpapers/0");
	        return null;
	      });
	    get("/wallpapers/:page", wallpapersController::showWallpapers, engine);
	    get("/upload", wallpapersController::upload , engine);
	    get("/upload/success", wallpapersController::showSuccessMessage, engine);
	    post("/upload/new", wallpapersController::create);
		
	}
	
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
