package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OptionCategoryRepository extends JpaRepository<OptionCategory, Long> {

    List<OptionCategory> findAllByShopId(Long shopId);

    @Query("select oc from OptionCategory oc join oc.menus m where oc.shop.id = :shopId and m.id = :menuId")
    List<OptionCategory> findAllByShopIdAndMenuId(Long shopId, Long menuId);

    Optional<OptionCategory> findByShopIdAndId(Long shopId, Long id);

    void deleteByShopIdAndId(Long shopId, Long id);
}
