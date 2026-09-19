package J2EE.com.example.project.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.ColorRequest;
import J2EE.com.example.project.dto.response.ColorResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.entity.Color;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.mapper.ColorMapper;
import J2EE.com.example.project.repository.ColorRepository;
import J2EE.com.example.project.service.ColorService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public PageResponse<ColorResponse> getAll(Pageable pageable) {
        var page = colorRepository.findAll(pageable).map(colorMapper::toResponse);
        return PageResponse.<ColorResponse>builder()
                .content(page.getContent())
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public ColorResponse getById(Integer id) {
        return colorMapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public ColorResponse create(ColorRequest request) {
        return colorMapper.toResponse(colorRepository.save(colorMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public ColorResponse update(Integer id, ColorRequest request) {
        Color entity = findById(id);
        colorMapper.updateEntity(request, entity);
        return colorMapper.toResponse(colorRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        colorRepository.delete(findById(id));
    }

    private Color findById(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
    }
}