package coffee.ssafy.ssafee.domain.user.service;

import coffee.ssafy.ssafee.domain.user.dto.request.ManagerLoginRequest;
import coffee.ssafy.ssafee.domain.user.dto.request.ManagerUpdateRequest;
import coffee.ssafy.ssafee.domain.user.dto.response.ManagerResponse;
import coffee.ssafy.ssafee.domain.user.entity.User;
import coffee.ssafy.ssafee.domain.user.entity.UserAuthority;
import coffee.ssafy.ssafee.domain.user.exception.UserErrorCode;
import coffee.ssafy.ssafee.domain.user.exception.UserException;
import coffee.ssafy.ssafee.domain.user.mapper.UserMapper;
import coffee.ssafy.ssafee.domain.user.repository.UserRepository;
import coffee.ssafy.ssafee.jwt.JwtTokenProvider;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public String login(ManagerLoginRequest managerLoginRequest) {
        User user = userRepository.findByUsernameAndPassword(managerLoginRequest.id(), managerLoginRequest.password())
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_ID_OR_PASSWORD));
        return jwtTokenProvider.issueManagerAccessToken(JwtPrincipalInfo.builder()
                .id(user.getId())
                .shopId(user.getShop().getId())
                .authority(UserAuthority.MANAGER.name())
                .build());
    }

    public void update(Long id, ManagerUpdateRequest managerUpdateRequest) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_MANAGER))
                .update(managerUpdateRequest);
    }

    @Transactional(readOnly = true)
    public ManagerResponse find(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_MANAGER));
    }

}
