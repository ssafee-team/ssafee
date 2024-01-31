package coffee.ssafy.ssafee.domain.shop.dto.response;

public record MenusById(
        Long shopId,
        Long menuCategoryId,
        String name,
        String image,
        Integer price
) {

}
