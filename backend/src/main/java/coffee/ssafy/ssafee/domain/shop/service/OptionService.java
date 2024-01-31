package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.shop.mapper.OptionMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final EntityManager entityManager;
    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;

    public List<OptionResponse> getOptionsByCategory(Long optionCategoryId) {
        List<Option> options = optionRepository.findByOptionCategoryId(optionCategoryId);
        return options.stream()
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
}
