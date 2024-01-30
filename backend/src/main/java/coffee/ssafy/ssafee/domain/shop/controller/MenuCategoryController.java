package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.service.MenuCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop/{shopId}")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    // 1. 메뉴 카테고리 조회
    @GetMapping("/menu-categories")
    public ResponseEntity<List<String>> getMenuCategories(@PathVariable Long shopId) {
        return ResponseEntity.ok().body(menuCategoryService.findMenuCategories(shopId));
    }

    // 2. 메뉴 카테고리 생성

    // 3. 메뉴 카테고리 수정

    // 4. 메뉴 카테고리 삭제

}
