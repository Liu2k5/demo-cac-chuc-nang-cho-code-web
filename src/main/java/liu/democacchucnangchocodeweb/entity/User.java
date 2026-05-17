package liu.democacchucnangchocodeweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
// annotation Builder cho phép tạo một đối tượng bằng cách nối tiếp các trường bằng dấu chấm, giúp mã dễ đọc và bảo trì hơn.
// SuperBuilder là một phần mở rộng của Builder, cho phép tạo các đối tượng kế thừa từ lớp cha một cách dễ dàng.
@SuperBuilder
// Tên bảng "user" trùng từ khóa trong các cơ sở dữ liệu
@Table(name = "users")
public class User implements UserDetails {
    // lớp này triển khai User Details để tích hợp với Spring Security, cung cấp thông tin về người dùng cho quá trình xác thực và ủy quyền.
    @Id
    private String username;
    private String password;

    // hàm được triển khai để Spring Security lấy thông tin về quyền hạn của người dùng,
    // trong trường hợp này trả về một danh sách rỗng vì User là lớp cần được mở rộng (extend)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
