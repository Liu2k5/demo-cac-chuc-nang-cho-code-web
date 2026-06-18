package liu.democacchucnangchocodeweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Customer extends User {
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    private Date dateOfBirth;
    @Column(unique = true)
    private String emailAddress;
    @Column(length = 11, unique = true)
    private String phoneNumber;
    private boolean isEnabled;

    // khuyến khích việc sử dụng getAuthoritíes() hơn là getRoles(),
    // trong thực tế gàm getRoles() gây một số khó khắn trong việc xác định quyền tại lớp Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    // xác định trạng thái tài khoản bị khóa
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
