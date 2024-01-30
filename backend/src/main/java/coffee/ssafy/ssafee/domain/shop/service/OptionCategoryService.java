package coffee.ssafy.ssafee.domain.shop.service;

import coffee.ssafy.ssafee.domain.shop.mapper.OptionCategoryMapper;
import coffee.ssafy.ssafee.domain.shop.repository.OptionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionCategoryService {

    private final OptionCategoryMapper optionCategoryMapper;
    private final OptionCategoryRepository optionCategoryRepository;


}
