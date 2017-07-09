package info.smartkit.godpaper.go.repository;

import info.smartkit.godpaper.go.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by smartkit on 16/06/2017.
 */
public interface UserRepository extends MongoRepository<User,String>{
        List<User> findByStatus(int status);
        List<User> findAllByOrderByCreatedDesc();
}
