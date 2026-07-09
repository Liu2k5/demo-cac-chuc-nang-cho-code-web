package liu.democacchucnangchocodeweb.repository;

import liu.democacchucnangchocodeweb.entity.Administrator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator,String> {
    Administrator findByUsername(String username);

    Page<Administrator> findAll(Pageable pageable);
}
