package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.ShopDetailDto;
import coffee.ssafy.ssafee.domain.shop.dto.ShopDto;
import coffee.ssafy.ssafee.domain.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<List<ShopDto>> getShops() {
        return ResponseEntity.ok().body(shopService.findAllShop());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDetailDto> getShop(@PathVariable Long id) {
        return ResponseEntity.ok().body(shopService.findShopById(id));
    }

}
