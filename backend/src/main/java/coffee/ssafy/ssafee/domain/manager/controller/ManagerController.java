package coffee.ssafy.ssafee.domain.manager.controller;

import coffee.ssafy.ssafee.domain.manager.dto.request.ManagerLoginRequest;
import coffee.ssafy.ssafee.domain.manager.dto.request.ManagerUpdateRequest;
import coffee.ssafy.ssafee.domain.manager.dto.response.ManagerResponse;
import coffee.ssafy.ssafee.domain.manager.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody ManagerLoginRequest managerLoginRequest) {
        String bearerToken = managerService.login(managerLoginRequest);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, bearerToken).build();
    }

    @PatchMapping("/me")
    @Operation(security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateManager(@RequestHeader(HttpHeaders.AUTHORIZATION) @Parameter(hidden = true) String bearerToken,
                                              @RequestBody ManagerUpdateRequest managerUpdateRequest) {
        managerService.updateManager(bearerToken, managerUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<ManagerResponse> getManager(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        return ResponseEntity.ok().body(managerService.findManager(bearerToken));
    }

}
