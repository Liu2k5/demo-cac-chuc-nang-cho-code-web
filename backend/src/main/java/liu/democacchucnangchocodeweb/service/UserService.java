package liu.democacchucnangchocodeweb.service;

import liu.democacchucnangchocodeweb.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService<T extends User> {
    // giao diện cho các service của admin và customer mở rộng, cung cấp các phương thức chung để quản lý người dùng.
    List<User> findAll();
    List<User> findAll(int page, int size, String sortBy);
    User findByUsername(String username);
}
