package Comparators;

import java.util.Comparator;

import domain.Wallpaper;

public class NewerWallpaperComparator implements Comparator<Wallpaper> {
	
		@Override
		public int compare(Wallpaper wallpaper1, Wallpaper wallpaper2) {
			return wallpaper1.getId().compareTo(wallpaper2.getId());
		}

}
