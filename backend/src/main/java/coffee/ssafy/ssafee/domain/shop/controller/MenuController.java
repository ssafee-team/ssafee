package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // /api/v1/shops/{id}/menu-categories/{id}/menus

    // 1. (카테고리별) 메뉴 조회
    // shopId 와 menuCategoryId를 가지고 해당 menuCategoryId에 해당하는 메뉴들을 출력
    @GetMapping("/menu-categories/{mc_id}/menus")
    @Operation(summary = "메뉴 조회")
    public ResponseEntity<List<MenuResponse>> getMenusByCategory(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("mc_id") Long menuCategoryId) {
        return ResponseEntity.ok().body(menuService.getMenusByCategory(shopId, menuCategoryId));
    }


    // 2. 메뉴 생성
    @PostMapping("/menu-categories/{mc_id}/menus")
    @Operation(summary = "메뉴 생성")
    public ResponseEntity<Void> createMenu(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("mc_id") Long menuCategoryId,
            @RequestBody MenuRequest menuRequest) {
        Long menuId = menuService.createMenu(shopId, menuCategoryId, menuRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/menu-categories/" + menuCategoryId + "/menus/" + menuId);
        return ResponseEntity.created(location).build();
    }

    // 3. 메뉴 수정
    @PatchMapping("/menu-categories/{mc_id}/menus/{menu_id}")
    @Operation(summary = "메뉴 수정")
    public ResponseEntity<Void> updateMenu(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("mc_id") Long menuCategoryId,
            @PathVariable("menu_id") Long menuId,
            @RequestBody MenuRequest menuRequest) {
        menuService.updateMenu(menuId, menuRequest);
        return ResponseEntity.noContent().build();
    }

    // 4. 메뉴 삭제

}
