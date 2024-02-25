package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuDetailResponse;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.service.MenuService;
import coffee.ssafy.ssafee.domain.shop.service.ShopService;
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
@RequestMapping("/v1/shops/{shopId}/menu-categories/{menuCategoryId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final ShopService shopService;

    @PostMapping
    @Operation(summary = "메뉴 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> create(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long menuCategoryId,
                                       @RequestBody MenuRequest menuRequest) {
        shopService.validatePrincipal(principal, shopId);
        Long menuId = menuService.create(shopId, menuCategoryId, menuRequest);
        URI location = URI.create("/v1/shops/" + shopId + "/menu-categories/" + menuCategoryId + "/menus/" + menuId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "메뉴 목록 조회")
    public ResponseEntity<List<MenuDetailResponse>> readAll(@PathVariable Long shopId,
                                                            @PathVariable Long menuCategoryId) {
        return ResponseEntity.ok().body(menuService.findAll(shopId, menuCategoryId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "메뉴 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> update(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long menuCategoryId,
                                       @PathVariable Long id,
                                       @RequestBody MenuRequest menuRequest) {
        shopService.validatePrincipal(principal, shopId);
        menuService.update(shopId, menuCategoryId, id, menuRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "메뉴 이미지 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateImage(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                            @PathVariable Long shopId,
                                            @PathVariable Long menuCategoryId,
                                            @PathVariable Long id,
                                            @RequestParam("image") MultipartFile file) {
        shopService.validatePrincipal(principal, shopId);
        if (file.isEmpty()) {
            throw new ShopException(ShopErrorCode.File_IS_EMPTY);
        }
        menuService.updateImage(shopId, menuCategoryId, id, file);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "메뉴 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> delete(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long shopId,
                                       @PathVariable Long menuCategoryId,
                                       @PathVariable Long id) {
        shopService.validatePrincipal(principal, shopId);
        menuService.delete(shopId, menuCategoryId, id);
        return ResponseEntity.noContent().build();
    }

}
