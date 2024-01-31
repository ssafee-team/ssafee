package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import coffee.ssafy.ssafee.domain.shop.mapper.OptionMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;

    public List<OptionResponse> getOptionsByCategory(Long optionCategoryId) {
        List<Option> options = optionRepository.findByOptionCategoryId(optionCategoryId);
        return options.stream()
                .map(optionMapper::optionToOptionResponse)
                .collect(Collectors.toList());
    }
}
