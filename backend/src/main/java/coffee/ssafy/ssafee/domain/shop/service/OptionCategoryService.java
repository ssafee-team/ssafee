package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.dto.response.OptionCategoryResponse;
import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import coffee.ssafy.ssafee.domain.shop.mapper.OptionCategoryMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionCategoryService {

    private final OptionCategoryMapper optionCategoryMapper;
    private final OptionCategoryRepository optionCategoryRepository;

    public List<OptionCategoryResponse> getOptionCategory(Long optionCategoryId) {
        List<OptionCategory> optionCategories = optionCategoryRepository.findAllById(optionCategoryId);
        return optionCategories.stream()
                .map(optionCategoryMapper::optionCategoryToOptionCategoryDto)
                .toList();
    }
}
