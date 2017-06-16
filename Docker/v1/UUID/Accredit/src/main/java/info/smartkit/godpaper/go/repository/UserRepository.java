package info.smartkit.godpaper.go.repository;

import info.smartkit.godpaper.go.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Created by smartkit on 16/06/2017.
 */
public interface UserRepository extends MongoRepository<User,String>{
}
