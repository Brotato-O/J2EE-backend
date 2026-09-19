package J2EE.com.example.project.service;

import org.springframework.data.domain.Pageable;

import J2EE.com.example.project.dto.request.NhaCungCapRequest;
import J2EE.com.example.project.dto.response.NhaCungCapResponse;
import J2EE.com.example.project.dto.response.PageResponse;

public interface NhaCungCapService {

    PageResponse<NhaCungCapResponse> getAll(Pageable pageable);

    NhaCungCapResponse getById(Integer id);

    NhaCungCapResponse create(NhaCungCapRequest request);

    NhaCungCapResponse update(Integer id, NhaCungCapRequest request);

    void delete(Integer id);
}