package coffee.ssafy.ssafee.domain.party.controller;

import coffee.ssafy.ssafee.domain.party.dto.request.OrderMenuRequest;
import coffee.ssafy.ssafee.domain.party.dto.response.OrderMenuResponse;
import coffee.ssafy.ssafee.domain.party.service.OrderMenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parties/{access_code}/order-menus")
@RequiredArgsConstructor
public class OrderMenuController {

    private final OrderMenuService orderMenuService;

    @PostMapping
    @Operation(summary = "주문 넣기")
    public ResponseEntity<Void> createOrder(@PathVariable("access_code") String accessCode, @RequestBody OrderMenuRequest orderMenuRequest) {
        Long id = orderMenuService.createOrderMenu(accessCode, orderMenuRequest);
        URI location = URI.create("/api/v1/parties/" + accessCode + "/order-menus/" + id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "주문 메뉴 목록")
    public ResponseEntity<List<OrderMenuResponse>> getOrderMenus(@PathVariable("access_code") String accessCode) {
        return ResponseEntity.ok().body(orderMenuService.findOrderMenusByAccessCode(accessCode));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "주문 메뉴 삭제")
    public ResponseEntity<Void> deleteOrderMenu(@PathVariable("access_code") String accessCode, @PathVariable Long id) {
        orderMenuService.deleteOrderMenuByAccessCodeAndId(accessCode, id);
        return ResponseEntity.noContent().build();
    }

}
