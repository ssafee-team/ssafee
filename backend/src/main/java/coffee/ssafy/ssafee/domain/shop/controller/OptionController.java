package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.service.OptionService;
import coffee.ssafy.ssafee.domain.shop.service.ShopService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/shops/{shopId}/option-categories/{optionCategoryId}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;
    private final ShopService shopService;

    @PostMapping
    @Operation(summary = "옵션 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> create(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long optionCategoryId,
                                       @RequestBody OptionRequest optionRequest) {
        shopService.validatePrincipal(principal, shopId);
        Long optionId = optionService.create(shopId, optionCategoryId, optionRequest);
        URI location = URI.create("/v1/shops/" + shopId + "/option-categories/" + optionCategoryId + "/options/" + optionId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "옵션 목록 조회")
    public ResponseEntity<List<OptionResponse>> readAll(@PathVariable Long shopId,
                                                        @PathVariable Long optionCategoryId) {
        return ResponseEntity.ok().body(optionService.findAll(shopId, optionCategoryId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "옵션 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> update(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long optionCategoryId,
                                       @PathVariable Long id,
                                       @RequestBody OptionRequest optionRequest) {
        shopService.validatePrincipal(principal, shopId);
        optionService.update(id, shopId, optionCategoryId, optionRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "옵션 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> delete(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long optionCategoryId,
                                       @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        optionService.delete(id, shopId, optionCategoryId);
        return ResponseEntity.noContent().build();
    }

}
