package info.smartkit.godpaper.go.repository;

import info.smartkit.godpaper.go.pojo.Gamer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by smartkit on 22/06/2017.
 */
public interface GamerRepository extends MongoRepository<Gamer,String> {
//        List<Gamer> findByStatus(int status);
}
