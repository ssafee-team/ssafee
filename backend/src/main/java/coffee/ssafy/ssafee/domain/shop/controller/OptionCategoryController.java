package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.service.OptionCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/option-categories")
@RequiredArgsConstructor
public class OptionCategoryController {

    private final OptionCategoryService optionCategoryService;


    // 1. 옵션 카테고리 조회
    // shopId, optionCategoryId를 가지고
    // name, required, maxCount, deleted -> OptionCategory Entity 형식을 반환
    @GetMapping("/{oc_id}")
    @Operation(summary = "옵션 카테고리 조회")
    public ResponseEntity<List<OptionCategoryResponse>> getOptionCategory(
            @PathVariable("oc_id") Long optionCategoryId) {
        return ResponseEntity.ok().body(optionCategoryService.getOptionCategory(optionCategoryId));
    }


    // 2. 옵션 카테고리 생성

    // 3. 옵션 카테고리 수정

    // 4. 옵션 카테고리 삭제


}
