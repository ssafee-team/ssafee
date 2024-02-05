package coffee.ssafy.ssafee.domain.manager.controller;

import coffee.ssafy.ssafee.domain.manager.dto.request.ManagerLoginRequest;
import coffee.ssafy.ssafee.domain.manager.dto.request.ManagerUpdateRequest;
import coffee.ssafy.ssafee.domain.manager.dto.response.ManagerResponse;
import coffee.ssafy.ssafee.domain.manager.service.ManagerService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/login")
    @Operation(summary = "매니저 로그인")
    public ResponseEntity<Void> login(@RequestBody ManagerLoginRequest managerLoginRequest) {
        String accessToken = managerService.login(managerLoginRequest);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).build();
    }

    @PutMapping("/me")
    @Operation(summary = "매니저 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateManager(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                              @RequestBody ManagerUpdateRequest managerUpdateRequest) {
        managerService.updateManager(principal.id(), managerUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "매니저 조회", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<ManagerResponse> getManager(@AuthenticationPrincipal JwtPrincipalInfo principal) {
        return ResponseEntity.ok().body(managerService.findManager(principal.id()));
    }

}
