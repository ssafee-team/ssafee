package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops/{shop_id}/option-categories/{oc_id}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @GetMapping
    @Operation(summary = "옵션 조회")
    public ResponseEntity<List<OptionResponse>> getOptionsByCategory(
            @PathVariable("oc_id") Long optionCategoryId) {
        return ResponseEntity.ok().body(optionService.getOptionsByCategory(optionCategoryId));
    }

    @PostMapping
    @Operation(summary = "옵션 생성", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> createOption(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("oc_id") Long optionCategoryId,
            @RequestBody OptionRequest optionRequest) {
        Long optionId = optionService.createOption(shopId, optionCategoryId, optionRequest);
        URI location = URI.create("/api/v1/shops/" + shopId + "/option-categories/" + optionCategoryId + "/options/" + optionId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{option_id}")
    @Operation(summary = "옵션 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> updateOption(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("oc_id") Long optionCategoryId,
            @PathVariable("option_id") Long optionId,
            @RequestBody OptionRequest optionRequest) {
        optionService.updateOption(optionId, optionRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{option_id}")
    @Operation(summary = "옵션 삭제", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> deleteOption(
            @PathVariable("shop_id") Long shopId,
            @PathVariable("oc_id") Long optionCategoryId,
            @PathVariable("option_id") Long optionId) {
        optionService.deleteOption(optionCategoryId);
        return ResponseEntity.ok().build();
    }

}
