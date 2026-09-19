package J2EE.com.example.project.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.ThuongHieuRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.ThuongHieuResponse;
import J2EE.com.example.project.entity.ThuongHieu;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.mapper.ThuongHieuMapper;
import J2EE.com.example.project.repository.ThuongHieuRepository;
import J2EE.com.example.project.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThuongHieuServiceImpl implements ThuongHieuService {

    private final ThuongHieuRepository thuongHieuRepository;
    private final ThuongHieuMapper thuongHieuMapper;

    @Override
    public PageResponse<ThuongHieuResponse> getAll(Pageable pageable) {
        var page = thuongHieuRepository.findAll(pageable).map(thuongHieuMapper::toResponse);
        return PageResponse.<ThuongHieuResponse>builder()
                .content(page.getContent())
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public ThuongHieuResponse getById(Integer id) {
        return thuongHieuMapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public ThuongHieuResponse create(ThuongHieuRequest request) {
        return thuongHieuMapper.toResponse(
                thuongHieuRepository.save(thuongHieuMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public ThuongHieuResponse update(Integer id, ThuongHieuRequest request) {
        ThuongHieu entity = findById(id);
        thuongHieuMapper.updateEntity(request, entity);
        return thuongHieuMapper.toResponse(thuongHieuRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        thuongHieuRepository.delete(findById(id));
    }

    private ThuongHieu findById(Integer id) {
        return thuongHieuRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
    }
}