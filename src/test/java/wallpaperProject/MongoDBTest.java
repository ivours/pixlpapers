package wallpaperProject;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import domain.User;
import domain.Wallpaper;
import persistence.UsersRepository;
import persistence.WallpapersRepository;

public class MongoDBTest extends InitTest {
	
	UsersRepository usersRepository;
	WallpapersRepository wallpapersRepository;
	MongoClient client;
	Morphia morphia;
	
	@Before
	public void init(){
		
		this.setUp();
		
		client = new MongoClient();
		morphia = new Morphia();
		
		morphia.map(User.class);
		morphia.map(Wallpaper.class);
		
		usersRepository = new UsersRepository(morphia.createDatastore(client, "users"));
		wallpapersRepository = new WallpapersRepository(morphia.createDatastore(client, "wallpapers"));
	}
	
	@Test 
	public void pixelOneIsSavedOnRepo(){
		wallpapersRepository.save(pixelOne);
		assertEquals(pixelOne.getId(), wallpapersRepository.showAllWalpapers().get(0).getId());
	}
	
	@Test
	public void ivoIsSavedOnRepo(){
		usersRepository.save(ivo);
		assertEquals(ivo.getId(), usersRepository.showAllUsers().get(0).getId());
	}
	
	@After
	public void dropDatabases(){
		client.dropDatabase("wallpapers");
		client.dropDatabase("users");
	}

}
