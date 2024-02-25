package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryDetailResponse;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.OptionCategoryMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionCategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionCategoryService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final OptionCategoryRepository optionCategoryRepository;
    private final OptionCategoryMapper optionCategoryMapper;

    public Long create(Long shopId, OptionCategoryRequest optionCategoryRequest) {
        OptionCategory optionCategory = OptionCategory.builder()
                .name(optionCategoryRequest.name())
                .required(optionCategoryRequest.required())
                .maxCount(optionCategoryRequest.maxCount())
                .shop(entityManager.getReference(Shop.class, shopId))
                .build();
        optionCategoryRepository.save(optionCategory);
        return optionCategory.getId();
    }

    @Transactional(readOnly = true)
    public List<OptionCategoryDetailResponse> findAll(Long shopId, Long menuId) {
        return Optional.ofNullable(menuId)
                .map(id -> optionCategoryRepository.findAllByShopIdAndMenuId(shopId, id))
                .orElseGet(() -> optionCategoryRepository.findAllByShopId(shopId)).stream()
                .map(optionCategoryMapper::toDetailResponse)
                .toList();
    }

    public void update(Long shopId, Long optionCategoryId, OptionCategoryRequest optionCategoryRequest) {
        optionCategoryRepository.findByShopIdAndId(shopId, optionCategoryId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_OPTION_CATEGORY))
                .update(optionCategoryRequest);
    }

    public void delete(Long shopId, Long optionCategoryId) {
        optionCategoryRepository.deleteByShopIdAndId(shopId, optionCategoryId);
    }

}
