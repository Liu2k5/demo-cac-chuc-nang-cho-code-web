package liu.democacchucnangchocodeweb.service.impl;

import jakarta.transaction.Transactional;
import liu.democacchucnangchocodeweb.dto.CustomerPageResponse;
import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.listener.event.DisableEvent;
import liu.democacchucnangchocodeweb.repository.CustomerRepository;
import liu.democacchucnangchocodeweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserService {
    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<Customer> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return customerRepository.findAll(pageable).getContent();
    }

    public CustomerPageResponse findPage(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        org.springframework.data.domain.Page<Customer> pageResult = customerRepository.findAll(pageable);
        return new CustomerPageResponse(
            pageResult.getContent(),
            pageResult.getTotalElements(),
            pageResult.hasNext()
        );
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

    @Override
    public List findAll() {
        return customerRepository.findAll();
    }
}
