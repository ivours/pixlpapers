package controllers;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import persistence.WallpapersRepository;
import domain.Wallpaper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class WallpapersController {

	WallpapersRepository wpRepo;
	Cloudinary cloudinary;
	List<Wallpaper> pendingQueue;

	public WallpapersController(WallpapersRepository wpRepo,
			Cloudinary cloudinary, List<Wallpaper> pendingQueue) {
		this.wpRepo = wpRepo;
		this.cloudinary = cloudinary;
		this.pendingQueue = pendingQueue;
	}

	public ModelAndView upload(Request request, Response response) {
		return new ModelAndView(null, "upload.hbs");
	}

	public ModelAndView showSuccessMessage(Request request, Response response) {
		return new ModelAndView(null, "success.hbs");
	}

	public ModelAndView showWallpapers(Request request, Response response) {

		int page = Integer.parseInt(request.params(":page"));

		HashMap<String, Object> viewModel = new HashMap<>();
		List<Wallpaper> wallpapers = wpRepo.showAllWallpapersSortedByNewest();
		wallpapers = wallpapers.subList(page * 12, wallpapers.size());
		wallpapers = wallpapers.stream().limit(12).collect(Collectors.toList());

		viewModel.put("wallpapers", wallpapers);

		if (page == 0) {
			viewModel.put("previousPage", null);
			viewModel.put("showPreviousPage", false);
		} else {
			viewModel.put("previousPage", page - 1);
			viewModel.put("showPreviousPage", true);
		}

		if (wallpapers.size() == 12)
			viewModel.put("nextPage", page + 1);
		else
			viewModel.put("nextPage", null);

		return new ModelAndView(viewModel, "wallpapers.hbs");
	}

	public ModelAndView add(Request request, Response response) {

		String uploader = request.queryParams("uploader");
		String url = request.queryParams("url");

		Wallpaper wallpaper = new Wallpaper();
		wallpaper.setUploader(uploader);
		wallpaper.setUrl(url);

		this.pendingQueue.add(wallpaper);

		response.redirect("/upload/success");

		return null;

	}

	public ModelAndView create(Request request, Response response) {

		Wallpaper wallpaper = pendingQueue.remove(0);

		Map uploadResult;
		
		try {
			uploadResult = cloudinary.uploader().upload(wallpaper.getUrl(),
					ObjectUtils.emptyMap());
			
			String cloudinaryUrl = (String) uploadResult.get("url");

			int width = (int) uploadResult.get("width");
			int height = (int) uploadResult.get("height");

			String thumbnailUrl = (String) cloudinary
					.uploader()
					.upload(wallpaper.getUrl(),
							ObjectUtils.asMap("transformation",
									new Transformation().width(350).height(197)
											.crop("limit"))).get("url");
			
			Dimension dimension = new Dimension();
			dimension.setSize(width, height);

			wallpaper.setCloudinaryUrl(cloudinaryUrl);
			wallpaper.setThumbnailUrl(thumbnailUrl);
			wallpaper.setDimension(dimension);
			wallpaper.setVisibility(true);

			wpRepo.save(wallpaper);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.redirect("/pendingwallpapers");

		return null;
	}
	
	public ModelAndView reject(Request request, Response response){
		
		pendingQueue.remove(0);
		
		response.redirect("/pendingwallpapers");

		return null;
		
	}

	public ModelAndView showWallpaper(Request request, Response response) {

		String wallpaperId = request.params(":id");

		HashMap<String, Object> viewModel = new HashMap<>();
		Wallpaper wallpaper = wpRepo.getWallpaper(wallpaperId);

		viewModel.put("wallpaper", wallpaper);

		return new ModelAndView(viewModel, "wallpaper.hbs");
	}

	public ModelAndView showPendingWallpapers(Request request, Response response) {

		HashMap<String, Object> viewModel = new HashMap<>();
		
		if(pendingQueue.size() > 0)
		{
			Wallpaper wallpaper = pendingQueue.get(0);
			viewModel.put("wallpaper", wallpaper);
		}
		
		return new ModelAndView(viewModel, "pending.hbs");
	}

	public ModelAndView showRandomWallpapers(Request request, Response response) {

		HashMap<String, Object> viewModel = new HashMap<>();
		List<Wallpaper> wallpapers = wpRepo.showRandomizedWallpapers();
		wallpapers = wallpapers.stream().limit(12).collect(Collectors.toList());

		viewModel.put("wallpapers", wallpapers);
		viewModel.put("random", true);

		return new ModelAndView(viewModel, "wallpapers.hbs");

	}

	public ModelAndView copyright(Request request, Response response) {
		return new ModelAndView(null, "copyright.hbs");
	}

}
