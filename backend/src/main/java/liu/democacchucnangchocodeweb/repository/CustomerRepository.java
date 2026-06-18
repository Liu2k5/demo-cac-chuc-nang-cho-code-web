package liu.democacchucnangchocodeweb.repository;

import liu.democacchucnangchocodeweb.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    // hàm được đặt theo quy tắc đặt tên của Spring Data JPA, trong đó "findBy" được theo sau bởi tên thuộc tính của thực thể (trong trường hợp này là "Username").
    // Khi bạn gọi phương thức này, Spring Data JPA sẽ tự động tạo truy vấn để tìm kiếm khách hàng dựa trên tên người dùng đã cung cấp.
    // Cực kì lưu ý tên được đặt phải đúng với tên thuộc tính của thực thể, nếu không sẽ gây lỗi khi Spring Data JPA cố gắng tạo truy vấn dựa trên tên phương thức.
    public Customer findByUsername(String username);

    void deleteByUsername(String username);
}
