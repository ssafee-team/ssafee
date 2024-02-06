package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.response.PartyInfoForManagerResponse;
import coffee.ssafy.ssafee.domain.shop.service.ShopOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/orders")
@RequiredArgsConstructor
public class ShopOrderController {

    private final ShopOrderService shopOrderService;

    // 사장이 내 가게의 과거 주문내역을 다 뽑는거.
    @GetMapping("/")
    @Operation(summary = "매니저: 주문기록조회")
    public ResponseEntity<List<PartyInfoForManagerResponse>> getShopHistory(
            @PathVariable("shop_id") Long shopId) {
        return ResponseEntity.ok().body(shopOrderService.getShopHistories(shopId));
    }

//    @GetMapping("/{order_id}/confirm")
//    @Operation(summary = "매니저 : 주문기록상세조회")
//    public ResponseEntity<List<>>

//    @PostMapping("/{order_id}/confirm")
//    @Operation(summary = "매니저 : 주문확인")
//    public ResponseEntity<Void> orderConfirm(
//            @PathVariable("shop_id") Long shopId,
//            @PathVariable("order_id") Long orderId) {
//        shopOrderService.orderConfirm(shopId, orderId);
//        return ResponseEntity.noContent().build();
//    }

}
