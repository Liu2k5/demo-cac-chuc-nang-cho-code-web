package liu.democacchucnangchocodeweb.service;

import liu.democacchucnangchocodeweb.entity.User;
import liu.democacchucnangchocodeweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    // giao diện cho các service của admin và customer mở rộng, cung cấp các phương thức chung để quản lý người dùng.
    User findByUsername(String username);
}
