package liu.democacchucnangchocodeweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
// annotation cân thiết để kích hoạt tính năng quản lý phiên làm việc (session) dựa trên JDBC trong ứng dụng Spring Boot.
// dự án này gặp lỗi không tự sinh bảng quan hệ cho spring session trong cơ sở dữ liệu, thực tế không cần annotation này
@EnableJdbcHttpSession
public class DemoCacChucNangChoCodeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoCacChucNangChoCodeWebApplication.class, args);
    }

}
