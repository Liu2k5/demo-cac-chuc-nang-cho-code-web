package liu.democacchucnangchocodeweb.configuration;

import liu.democacchucnangchocodeweb.entity.Administrator;
import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.repository.AdminRepository;
import liu.democacchucnangchocodeweb.repository.CustomerRepository;
import liu.democacchucnangchocodeweb.service.AiService;
import liu.democacchucnangchocodeweb.service.impl.AdminService;
import liu.democacchucnangchocodeweb.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class Initialisation implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Initialisation.class);
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final AiService aiService;

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

        // chạy bất đồng bộ
        CompletableFuture.runAsync(() -> aiService.loadDataToVectorDb());

        // // chưa rõ vì sao việc tạo tự động có vấn đề, ghi chú tạm ở đây
        // jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS SPRING_SESSION (PRIMARY_ID CHAR(36) NOT NULL, SESSION_ID CHAR(36) NOT NULL, CREATION_TIME BIGINT NOT NULL, LAST_ACCESS_TIME BIGINT NOT NULL, MAX_INACTIVE_INTERVAL INT NOT NULL, EXPIRY_TIME BIGINT NOT NULL, PRINCIPAL_NAME VARCHAR(100), CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID))");
        // jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID)");
        // jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME)");
        // jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME)");
        // jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES (SESSION_PRIMARY_ID CHAR(36) NOT NULL, ATTRIBUTE_NAME VARCHAR(200) NOT NULL, ATTRIBUTE_BYTES LONGVARBINARY NOT NULL, CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME), CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE)");
        // System.out.println("--- Spring Session tables checked/created ---");

        System.out.println("Du lieu da duoc luu:");
        System.out.println("Admin: " + adminService.findAll().stream().map(u -> u.getUsername()).toList().toString());
        System.out.println("Customer: " + customerService.findAll().stream().map(u -> u. getUsername()).toList().toString());
    }

}
