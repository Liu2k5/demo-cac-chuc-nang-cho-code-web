package liu.democacchucnangchocodeweb.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import liu.democacchucnangchocodeweb.entity.Order;
import liu.democacchucnangchocodeweb.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final PayOS payOS;
    private final OrderRepository orderRepository;


    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> findByCustomer_Username(String username) {
        return orderRepository.findByCustomer_Username(username).orElse(List.of());
    }

    public String getCheckoutUrlByOrderId(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return "";

        CreatePaymentLinkRequest paymentData =
        CreatePaymentLinkRequest.builder()
                .orderCode(order.getId())
                .amount(order.getTotalAmount())
                .expiredAt(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toEpochSecond())
                .description("FS" + order.getId())
                .returnUrl("http://localhost:8080/customer/order-success")
                .cancelUrl("http://localhost:8080/customer/order-cancel")
                .build();
        return payOS.paymentRequests().create(paymentData).getCheckoutUrl();
    }

}
