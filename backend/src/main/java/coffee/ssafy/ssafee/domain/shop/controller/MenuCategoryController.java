package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.service.MenuCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop/{shop_id}")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    // 1. 메뉴 카테고리 조회

    @GetMapping("/menu-categories")
    @Operation(summary = "메뉴 카테고리 조회")
    public ResponseEntity<List<String>> getMenuCategories(@PathVariable Long shopId) {
        return ResponseEntity.ok().body(menuCategoryService.findMenuCategories(shopId));
    }

    // 2. 메뉴 카테고리 생성
    @PostMapping("/menu-categories/")
    @Operation(summary = "메뉴 카테고리 생성 (미완성)")
    public ResponseEntity<MenuCategoryResponse> createMenuCategory(
            @PathVariable ("shop_id") Long shopId,
            @RequestBody MenuCategoryRequest menuCategoryRequest) {
        // Service 계층에 DTO를 전달하여 DB에 저장하고 결과를 반환
        // 생성된 MenuCategoryResponse 객체를 이용해 ResponseEntity 생성
        MenuCategoryResponse menuCategoryResponse = menuCategoryService.createMenuCategories(menuCategoryRequest);
        return ResponseEntity.ok(menuCategoryResponse);
    }

    // 3. 메뉴 카테고리 수정

    // 4. 메뉴 카테고리 삭제

}
