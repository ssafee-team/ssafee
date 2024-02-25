package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.service.MenuCategoryService;
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
@RequestMapping("/v1/shops/{shopId}/menu-categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;
    private final ShopService shopService;

    @PostMapping
    @Operation(summary = "메뉴 카테고리 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> create(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @RequestBody MenuCategoryRequest menuCategoryRequest) {
        shopService.validatePrincipal(principal, shopId);
        Long menuCategoryId = menuCategoryService.create(shopId, menuCategoryRequest);
        URI location = URI.create("/v1/shops/" + shopId + "/menu-categories/" + menuCategoryId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "메뉴 카테고리 목록 조회")
    public ResponseEntity<List<MenuCategoryDetailResponse>> readAll(@PathVariable Long shopId) {
        return ResponseEntity.ok().body(menuCategoryService.findAll(shopId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "메뉴 카테고리 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> update(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long id,
                                       @RequestBody MenuCategoryRequest menuCategoryRequest) {
        shopService.validatePrincipal(principal, shopId);
        menuCategoryService.update(shopId, id, menuCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "메뉴 카테고리 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> delete(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        menuCategoryService.delete(shopId, id);
        return ResponseEntity.noContent().build();
    }

}
