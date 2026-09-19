package J2EE.com.example.project.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.NhaCungCapRequest;
import J2EE.com.example.project.dto.response.NhaCungCapResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.entity.NhaCungCap;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.mapper.NhaCungCapMapper;
import J2EE.com.example.project.repository.NhaCungCapRepository;
import J2EE.com.example.project.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NhaCungCapServiceImpl implements NhaCungCapService {

    private static final int ACTIVE_STATUS = 0;

    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhaCungCapMapper nhaCungCapMapper;

    @Override
    public PageResponse<NhaCungCapResponse> getAll(Pageable pageable) {
        var page = nhaCungCapRepository.findAllByStatus(ACTIVE_STATUS, pageable)
                .map(nhaCungCapMapper::toResponse);
        return PageResponse.<NhaCungCapResponse>builder()
                .content(page.getContent())
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public NhaCungCapResponse getById(Integer id) {
        return nhaCungCapMapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public NhaCungCapResponse create(NhaCungCapRequest request) {
        return nhaCungCapMapper.toResponse(
                nhaCungCapRepository.save(nhaCungCapMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public NhaCungCapResponse update(Integer id, NhaCungCapRequest request) {
        NhaCungCap entity = findById(id);
        nhaCungCapMapper.updateEntity(request, entity);
        return nhaCungCapMapper.toResponse(nhaCungCapRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        NhaCungCap entity = findById(id);
        entity.setStatus(1);
        nhaCungCapRepository.save(entity);
    }

    private NhaCungCap findById(Integer id) {
        return nhaCungCapRepository.findByIdAndStatus(id, ACTIVE_STATUS)
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
    }
}