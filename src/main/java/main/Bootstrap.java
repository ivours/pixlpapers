package main;

import java.io.File;
import java.io.IOException;

import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import domain.Wallpaper;

public class Bootstrap {
	
	MongoClient client;
	GridFS fileSystem;
	
	public void setFileSystem(GridFS fileSystem){
		this.fileSystem = fileSystem;
	}
	
	public void setDb(MongoClient client){
		this.client = client;
	}
	
	public static void main(String[] args) throws IOException {
		new Bootstrap().run();
	}
	
	public void run() throws IOException{
			
		Wallpaper wallpaper = new Wallpaper();
		
		File imageFile = new File("/home/ivo/Imágenes/211691.jpg");
						
	}

}
