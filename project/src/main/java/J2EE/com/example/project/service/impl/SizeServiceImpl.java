package J2EE.com.example.project.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.SizeRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.SizeResponse;
import J2EE.com.example.project.entity.Size;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.mapper.SizeMapper;
import J2EE.com.example.project.repository.SizeRepository;
import J2EE.com.example.project.service.SizeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Override
    public PageResponse<SizeResponse> getAll(Pageable pageable) {
        var page = sizeRepository.findAll(pageable).map(sizeMapper::toResponse);
        return PageResponse.<SizeResponse>builder()
                .content(page.getContent())
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public SizeResponse getById(Integer id) {
        return sizeMapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public SizeResponse create(SizeRequest request) {
        return sizeMapper.toResponse(sizeRepository.save(sizeMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public SizeResponse update(Integer id, SizeRequest request) {
        Size entity = findById(id);
        sizeMapper.updateEntity(request, entity);
        return sizeMapper.toResponse(sizeRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        sizeRepository.delete(findById(id));
    }

    private Size findById(Integer id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
    }
}