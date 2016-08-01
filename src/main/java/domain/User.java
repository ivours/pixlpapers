package domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class User {
	
	@Id
	ObjectId id;
	
	private String username;
	private String password;
	private List<Wallpaper> uploadedWallpapers;
	private List<Wallpaper> favouriteWallpapers;
	
	public void addToFavourites(Wallpaper wallpaper){
		favouriteWallpapers.add(wallpaper);
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	
}
