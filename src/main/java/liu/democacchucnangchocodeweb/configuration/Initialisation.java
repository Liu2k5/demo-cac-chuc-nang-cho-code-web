package liu.democacchucnangchocodeweb.configuration;

import liu.democacchucnangchocodeweb.entity.Administrator;
import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.entity.User;
import liu.democacchucnangchocodeweb.repository.AdminRepository;
import liu.democacchucnangchocodeweb.repository.CustomerRepository;
import liu.democacchucnangchocodeweb.service.UserService;
import liu.democacchucnangchocodeweb.service.impl.AdminService;
import liu.democacchucnangchocodeweb.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Initialisation implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Initialisation.class);
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final AdminService adminService;

    // annotation Value lấy giá trị với tên tương ứng từ tệp cấu hình (properties hoặc yaml) và gán chúng cho các biến adminUsername và adminPassword.
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    // triển khai hàm run() từ CommandLineRunner để thực hiện các tác vụ khởi tạo khi ứng dụng Spring Boot bắt đầu chạy.
    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem có tồn tại người dùng quản trị với tên người dùng đã chỉ định hay không. Nếu không tồn tại, tạo một người dùng quản trị mới với tên người dùng và mật khẩu đã chỉ định.
        if (adminRepository.findByUsername(adminUsername) == null) {
            Administrator admin =
                    Administrator.builder()
                                .username(adminUsername)
                                .password(adminPassword)
                                .build();
            adminRepository.save(admin);
        }
        Customer customer1 =
                Customer.builder()
                        .username("customer1")
                        .password("password")
                        .name("Customer One")
                        .emailAddress("customer1@mail.com")
                        .isEnabled(true)
                        .build();
        Customer customer2 =
                Customer.builder()
                        .username("customer2")
                        .password("password")
                        .name("Customer One")
                        .emailAddress("customer2@mail.com")
                        .isEnabled(false)
                        .build();
        customerRepository.saveAll(List.of(customer1, customer2));

        System.out.println("Du lieu da duoc luu:");
        System.out.println("Admin: " + adminService.findAll().stream().map(u -> u.getUsername()).toList().toString());
        System.out.println("Customer: " + customerService.findAll().stream().map(u -> u. getUsername()).toList().toString());
    }
}
