package info.smartkit.godpaper.go.service;

import info.smartkit.godpaper.go.pojo.User;

import java.util.List;

/**
 * Created by smartkit on 16/06/2017.
 */
public interface UserService {
        List<User> createRandomUsers(int numbers);

}
