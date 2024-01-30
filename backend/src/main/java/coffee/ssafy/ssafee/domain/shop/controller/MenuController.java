package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuResponse;
import coffee.ssafy.ssafee.domain.shop.service.MenuService;
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

    // 1. (카테고리별) 메뉴 조회
//    @GetMapping("/menu-categories/{mc_id}/menus")
//    public ResponseEntity<List<MenuResponse>> getMenusByCategory(@PathVariable("mc_id") Long menuCategoryId){
//        return ResponseEntity.ok().body(menuService.findMenusByShopId)
//    }

    // 2. 메뉴 생성

    // 3. 메뉴 수정

    // 4. 메뉴 삭제

}
