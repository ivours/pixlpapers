package persistence;

import java.util.List;

import org.mongodb.morphia.Datastore;

import domain.User;

public class UsersRepository {
	
	private Datastore datastore;
	
	public UsersRepository(Datastore datastore){
		this.datastore = datastore;
	}
	
	public void save(User user){
		datastore.save(user);
	}
	
	public List<User> showAllUsers(){
		return datastore.find(User.class).asList();
	}

}
