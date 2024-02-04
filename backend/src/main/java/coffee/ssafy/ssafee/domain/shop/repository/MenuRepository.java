package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByShopIdAndMenuCategoryId(Long shopId, Long menuCategoryId);

    Optional<Menu> findByShopIdAndId(Long shopId, Long Id);

    void deleteByShopIdAndId(Long shopId, Long id);

}
