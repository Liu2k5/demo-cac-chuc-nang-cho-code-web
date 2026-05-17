package liu.democacchucnangchocodeweb.configuration;

import liu.democacchucnangchocodeweb.entity.Administrator;
import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.repository.AdminRepository;
import liu.democacchucnangchocodeweb.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Initialisation implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

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
                        .build();
        Customer customer2 =
                Customer.builder()
                        .username("customer2")
                        .password("password")
                        .name("Customer One")
                        .emailAddress("customer2@mail.com")
                        .build();
        customerRepository.saveAll(List.of(customer1, customer2));
    }
}
