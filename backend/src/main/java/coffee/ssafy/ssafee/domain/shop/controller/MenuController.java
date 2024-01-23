package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.MenuDetailDto;
import coffee.ssafy.ssafee.domain.shop.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<MenuDetailDto> getMenu(Long id) {
        return ResponseEntity.ok().body(menuService.findMenuById(id));
    }

}
