package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionCategoryRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.mapper.OptionCategoryMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionCategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionCategoryService {

    private final EntityManager entityManager;
    private final OptionCategoryMapper optionCategoryMapper;
    private final OptionCategoryRepository optionCategoryRepository;

    public List<OptionCategoryResponse> getOptionCategory(Long optionCategoryId) {
        List<OptionCategory> optionCategories = optionCategoryRepository.findAllById(optionCategoryId);
        return optionCategories.stream()
                .map(optionCategoryMapper::optionCategoryToOptionCategoryDto)
                .toList();
    }

    public Long createOptionCategory(Long shopId, OptionCategoryRequest optionCategoryRequest) {
        OptionCategory optionCategory = optionCategoryMapper.toEntity(optionCategoryRequest);
        optionCategory.setShop(entityManager.getReference(Shop.class, shopId));
        optionCategoryRepository.save(optionCategory);
        return optionCategory.getId();
    }

    public void updateOptionCategory(Long optionCategoryId, OptionCategoryRequest optionCategoryRequest) {
        optionCategoryRepository.findById(optionCategoryId).ifPresent(optionCategory -> {
            optionCategoryMapper.updateFromDto(optionCategoryRequest, optionCategory);
            optionCategoryRepository.save(optionCategory);
        });
    }

}
