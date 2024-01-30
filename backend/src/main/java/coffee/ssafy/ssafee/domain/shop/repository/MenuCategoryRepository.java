package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.shop.dto.response.MenuCategoryName;
import coffee.ssafy.ssafee.domain.shop.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    List<MenuCategory> findAllByShopId(Long shopId);

}
