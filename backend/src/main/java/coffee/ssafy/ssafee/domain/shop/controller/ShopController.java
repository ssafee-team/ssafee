package coffee.ssafy.ssafee.domain.shop.controller;

import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.ShopDetailResponse;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.service.ShopService;
import coffee.ssafy.ssafee.jwt.dto.JwtPrincipalInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    @Operation(summary = "가게 목록 조회")
    public ResponseEntity<List<ShopDetailResponse>> readAll() {
        return ResponseEntity.ok().body(shopService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 조회")
    public ResponseEntity<ShopDetailResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok().body(shopService.find(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "가게 수정", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<Void> update(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                       @PathVariable Long id,
                                       @RequestBody ShopRequest shopRequest) {
        shopService.validatePrincipal(principal, id);
        shopService.update(id, shopRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "가게 이미지 업로드", security = @SecurityRequirement(name = "access-token"))
    public ResponseEntity<String> updateImage(@AuthenticationPrincipal JwtPrincipalInfo principal,
                                              @PathVariable Long id,
                                              @RequestParam("image") MultipartFile file) {
        shopService.validatePrincipal(principal, id);
        if (file.isEmpty()) {
            throw new ShopException(ShopErrorCode.File_IS_EMPTY);
        }
        shopService.updateImage(id, file);
        return ResponseEntity.noContent().build();
    }

}
