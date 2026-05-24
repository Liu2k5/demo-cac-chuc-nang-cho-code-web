package liu.democacchucnangchocodeweb.service;

import liu.democacchucnangchocodeweb.entity.Administrator;
import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.service.impl.AdminService;
import liu.democacchucnangchocodeweb.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    // MyUserDetailsService là một lớp dịch vụ tùy chỉnh triển khai UserDetailsService của Spring Security,
    // cung cấp cách để tải thông tin người dùng từ cơ sở dữ liệu hoặc nguồn dữ liệu khác để xác thực và ủy quyền trong ứng dụng.
    // Chú ý chỉ nên cho 1 lớp duy nhấy triển khai UserDetailService, nếu không Spring Security sẽ không biết nên sử dụng lớp nào để tải thông tin người dùng,
    // dẫn đến việc phải khai báo DaoAuthenticationProvider và chỉ định rõ ràng lớp nào sẽ được sử dụng, điều này làm tăng độ phức tạp của cấu hình bảo mật.
    private final AdminService adminService;
    private final CustomerService customerService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator admin = adminService.findByUsername(username);
        if (admin != null) {
            return admin;
        }
        Customer customer = customerService.findByUsername(username);
        if (customer != null) {
            return customer;
        }
        throw new UsernameNotFoundException(username);
    }
}
