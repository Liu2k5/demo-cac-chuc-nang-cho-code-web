package liu.democacchucnangchocodeweb.api;

import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.entity.Order;
import liu.democacchucnangchocodeweb.record.AiMessage;
import liu.democacchucnangchocodeweb.record.AiMessageRecord;
import liu.democacchucnangchocodeweb.service.AiService;
import liu.democacchucnangchocodeweb.service.OrderService;
import liu.democacchucnangchocodeweb.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;


@Slf4j
@RestController // 1. Sửa từ @Controller thành @RestController
@RequiredArgsConstructor
// 2. Bỏ @RequestMapping(Api.API) ở đây vì các biến bên dưới đã có "/api" rồi
public class Api {
    public static final String API = "/api";
    public static final String ADMIN = API + "/admin";
    public static final String CUSTOMER = API + "/customer";


    private final CustomerService customerService;
    private final AiService aiService;
    private final OrderService orderService;

    @GetMapping(ADMIN + "/manage-customer") // Đường dẫn: /api/admin/customers
    public List<Customer> getCustomers() { // 3. Sửa kiểu trả về thành List<User> cho khớp với Service
        System.out.println("get all customers");
        return customerService.findAll();
    }

    // việc truyền tham sô lên url là nguy hiểm, nhưng chấp nhận được nếu hệ thống sử dụng phân quyền
    @DeleteMapping(ADMIN + "/manage-customer/{username}") // Đường dẫn: /api/admin/customers/{id}
    public void deleteCustomer(@PathVariable String username) {
        try {
            System.out.println("delete customer with username: " + username);
            customerService.deleteByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @PutMapping(ADMIN + "/manage-customer/{username}/{status}") // Đường dẫn: /api/admin/customers/{id}
    public void changeCustomerStatus(@PathVariable String username,
                                     @PathVariable String status) {
        try {
            System.out.println("update customer with username: " + username + " to status: " + status);
            if (status.equals("enable")) {
                customerService.enableByUsername(username);
            } else if (status.equals("disable")) {
                customerService.disableByUsername(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping(API + "/auth/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        // Authentication là một đối tượng được Spring Security sử dụng để lưu trữ thông tin về người dùng đã xác thực, bao gồm tên người dùng, vai trò và các chi tiết khác.
        // hàm này trả về thông tin của người dùng hiện tại đang đăng nhập, được lấy từ đối tượng Authentication.
        if (authentication == null) {
            return null;
        }
        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @GetMapping(ADMIN + "/ai")
    @SuppressWarnings("unchecked")
    public List<AiMessage> Ai(HttpSession session) {
        String conversationId = (String) session.getAttribute("conversationId");
        List<AiMessage> conversation = (List<AiMessage>) session.getAttribute("conversation");
        if (conversationId == null) {
            conversationId = UUID.randomUUID().toString();
            session.setAttribute("conversationId", conversationId);
        }
        if (conversation == null) {
            conversation = new ArrayList<>();
            session.setAttribute("conversation", conversation);
        }
        return conversation;
    }

    @PostMapping(ADMIN + "/ai")
    @SuppressWarnings("unchecked")
    public ResponseEntity<List<AiMessage>> Ai(
        @RequestBody AiMessageRecord request,
        HttpSession session
    ) {
        String conversationId = (String) session.getAttribute("conversationId");
        if (conversationId == null) {
            conversationId = UUID.randomUUID().toString();
            session.setAttribute("conversationId", conversationId);
        }
        List<AiMessage> conversation = (List<AiMessage>) session.getAttribute("conversation");
        aiService.ask(conversationId, request.question(), conversation);
        session.setAttribute("conversation", conversation);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping(CUSTOMER + "/orders")
    public List<Order> getOrders(Principal principal) {
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        return orderService.findByCustomer_Username(customer.getUsername());
    }

    @PostMapping(CUSTOMER + "/pay")
    public String pay(
        @RequestAttribute String orderId,
        Principal principal) {
        long fetchedOrderId;
        try {
            fetchedOrderId = Long.parseLong(orderId);
            return orderService.getCheckoutUrlByOrderId(fetchedOrderId);
        } catch (Exception e) {
            System.err.println("error in parsing number");
            return null;
        }
    }
    
}