package persistence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.google.common.collect.Lists;

import Comparators.NewerWallpaperComparator;
import domain.Wallpaper;

public class WallpapersRepository {

	private Datastore datastore;

	public WallpapersRepository(Datastore datastore) {
		this.datastore = datastore;
	}

	public void save(Wallpaper wallpaper) {
		datastore.save(wallpaper);
	}

	public Wallpaper getWallpaper(String id) {
		return datastore
				.find(Wallpaper.class)
				.asList()
				.stream()
				.filter(wallpaper -> wallpaper.getId().toString()
						.equalsIgnoreCase(id)).collect(Collectors.toList())
				.get(0);
	}

	public List<Wallpaper> showAllWalpapers() {
		return datastore.find(Wallpaper.class).asList();
	}

	public List<Wallpaper> showAllWallpapersSortedByNewest() {

		List<Wallpaper> wallpapers = datastore.find(Wallpaper.class).asList();

		wallpapers = wallpapers.stream()
				.filter(wallpaper -> wallpaper.isVisible())
				.collect(Collectors.toList());

		return Lists.reverse(wallpapers);
	}
	
	public List<Wallpaper> showRandomizedWallpapers() {
		
		List<Wallpaper> wallpapers = datastore.find(Wallpaper.class).asList();
		
		Collections.shuffle(wallpapers);
		
		return wallpapers;
		
	}
	
}
