package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import coffee.ssafy.ssafee.domain.shop.service.MenuCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/menu-categories")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    // 1. 메뉴 카테고리 조회
    @GetMapping
    @Operation(summary = "메뉴 카테고리 조회")
    public ResponseEntity<List<String>> getMenuCategories(@PathVariable("shop_id") Long shopId) {
        return ResponseEntity.ok().body(menuCategoryService.findMenuCategories(shopId));
    }

    // 2. 메뉴 카테고리 생성
    @PostMapping
    @Operation(summary = "메뉴 카테고리 생성")
    public ResponseEntity<Void> createMenuCategory(
            @PathVariable("shop_id") Long shopId,
            @RequestBody MenuCategoryRequest menuCategoryRequest) {
        // Service 계층에 DTO를 전달하여 DB에 저장하고 결과를 반환
        // 생성된 MenuCategoryResponse 객체를 이용해 ResponseEntity 생성
        Long menuCategoryId = menuCategoryService.createMenuCategories(shopId, menuCategoryRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/menu-categories/" + menuCategoryId);
        return ResponseEntity.created(location).build();
    }

    // 3. 메뉴 카테고리 수정
    @PatchMapping("/{mc_id}")
    @Operation(summary = "메뉴 카테고리 수정")
    public ResponseEntity<Void> updateMenuCategory(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("mc_id") Long menuCategoryId,
            @RequestBody MenuCategoryRequest menuCategoryRequest) {
        menuCategoryService.updateMenuCategory(menuCategoryId, menuCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    // 4. 메뉴 카테고리 삭제

}
