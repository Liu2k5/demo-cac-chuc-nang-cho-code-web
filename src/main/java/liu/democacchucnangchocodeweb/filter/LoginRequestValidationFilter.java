package liu.democacchucnangchocodeweb.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoginRequestValidationFilter extends OncePerRequestFilter {
    // lớp này mở rộng OncePerRequestFilter, đảm bảo rằng bộ lọc chỉ được thực thi một lần cho mỗi yêu cầu HTTP.
    // Bộ lọc này có thể được sử dụng để thực hiện các kiểm tra hoặc xác thực bổ sung trước khi quá trình xác thực chính diễn ra.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {
        // Phương thức này được gọi cho mỗi yêu cầu HTTP. Bạn có thể thêm logic kiểm tra ở đây, ví dụ:
        // - Kiểm tra xem yêu cầu có phải là yêu cầu đăng nhập hay không.
        // - Kiểm tra các tham số của yêu cầu (ví dụ: email và password).
        // - Nếu có lỗi, bạn có thể trả về phản hồi lỗi ngay lập tức mà không cần tiếp tục chuỗi bộ lọc.

        // Ví dụ: Kiểm tra nếu yêu cầu là POST đến /login và thiếu tham số email hoặc password
        if ("POST".equalsIgnoreCase(request.getMethod()) && "/login".equals(request.getServletPath())) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email == null || password == null) {
                response.sendRedirect("/login?missing_email_or_password");
                return; // Dừng chuỗi bộ lọc nếu thiếu thông tin đăng nhập
            }
        }

        // Tiếp tục chuỗi bộ lọc nếu mọi thứ ổn
        filterChain.doFilter(request, response);
    }
}
