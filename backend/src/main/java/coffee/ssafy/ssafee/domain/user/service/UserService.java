package coffee.ssafy.ssafee.domain.user.service;

import coffee.ssafy.ssafee.domain.user.entity.User;
import coffee.ssafy.ssafee.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(String email) {
        return userRepository.save(userRepository.findByEmail(email)
                .orElse(User.builder().email(email).build()));
    }

}
