package liu.democacchucnangchocodeweb.repository;

import liu.democacchucnangchocodeweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // Giao diện này mở rộng JpaRepository, cung cấp các phương thức CRUD (Create, Read, Update, Delete) cho thực thể User.
    // JpaRepository cung cấp các phương thức như save(), findById(), findAll(), deleteById(), v.v., giúp quản lý dữ liệu người dùng một cách dễ dàng.
}
