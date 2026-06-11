package liu.democacchucnangchocodeweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import liu.democacchucnangchocodeweb.entity.Order;
import liu.democacchucnangchocodeweb.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> findByCustomer_Username(String username) {
        return orderRepository.findByCustomer_Username(username).orElse(List.of());
    }

}
