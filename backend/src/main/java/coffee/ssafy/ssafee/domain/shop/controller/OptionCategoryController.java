package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.service.OptionCategoryService;
import coffee.ssafy.ssafee.domain.user.service.ManagerService;
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
@RequestMapping("/api/v1/shops/{shop_id}/option-categories")
@RequiredArgsConstructor
public class OptionCategoryController {

    private final OptionCategoryService optionCategoryService;
    private final ManagerService managerService;

    @GetMapping
    @Operation(summary = "옵션 카테고리 목록 조회")
    public ResponseEntity<List<OptionCategoryDetailResponse>> getOptionCategories(@PathVariable("shop_id") Long shopId,
                                                                                  @RequestParam(value = "menu_id", required = false) Long menuId) {
        return ResponseEntity.ok().body(optionCategoryService.getOptionCategories(shopId, menuId));
    }

    @PostMapping
    @Operation(summary = "옵션 카테고리 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> createOptionCategory(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                     @PathVariable("shop_id") Long shopId,
                                                     @RequestBody OptionCategoryRequest optionCategoryRequest) {
        managerService.validate(principal, shopId);
        Long optionCategoryId = optionCategoryService.createOptionCategory(shopId, optionCategoryRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/option-categories/" + optionCategoryId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{oc_id}")
    @Operation(summary = "옵션 카테고리 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateOptionCategory(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                     @PathVariable("shop_id") Long shopId,
                                                     @PathVariable("oc_id") Long optionCategoryId,
                                                     @RequestBody OptionCategoryRequest optionCategoryRequest) {
        managerService.validate(principal, shopId);
        optionCategoryService.updateOptionCategory(shopId, optionCategoryId, optionCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{oc_id}")
    @Operation(summary = "옵션 카테고리 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> deleteOptionCategory(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                     @PathVariable("shop_id") Long shopId,
                                                     @PathVariable("oc_id") Long optionCategoryId) {
        managerService.validate(principal, shopId);
        optionCategoryService.deleteOptionCategory(shopId, optionCategoryId);
        return ResponseEntity.ok().build();
    }

}
