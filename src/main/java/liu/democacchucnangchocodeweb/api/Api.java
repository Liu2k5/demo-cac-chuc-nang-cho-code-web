package liu.democacchucnangchocodeweb.api;

import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.entity.User; // Import thêm User
import liu.democacchucnangchocodeweb.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // 1. Sửa từ @Controller thành @RestController
@RequiredArgsConstructor
// 2. Bỏ @RequestMapping(Api.API) ở đây vì các biến bên dưới đã có "/api" rồi
public class Api {
    public static final String API = "/api";
    public static final String ADMIN = API + "/admin";

    private final CustomerService customerService;

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
}