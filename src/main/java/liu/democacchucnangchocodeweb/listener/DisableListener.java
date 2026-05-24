package liu.democacchucnangchocodeweb.listener;

import liu.democacchucnangchocodeweb.listener.event.DisableEvent;
import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisableListener {
    // Spring Session cho phép quản lí phiên đăng nhập của người dùng,
    // khi một tài khoản bị vô hiệu hóa, có thể sử dụng để xóa các phiên làm việc của tài khoản này để buộc đăng xuất
    // các phụ thuộc sau là cần thiết cho Spring Session

    //        <dependency>
    //            <groupId>org.springframework.session</groupId>
    //            <artifactId>spring-session-core</artifactId>
    //        </dependency>
    //        <dependency>
    //            <groupId>org.springframework.session</groupId>
    //            <artifactId>spring-session-jdbc</artifactId>
    //        </dependency>

    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    // listener này hứng event được phát từ hàm CustomerService.disableByUsername()
    @EventListener
    public void onDisableEvent(DisableEvent event){
        // không phải event.getUsername()
        sessionRepository.findByPrincipalName(event.username()).values().forEach(session -> {
            sessionRepository.deleteById(session.getId());
        });
    }


}
