package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    List<MenuCategory> findAllByShopId(Long shopId);

    Optional<MenuCategory> findByShopIdAndId(Long shopId, Long id);

    void deleteByShopIdAndId(Long shopId, Long id);

}
