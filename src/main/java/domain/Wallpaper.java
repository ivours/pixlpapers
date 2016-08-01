package domain;

import java.awt.Dimension;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import com.mongodb.gridfs.GridFS;

import exceptions.DoesNotHaveNameException;
import exceptions.InvalidDescriptionException;
import exceptions.InvalidNameException;


public class Wallpaper {
	
	@Id
	private ObjectId id;
	
	private String url;
	private String cloudinaryUrl;
	private String thumbnailUrl;
	private String uploader;
	private Dimension dimension;
	private boolean visible;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	public int getWidth() {
		return this.dimension.width;
	}
	
	public int getHeight() {
		return this.dimension.height;
	}
	
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	
	public String getUploader() {
		return uploader;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public String getCloudinaryUrl() {
		return cloudinaryUrl;
	}

	public void setCloudinaryUrl(String cloudinaryUrl) {
		this.cloudinaryUrl = cloudinaryUrl;
	}
	
	public void setVisibility(Boolean visibility){
		this.visible = visibility;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	

}
