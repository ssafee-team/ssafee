package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.exception.ShopErrorCode;
import coffee.ssafy.ssafee.domain.shop.exception.ShopException;
import coffee.ssafy.ssafee.domain.shop.mapper.OptionMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;

    public List<OptionResponse> getOptionsByCategory(Long shopId, Long optionCategoryId) {
        return optionRepository.findAllByShopIdAndOptionCategoryId(shopId, optionCategoryId).stream()
                .map(optionMapper::optionToOptionResponse)
                .collect(Collectors.toList());
    }

    public Long createOption(Long shopId, Long optionCategoryId, OptionRequest optionRequest) {
        Option option = optionMapper.toEntity(optionRequest);
        option.setOptionCategory(entityManager.getReference(OptionCategory.class, optionCategoryId));
        option.setShop(entityManager.getReference(Shop.class, shopId));
        optionRepository.save(option);
        return option.getId();
    }

    public void updateOption(Long shopId, Long optionCategoryId, Long optionId, OptionRequest optionRequest) {
        optionRepository.findByShopIdAndId(shopId, optionId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_OPTION_CATEGORY))
                .update(optionRequest);
    }

    public void deleteOption(Long shopId, Long optionCategoryId, Long optionId) {
        optionRepository.deleteByShopIdAndId(shopId, optionId);
    }

}
