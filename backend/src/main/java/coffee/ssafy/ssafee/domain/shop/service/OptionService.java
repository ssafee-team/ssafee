package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.mapper.OptionMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final EntityManager entityManager;
    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;

    @Transactional
    public List<OptionResponse> getOptionsByCategory(Long optionCategoryId) {
        List<Option> options = optionRepository.findByOptionCategoryId(optionCategoryId);
        return options.stream()
                .map(optionMapper::optionToOptionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createOption(Long shopId, Long optionCategoryId, OptionRequest optionRequest) {
        Option option = optionMapper.toEntity(optionRequest);
        option.setOptionCategory(entityManager.getReference(OptionCategory.class, optionCategoryId));
        option.setShop(entityManager.getReference(Shop.class, shopId));
        optionRepository.save(option);
        return option.getId();
    }

    @Transactional
    public void updateOption(Long optionId, OptionRequest optionRequest) {
        optionRepository.findById(optionId).ifPresent(option -> {
            optionMapper.updateFromDto(optionRequest, option);
            optionRepository.save(option);
        });
    }
}
