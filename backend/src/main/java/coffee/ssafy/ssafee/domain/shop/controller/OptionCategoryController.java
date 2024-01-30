package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.service.OptionCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shops/{id}/menu-categories/{id}/menus/{id}")
public class OptionCategoryController {

//    private final OptionCategoryService optionCategoryService;



    // 1. 옵션 카테고리 조회
    // shop_id가 칼럼에 없음 그래서 아래와 같은 순서여야함
    // 메뉴 카테고리 조회 -> 메뉴 조회 -> 메뉴-옵션 카테고리 조회 -> 옵션 카테고리 조회

    // 2. 옵션 카테고리 생성

    // 3. 옵션 카테고리 수정

    // 4. 옵션 카테고리 삭제


}
