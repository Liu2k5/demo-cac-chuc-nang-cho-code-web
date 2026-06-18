package liu.democacchucnangchocodeweb.repository;

import liu.democacchucnangchocodeweb.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Administrator,String> {
    Administrator findByUsername(String username);
}
