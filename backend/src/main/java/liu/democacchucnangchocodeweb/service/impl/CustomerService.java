package liu.democacchucnangchocodeweb.service.impl;

import jakarta.transaction.Transactional;
import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.listener.event.DisableEvent;
import liu.democacchucnangchocodeweb.repository.CustomerRepository;
import liu.democacchucnangchocodeweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserService {
    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    // vì hàm này thay đổi dữ liệu, annotation này là bắt buộc để Spring Security cho phép thưc thi
    @Transactional
    public void deleteByUsername(String username) {
        customerRepository.deleteByUsername(username);
    }

    @Transactional
    public void enableByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username);
        customer.setEnabled(true);
        customerRepository.save(customer);
    }
    @Transactional
    public void disableByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username);
        customer.setEnabled(false);
        customerRepository.save(customer);
        // kích hoạt event để lisstener tại DisableListener thấy và thực hiện hành động khóa tài khoản trong Spring Security
        applicationEventPublisher.publishEvent(new DisableEvent(customer.getUsername()));
    }
}
