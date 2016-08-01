package main;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.Bootstrap;

import org.mongodb.morphia.Morphia;

import persistence.WallpapersRepository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import controllers.PagesController;
import controllers.WallpapersController;
import domain.User;
import domain.Wallpaper;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

	public static void main(String[] args) {

		System.out.println("Iniciando servidor");
		
		List<Wallpaper> pendingQueue = new ArrayList<Wallpaper>();

		Morphia morphia = new Morphia();

		MongoClientURI uri = new MongoClientURI(
				"aca va un uri");
		MongoClient client = new MongoClient(uri);

		morphia.map(User.class);
		morphia.map(Wallpaper.class);

		WallpapersRepository wallpapersRepository = new WallpapersRepository(
				morphia.createDatastore(client, "nombre del repo"));

		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				  "cloud_name", "nombre",
				  "api_key", "key",
				  "api_secret", "pass")); 

		WallpapersController wallpapersController = new WallpapersController(
				wallpapersRepository, cloudinary, pendingQueue);

		Bootstrap bootstrap = new Bootstrap();

		staticFileLocation("/public");

		port(getHerokuAssignedPort());

		get("/", (request, response) -> {
			response.redirect("/wallpapers/0");
			return null;
		});
		get("/wallpapers/:page", wallpapersController::showWallpapers, engine);
		get("/random", wallpapersController::showRandomWallpapers, engine);
		get("/wallpaper/:id", wallpapersController::showWallpaper, engine);
		get("/upload", wallpapersController::upload, engine);
		get("/upload/success", wallpapersController::showSuccessMessage, engine);
		get("/pendingwallpapers", wallpapersController::showPendingWallpapers, engine);
		get("/copyright", wallpapersController::copyright, engine);
		get("/create", wallpapersController::create, engine);
		get("/reject", wallpapersController::reject, engine);
		post("/upload/new", wallpapersController::add);

	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on
						// localhost)
	}

}
