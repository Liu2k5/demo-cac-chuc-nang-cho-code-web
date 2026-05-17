package liu.democacchucnangchocodeweb.service.impl;

import liu.democacchucnangchocodeweb.entity.Customer;
import liu.democacchucnangchocodeweb.entity.User;
import liu.democacchucnangchocodeweb.repository.CustomerRepository;
import liu.democacchucnangchocodeweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public void deleteByUsername(String username) {
        customerRepository.deleteByUsername(username);
    }
}
