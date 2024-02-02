package coffee.ssafy.ssafee.domain.shop.repository;

import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OptionCategoryRepository extends JpaRepository<OptionCategory, Long> {

    List<OptionCategory> findAllById(Long optionCategoryId);

    void deleteById(Long optionCategoryId);
}
