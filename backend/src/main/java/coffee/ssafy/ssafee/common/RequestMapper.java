package coffee.ssafy.ssafee.common;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface RequestMapper<D, E> {

    @Mapping(target = "id", ignore = true)
    E toEntity(D e);

    List<E> toEntityList(List<D> e);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(D dto, @MappingTarget E entity);

}
