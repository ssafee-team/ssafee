package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.manager.service.ManagerService;
import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.service.MenuCategoryService;
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
@RequestMapping("/api/v1/shops/{shop_id}/menu-categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;
    private final ManagerService managerService;

    @GetMapping
    @Operation(summary = "메뉴 카테고리 목록 조회")
    public ResponseEntity<List<MenuCategoryDetailResponse>> getMenuCategories(@PathVariable("shop_id") Long shopId) {
        return ResponseEntity.ok().body(menuCategoryService.findMenuCategories(shopId));
    }

    @PostMapping
    @Operation(summary = "메뉴 카테고리 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> createMenuCategory(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                   @PathVariable("shop_id") Long shopId,
                                                   @RequestBody MenuCategoryRequest menuCategoryRequest) {
        managerService.validate(principal, shopId);
        Long menuCategoryId = menuCategoryService.createMenuCategory(shopId, menuCategoryRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/menu-categories/" + menuCategoryId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{mc_id}")
    @Operation(summary = "메뉴 카테고리 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateMenuCategory(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                   @PathVariable("shop_id") Long shopId,
                                                   @PathVariable("mc_id") Long menuCategoryId,
                                                   @RequestBody MenuCategoryRequest menuCategoryRequest) {
        managerService.validate(principal, shopId);
        menuCategoryService.updateMenuCategory(shopId, menuCategoryId, menuCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{mc_id}")
    @Operation(summary = "메뉴 카테고리 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> deleteMenuCategory(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                   @PathVariable("shop_id") Long shopId,
                                                   @PathVariable("mc_id") Long menuCategoryId) {
        managerService.validate(principal, shopId);
        menuCategoryService.deleteMenuCategory(shopId, menuCategoryId);
        return ResponseEntity.ok().build();
    }

}
