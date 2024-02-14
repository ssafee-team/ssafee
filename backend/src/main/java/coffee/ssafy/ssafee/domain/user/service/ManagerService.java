package coffee.ssafy.ssafee.domain.user.service;

import coffee.ssafy.ssafee.domain.user.dto.request.ManagerLoginRequest;
import coffee.ssafy.ssafee.domain.user.dto.request.ManagerUpdateRequest;
import coffee.ssafy.ssafee.domain.user.dto.response.ManagerResponse;
import coffee.ssafy.ssafee.domain.user.entity.Manager;
import coffee.ssafy.ssafee.domain.user.exception.ManagerErrorCode;
import coffee.ssafy.ssafee.domain.user.exception.ManagerException;
import coffee.ssafy.ssafee.domain.user.mapper.ManagerMapper;
import coffee.ssafy.ssafee.domain.user.repository.ManagerRepository;
import coffee.ssafy.ssafee.jwt.JwtTokenProvider;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public String login(ManagerLoginRequest managerLoginRequest) {
        Manager manager = managerRepository.findByIdAndPassword(managerLoginRequest.id(), managerLoginRequest.password())
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.INVALID_ID_OR_PASSWORD));
        return jwtTokenProvider.issueManagerAccessToken(JwtPrincipalInfo.builder()
                .id(manager.getId())
                .info(String.valueOf(manager.getShop().getId()))
                .role("ROLE_MANAGER")
                .build());
    }

    public void updateManager(String id, ManagerUpdateRequest managerUpdateRequest) {
        managerRepository.save(Manager.builder()
                .id(id)
                .password(managerUpdateRequest.password())
                .build());
    }

    @Transactional(readOnly = true)
    public ManagerResponse findManager(String id) {
        return managerRepository.findById(id)
                .map(managerMapper::toResponse)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.NOT_FOUND_MANAGER));
    }

    @Transactional(readOnly = true)
    public void validateShop(JwtPrincipalInfo principal, Long shopId) {
        if (!principal.shopId().equals(shopId)) {
            throw new ManagerException(ManagerErrorCode.UNAUTHORIZED_SHOP);
        }
    }

}
