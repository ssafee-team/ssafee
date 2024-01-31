package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenusById;
import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop/{shop_id}")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // /api/v1/shops/{id}/menu-categories/{id}/menus

    // 1. (카테고리별) 메뉴 조회
    // shopId 와 menuCategoryId를 가지고 해당 menuCategoryId에 해당하는 메뉴들을 출력
    @GetMapping("/menu-categories/{mc_id}/menus")
    @Operation(summary = "메뉴 조회")
    public ResponseEntity<List<MenusById>> getMenusByCategory(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("mc_id")  Long menuCategoryId) {
        return ResponseEntity.ok().body(menuService.getMenusByCategory(shopId, menuCategoryId));
    }



    // 2. 메뉴 생성

    // 3. 메뉴 수정

    // 4. 메뉴 삭제

}
