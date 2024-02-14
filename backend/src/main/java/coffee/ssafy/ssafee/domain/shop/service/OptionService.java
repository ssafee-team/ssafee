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

@Service
@Transactional
@RequiredArgsConstructor
public class OptionService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptionsByCategory(long shopId, long optionCategoryId) {
        return optionRepository.findAllByShopIdAndOptionCategoryId(shopId, optionCategoryId).stream()
                .map(optionMapper::optionToOptionResponse)
                .toList();
    }

    public long createOption(long shopId, long optionCategoryId, OptionRequest optionRequest) {
        Option option = Option.builder()
                .name(optionRequest.name())
                .price(optionRequest.price())
                .optionCategory(entityManager.getReference(OptionCategory.class, optionCategoryId))
                .shop(entityManager.getReference(Shop.class, shopId))
                .build();
        optionRepository.save(option);
        return option.getId();
    }

    public void updateOption(long id, long shopId, long optionCategoryId, OptionRequest optionRequest) {
        optionRepository.findByIdAndShopIdAndOptionCategoryId(id, shopId, optionCategoryId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NOT_EXISTS_OPTION_CATEGORY))
                .update(optionRequest);
    }

    public void deleteOption(long shopId, long optionCategoryId, long id) {
        optionRepository.deleteByIdAndShopIdAndOptionCategoryId(id, shopId, optionCategoryId);
    }

}
