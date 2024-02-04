package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopResponse;
import coffee.ssafy.ssafee.domain.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<List<ShopResponse>> getShops() {
        return ResponseEntity.ok().body(shopService.findShops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDetailResponse> getShop(@PathVariable Long id) {
        return ResponseEntity.ok().body(shopService.findShopById(id));
    }

    @PutMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateShop(@PathVariable Long id, @RequestBody ShopRequest shopRequest) {
        shopService.updateShop(id, shopRequest);
        return ResponseEntity.noContent().build();
    }

}
