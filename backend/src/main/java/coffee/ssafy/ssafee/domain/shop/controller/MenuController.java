package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.manager.service.ManagerService;
import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.service.MenuService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/menu-categories/{mc_id}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final ManagerService managerService;

    @GetMapping
    @Operation(summary = "메뉴 목록 조회")
    public ResponseEntity<List<MenuResponse>> getMenusByCategory(@PathVariable("shop_id") Long shopId,
                                                                 @PathVariable("mc_id") Long menuCategoryId) {
        return ResponseEntity.ok().body(menuService.getMenusByCategory(shopId, menuCategoryId));
    }

    @PostMapping
    @Operation(summary = "메뉴 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> createMenu(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                           @PathVariable("shop_id") Long shopId,
                                           @PathVariable("mc_id") Long menuCategoryId,
                                           @RequestBody MenuRequest menuRequest) {
        managerService.validate(principal, shopId);
        Long menuId = menuService.createMenu(shopId, menuCategoryId, menuRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/menu-categories/" + menuCategoryId + "/menus/" + menuId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{menu_id}")
    @Operation(summary = "메뉴 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateMenu(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                           @PathVariable("shop_id") Long shopId,
                                           @PathVariable("mc_id") Long menuCategoryId,
                                           @PathVariable("menu_id") Long menuId,
                                           @RequestBody MenuRequest menuRequest) {
        managerService.validate(principal, shopId);
        menuService.updateMenu(shopId, menuCategoryId, menuId, menuRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{menu_id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "메뉴 이미지 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateMenuImage(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                                @PathVariable("shop_id") Long shopId,
                                                @PathVariable("mc_id") Long menuCategoryId,
                                                @PathVariable("menu_id") Long menuId,
                                                @RequestParam("image") MultipartFile file) {
        managerService.validate(principal, shopId);
        if (file.isEmpty()) {
            throw new ShopException(ShopErrorCode.File_IS_EMPTY);
        }
        menuService.updateMenuImage(shopId, menuCategoryId, menuId, file);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{menu_id}")
    @Operation(summary = "메뉴 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> deleteMenu(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                           @PathVariable("shop_id") Long shopId,
                                           @PathVariable("mc_id") Long menuCategoryId,
                                           @PathVariable("menu_id") Long menuId) {
        managerService.validate(principal, shopId);
        menuService.deleteMenu(shopId, menuCategoryId, menuId);
        return ResponseEntity.ok().build();
    }

}
