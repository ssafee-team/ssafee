package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OptionCategoryRepository extends JpaRepository<OptionCategory, Long> {

    List<OptionCategory> findAllByShopId(Long shopId);

    @Query("SELECT oc FROM Menu m JOIN m.optionCategories oc WHERE m.shop.id = :shopId AND m.id = :menuId")
    List<OptionCategory> findAllByShopIdAndMenuId(Long shopId, Long menuId);

    Optional<OptionCategory> findByShopIdAndId(Long shopId, Long id);

    void deleteByShopIdAndId(Long shopId, Long id);

}
