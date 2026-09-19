package J2EE.com.example.project.service;

import org.springframework.data.domain.Pageable;

import J2EE.com.example.project.dto.request.SizeRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.SizeResponse;

public interface SizeService {

    PageResponse<SizeResponse> getAll(Pageable pageable);

    SizeResponse getById(Integer id);

    SizeResponse create(SizeRequest request);

    SizeResponse update(Integer id, SizeRequest request);

    void delete(Integer id);
}