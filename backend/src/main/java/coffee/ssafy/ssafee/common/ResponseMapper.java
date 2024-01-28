package coffee.ssafy.ssafee.common;

import java.util.List;

public interface ResponseMapper<D, E> {
    D toDto(E e);

    List<D> toDtoList(List<E> e);

}
