package coffee.ssafy.ssafee.domain.user.controller;

import coffee.ssafy.ssafee.domain.user.dto.request.ManagerLoginRequest;
import coffee.ssafy.ssafee.domain.user.dto.request.ManagerUpdateRequest;
import coffee.ssafy.ssafee.domain.user.dto.response.ManagerResponse;
import coffee.ssafy.ssafee.domain.user.service.ManagerService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/login")
    @Operation(summary = "매니저 로그인")
    public ResponseEntity<Void> login(@RequestBody ManagerLoginRequest managerLoginRequest) {
        String accessToken = managerService.login(managerLoginRequest);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).build();
    }

    @GetMapping("/me")
    @Operation(summary = "매니저 조회", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<ManagerResponse> read(@AuthenticationPrincipal JwtPrincipalInfo principal) {
        return ResponseEntity.ok().body(managerService.find(principal.id()));
    }

    @PutMapping("/me")
    @Operation(summary = "매니저 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> update(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @RequestBody ManagerUpdateRequest managerUpdateRequest) {
        managerService.update(principal.id(), managerUpdateRequest);
        return ResponseEntity.noContent().build();
    }

}
