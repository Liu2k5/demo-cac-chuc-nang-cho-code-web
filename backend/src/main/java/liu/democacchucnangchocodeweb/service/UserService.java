package liu.democacchucnangchocodeweb.service;

import liu.democacchucnangchocodeweb.entity.User;
import liu.democacchucnangchocodeweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService<T extends User> {
    // giao diện cho các service của admin và customer mở rộng, cung cấp các phương thức chung để quản lý người dùng.
    List<User> findAll();
    User findByUsername(String username);
}
