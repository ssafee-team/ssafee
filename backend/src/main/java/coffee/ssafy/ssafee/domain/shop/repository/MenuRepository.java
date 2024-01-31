package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByShopIdAndMenuCategoryId(Long shopId, Long menuCategoryId);

}
