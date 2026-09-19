package J2EE.com.example.project.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.CategoryRequest;
import J2EE.com.example.project.dto.response.CategoryResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.entity.Category;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.mapper.CategoryMapper;
import J2EE.com.example.project.repository.CategoryRepository;
import J2EE.com.example.project.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private static final int ACTIVE_STATUS = 0;

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public PageResponse<CategoryResponse> getAll(Pageable pageable) {
        var page = categoryRepository.findAllByStatus(ACTIVE_STATUS, pageable).map(categoryMapper::toResponse);
        return PageResponse.<CategoryResponse>builder()
                .content(page.getContent())
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public CategoryResponse getById(Integer id) {
        return categoryMapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public CategoryResponse update(Integer id, CategoryRequest request) {
        Category entity = findById(id);
        categoryMapper.updateEntity(request, entity);
        return categoryMapper.toResponse(categoryRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Category entity = findById(id);
        entity.setStatus(1);
        categoryRepository.save(entity);
    }

    private Category findById(Integer id) {
        return categoryRepository.findByIdAndStatus(id, ACTIVE_STATUS)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}