package info.smartkit.godpaper.go.repository;

import info.smartkit.godpaper.go.pojo.Aier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by smartkit on 09/07/2017.
 */
public interface AierRepository extends MongoRepository<Aier,String> {
        List<Aier> findByStatus(int status);
}
