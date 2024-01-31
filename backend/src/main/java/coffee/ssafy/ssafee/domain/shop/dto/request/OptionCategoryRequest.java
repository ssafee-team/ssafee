package coffee.ssafy.ssafee.domain.shop.dto.request;

public record OptionCategoryRequest (
    String name,
    Boolean required,
    Integer maxCount
) {

}
