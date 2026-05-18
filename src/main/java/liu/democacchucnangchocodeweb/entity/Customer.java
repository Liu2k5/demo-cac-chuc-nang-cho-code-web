package liu.democacchucnangchocodeweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_CUSTOMER");
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
