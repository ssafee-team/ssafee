package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/option-categories/{oc_id}")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @GetMapping("/options")
    @Operation(summary = "옵션 조회")
    public ResponseEntity<List<OptionResponse>> getOptionsByCategory(
            @PathVariable("oc_id") Long optionCategoryId) {
        return ResponseEntity.ok().body(optionService.getOptionsByCategory(optionCategoryId));
    }

    @PostMapping("/option")
    @Operation(summary = "옵션 생성")
    public  ResponseEntity<Void> createOption(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("oc_id") Long optionCategoryId,
            @RequestBody OptionRequest optionRequest) {
        Long optionId = optionService.createOption(shopId, optionCategoryId, optionRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/option-categories/" + optionCategoryId + "/options/" + optionId);
        return ResponseEntity.created(location).build();
    }


}
