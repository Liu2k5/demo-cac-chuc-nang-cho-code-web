package liu.democacchucnangchocodeweb.service.impl;

import liu.democacchucnangchocodeweb.entity.Administrator;
import liu.democacchucnangchocodeweb.repository.AdminRepository;
import liu.democacchucnangchocodeweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService implements UserService {
    // đôi tượng final được tự động tiêm khi có constructor bao gồm nó
    private final AdminRepository adminRepository;

    @Override
    public List<Administrator> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Administrator findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

}
