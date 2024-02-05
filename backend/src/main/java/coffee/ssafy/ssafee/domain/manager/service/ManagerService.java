package coffee.ssafy.ssafee.domain.manager.service;

import coffee.ssafy.ssafee.domain.manager.dto.request.ManagerLoginRequest;
import coffee.ssafy.ssafee.domain.manager.dto.request.ManagerUpdateRequest;
import coffee.ssafy.ssafee.domain.manager.dto.response.ManagerResponse;
import coffee.ssafy.ssafee.domain.manager.entity.Manager;
import coffee.ssafy.ssafee.domain.manager.exception.ManagerErrorCode;
import coffee.ssafy.ssafee.domain.manager.exception.ManagerException;
import coffee.ssafy.ssafee.domain.manager.mapper.ManagerMapper;
import coffee.ssafy.ssafee.domain.manager.repository.ManagerRepository;
import coffee.ssafy.ssafee.jwt.JwtTokenProvider;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private static final String ROLE_MANAGER = "ROLE_MANAGER";
    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String login(ManagerLoginRequest managerLoginRequest) {
        Manager manager = managerRepository.findByIdAndPassword(managerLoginRequest.id(), managerLoginRequest.password())
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.INVALID_ID_OR_PASSWORD));
        return jwtTokenProvider.issueAccessToken(JwtPrincipalInfo.builder()
                .id(manager.getId())
                .shopId(manager.getShop().getId())
                .role(ROLE_MANAGER)
                .build());
    }

    @Transactional
    public void updateManager(String id, ManagerUpdateRequest managerUpdateRequest) {
        managerRepository.save(Manager.builder()
                .id(id)
                .password(managerUpdateRequest.password())
                .build());
    }

    @Transactional
    public ManagerResponse findManager(String id) {
        return managerRepository.findById(id)
                .map(managerMapper::toDto)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.NOT_FOUND_MANAGER));
    }

    public void validate(JwtPrincipalInfo principal, Long shopId) {
        if (!principal.shopId().equals(shopId)) {
            throw new ManagerException(ManagerErrorCode.UNAUTHORIZED_SHOP);
        }
    }

}
