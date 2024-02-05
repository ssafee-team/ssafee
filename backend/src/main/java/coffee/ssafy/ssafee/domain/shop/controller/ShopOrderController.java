package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.manager.service.ManagerService;
import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.service.ShopService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{id}/orders")
@RequiredArgsConstructor
public class ShopOrderController {

    private final ShopService shopService;
    private final ManagerService managerService;

    // 사장이 내 가게의 과거 주문내역을 다 뽑는거.
    @GetMapping("/")
    @Operation(summary = "매니저: 주문기록조회")
    public ResponseEntity<>


}
