package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.pojo.User;
import info.smartkit.godpaper.go.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by smartkit on 16/06/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public List<User> createRandomUsers(int numbers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            User user = new User();
            user.setEmail(RandomStringUtils.randomAlphanumeric(7).toLowerCase().concat("@toyhouse.cc"));
            user.setFullName(RandomStringUtils.randomAlphanumeric(4).toUpperCase().concat(".").concat(RandomStringUtils.randomAlphanumeric(5).toUpperCase()));
            //Random rank
            Random generator = new Random();
            int rRank = 3 - generator.nextInt(3);
            user.setRank(rRank);
            User saved = repository.save(user);
            users.add(saved);
        }
        return users;
    }
}
