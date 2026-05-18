package liu.democacchucnangchocodeweb.configuration;

import jakarta.servlet.http.HttpServletResponse;
import liu.democacchucnangchocodeweb.filter.LoginRequestValidationFilter;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
// kích hoạt tính năng Web Security trong ứng dụng Spring Boot, cho phép cấu hình bảo mật cho các endpoint và xác thực người dùng.
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {
    // các đường dẫn phải bắt đầu bằng dấu gạch chéo (/)
    private final String HOME_URL = "/";
    private final String[] ALL_ALLOWED = {"/", "/login/**", "/api/auth/**"};
    private final String[] ADMIN_ALLOWED = {"/admin/**", "/api/admin/**"};
    private final String[] CUSTOMER_ALLOWED = {"/customer/**", "/api/customer/**"};

    private final LoginRequestValidationFilter loginRequestValidationFilter;

    @Bean
    // "springSecurityFilterChain" trùng tên với bean mặc định của Spring Security
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000")); // Cho phép React
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                // cấu hình form login, chỉ định tên tham số cho tên người dùng là "email",
                // thiết lập handler xử lý lỗi đăng nhập và URL mặc định sau khi đăng nhập thành công.
                .formLogin(i -> i
                        .usernameParameter("email")
                        .failureHandler(loginFailureHandler())
                        .defaultSuccessUrl(HOME_URL, true)
                )
                // cấu hình đăng nhập với oauth2, thiết lập handler xử lý lỗi đăng nhập và URL mặc định sau khi đăng nhập thành công.
//                .oauth2Login(i -> i
//                        .failureHandler(oauth2FailureHandler())
//                        .defaultSuccessUrl(HOME_URL, true)
//                )
                // thêm filter kiểm tra định dạng các trường nhập trước khi thực hiện xác thực
                .addFilterBefore(loginRequestValidationFilter, UsernamePasswordAuthenticationFilter.class)
                // phân quyền truy cập
                .authorizeHttpRequests(i -> i
                        .requestMatchers(ALL_ALLOWED).permitAll()
                        .requestMatchers(ADMIN_ALLOWED).hasRole("ADMIN")
                        .requestMatchers(CUSTOMER_ALLOWED).hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                // cấu hình CSRF, bỏ qua kiểm tra CSRF cho endpoint "/webhook" để cho phép các yêu cầu từ bên ngoài mà không cần token CSRF.
                .csrf(csrf -> csrf.disable())
                // xử lí các ngoại lệ còn lại
                .exceptionHandling(i -> i
                        // Thay vì redirect đến /login, trả về mã 401 Unauthorized
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // handler xử lí lỗi đăng nhập với JSON response thay vì redirect đến trang lỗi
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {

            // thay vì chuyển hướng đến trang lỗi, trả về JSON với mã lỗi và thông điệp lỗi
            // giá trị SC_UNAUTHORIZED (401) được quy định sẵn làm mã trạng thái HTTP
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            // tạo một đối tượng cho JSON để chứa thông tin lỗi
            JSONObject json = new JSONObject();

            if (exception instanceof BadCredentialsException
                    || exception instanceof UsernameNotFoundException) {
//                response.sendRedirect("/login?incorrect_email_or_password");
                json.put("error", "incorrect_email_or_password");
            } else if (exception instanceof DisabledException) {
                json.put("error", "disabled");
            } else {
                json.put("error", exception.getMessage());
            }
            // gửi JSON về phía client
            response.getWriter().write(json.toString());
        };
    }

    public AuthenticationFailureHandler oauth2FailureHandler() {
        return (request, response, exception) -> {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            JSONObject json = new JSONObject();

            if (exception instanceof OAuth2AuthenticationException authEx) {
                String error = authEx.getError().getErrorCode();
                if (error.equals("account_disabled")) {
                    json.put("error", "account_disabled");
                } else {
                    json.put("error", error);
                }
            } else {
                json.put("error", exception.getMessage());
            }
            response.getWriter().write(json.toString());
        };
    }
}
