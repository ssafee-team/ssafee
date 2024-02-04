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
import coffee.ssafy.ssafee.jwt.dto.JwtClaimInfo;
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

    public String login(ManagerLoginRequest managerLoginRequest) {
        boolean exists = managerRepository.existsByIdAndPassword(managerLoginRequest.id(), managerLoginRequest.password());
        if (!exists) {
            throw new ManagerException(ManagerErrorCode.INVALID_ID_OR_PASSWORD);
        }
        String accessToken = jwtTokenProvider.issueAccessToken(JwtClaimInfo.builder()
                .id(managerLoginRequest.id())
                .role("ROLE_MANAGER")
                .build());
        return jwtTokenProvider.accessTokenToBearerToken(accessToken);
    }

    public void updateManager(String bearerToken, ManagerUpdateRequest managerUpdateRequest) {
        String accessToken = jwtTokenProvider.bearerTokenToAccessToken(bearerToken);
        String id = jwtTokenProvider.parseAccessToken(accessToken).id();
        managerRepository.save(Manager.builder()
                .id(id)
                .password(managerUpdateRequest.password())
                .build());
    }

    public ManagerResponse findManager(String bearerToken) {
        String accessToken = jwtTokenProvider.bearerTokenToAccessToken(bearerToken);
        String id = jwtTokenProvider.parseAccessToken(accessToken).id();
        return managerRepository.findById(id)
                .map(managerMapper::toDto)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.NOT_FOUND_MANAGER));
    }

}
