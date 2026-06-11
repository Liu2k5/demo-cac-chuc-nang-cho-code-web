package liu.democacchucnangchocodeweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import liu.democacchucnangchocodeweb.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<List<Order>> findByCustomer_Username(String username);

}
