package liu.democacchucnangchocodeweb.tool;

import liu.democacchucnangchocodeweb.service.impl.CustomerService;
import liu.democacchucnangchocodeweb.entity.Customer;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerTool {
    private final CustomerService customerService;

    @Tool(name = "count_customers", description = "Đếm số lượng khách hàng hiện có trong hệ thống")
    public Integer countCustomers() {
        return customerService.findAll().size();
    }

    @Tool(name = "check_customer_name", description = "Kiểm tra tên hiển thị của khách hàng theo username; trả về tên hoặc 'NOT_FOUND'")
    public String checkCustomerName(String username) {
        Customer c = customerService.findByUsername(username);
        if (c == null) {
            return "NOT_FOUND";
        }
        String name = c.getName();
        return (name == null || name.isBlank()) ? c.getUsername() : name;
    }

    @Tool(name = "enable_customer", description = "Kích hoạt tài khoản khách hàng theo username")
    public String enableCustomer(String username) {
        try {
            customerService.enableByUsername(username);
            return "CUSTOMER_ENABLED";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    @Tool(name = "disable_customer", description = "Vô hiệu hóa tài khoản khách hàng theo username")
    public String disableCustomer(String username) {
        try {
            customerService.disableByUsername(username);
            return "CUSTOMER_DISABLED";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    @Tool(name = "delete_customer", description = "Xóa tài khoản khách hàng theo username")
    public String deleteCustomer(String username) {
        try {
            customerService.deleteByUsername(username);
            return "CUSTOMER_DELETED";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }
}
