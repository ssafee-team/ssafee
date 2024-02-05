package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OptionMapper {

    OptionResponse optionToOptionResponse(Option option);

    Option toEntity(OptionRequest optionRequest);

    @AfterMapping
    default void update(@MappingTarget Option option, OptionRequest optionRequest) {
        option.update(optionRequest);
    }

}
