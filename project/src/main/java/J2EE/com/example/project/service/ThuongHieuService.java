package J2EE.com.example.project.service;

import org.springframework.data.domain.Pageable;

import J2EE.com.example.project.dto.request.ThuongHieuRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.ThuongHieuResponse;

public interface ThuongHieuService {

    PageResponse<ThuongHieuResponse> getAll(Pageable pageable);

    ThuongHieuResponse getById(Integer id);

    ThuongHieuResponse create(ThuongHieuRequest request);

    ThuongHieuResponse update(Integer id, ThuongHieuRequest request);

    void delete(Integer id);
}