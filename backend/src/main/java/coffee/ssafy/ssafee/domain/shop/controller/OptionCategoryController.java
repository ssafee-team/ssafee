package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.service.OptionCategoryService;
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
@RequestMapping("/v1/shops/{shopId}/option-categories")
@RequiredArgsConstructor
public class OptionCategoryController {

    private final OptionCategoryService optionCategoryService;
    private final ShopService shopService;

    @PostMapping
    @Operation(summary = "옵션 카테고리 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> create(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @RequestBody OptionCategoryRequest optionCategoryRequest) {
        shopService.validatePrincipal(principal, shopId);
        Long optionCategoryId = optionCategoryService.create(shopId, optionCategoryRequest);
        URI location = URI.create("/v1/shops/" + shopId + "/option-categories/" + optionCategoryId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "옵션 카테고리 목록 조회")
    public ResponseEntity<List<OptionCategoryDetailResponse>> readAll(@PathVariable Long shopId,
                                                                      @RequestParam(required = false) Long menuId) {
        return ResponseEntity.ok().body(optionCategoryService.findAll(shopId, menuId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "옵션 카테고리 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> update(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long id,
                                       @RequestBody OptionCategoryRequest optionCategoryRequest) {
        shopService.validatePrincipal(principal, shopId);
        optionCategoryService.update(shopId, id, optionCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "옵션 카테고리 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> delete(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        optionCategoryService.delete(shopId, id);
        return ResponseEntity.noContent().build();
    }

}
