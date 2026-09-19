package J2EE.com.example.project.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.ProductCreationRequest;
import J2EE.com.example.project.dto.request.ProductUpdateRequest;
import J2EE.com.example.project.dto.request.VariantRequest;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.dto.response.ProductResponse;
import J2EE.com.example.project.entity.Category;
import J2EE.com.example.project.entity.Color;
import J2EE.com.example.project.entity.NhaCungCap;
import J2EE.com.example.project.entity.ProChitiet;
import J2EE.com.example.project.entity.Product;
import J2EE.com.example.project.entity.Size;
import J2EE.com.example.project.entity.ThuongHieu;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.mapper.ProductMapper;
import J2EE.com.example.project.mapper.VariantMapper;
import J2EE.com.example.project.repository.CategoryRepository;
import J2EE.com.example.project.repository.ColorRepository;
import J2EE.com.example.project.repository.NhaCungCapRepository;
import J2EE.com.example.project.repository.ProductRepository;
import J2EE.com.example.project.repository.SizeRepository;
import J2EE.com.example.project.repository.ThuongHieuRepository;
import J2EE.com.example.project.service.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private static final int ACTIVE_STATUS = 0;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final VariantMapper variantMapper;
    private final ThuongHieuRepository thuongHieuRepository;
    private final CategoryRepository categoryRepository;
    private final NhaCungCapRepository nhaCungCapRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @Override
    public PageResponse<ProductResponse> getAll(Pageable pageable) {
        var page = productRepository.findAllByStatus(ACTIVE_STATUS, pageable).map(productMapper::toResponse);
        return PageResponse.<ProductResponse>builder()
                .content(page.getContent())
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public ProductResponse getById(Integer id) {
        return productMapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public ProductResponse create(ProductCreationRequest request) {
        Product product = productMapper.toEntity(request);
        applyReferences(product, request.getBrandId(), request.getCategoryId(), request.getSupplierId());
        for (VariantRequest variantRequest : request.getVariants()) {
            product.addVariant(toVariant(variantRequest));
        }
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse update(Integer id, ProductUpdateRequest request) {
        Product product = findById(id);
        productMapper.updateEntity(request, product);
        applyReferences(product, request.getBrandId(), request.getCategoryId(), request.getSupplierId());
        synchronizeVariants(product, request);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Product product = findById(id);
        product.setStatus(1);
        productRepository.save(product);
    }

    private void applyReferences(Product product, Integer brandId, Integer categoryId, Integer supplierId) {
        ThuongHieu brand = thuongHieuRepository.findById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        Category category = categoryRepository.findByIdAndStatus(categoryId, ACTIVE_STATUS)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        NhaCungCap supplier = nhaCungCapRepository.findByIdAndStatus(supplierId, ACTIVE_STATUS)
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));

        product.setBrand(brand);
        product.setBrandName(brand.getName());
        product.setCategory(category);
        product.setSupplier(supplier);
    }

    private ProChitiet toVariant(VariantRequest request) {
        ProChitiet variant = variantMapper.toEntity(request);
        variant.setColor(findColor(request.getColorId()));
        variant.setSize(findSize(request.getSizeId()));
        return variant;
    }

    private void synchronizeVariants(Product product, ProductUpdateRequest request) {
        Set<Integer> retainedIds = new HashSet<>();
        for (VariantRequest variantRequest : request.getVariants()) {
            if (variantRequest.getId() == null) {
                product.addVariant(toVariant(variantRequest));
                continue;
            }

            ProChitiet variant = product.getVariants().stream()
                    .filter(item -> variantRequest.getId().equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            variantMapper.updateEntity(variantRequest, variant);
            variant.setColor(findColor(variantRequest.getColorId()));
            variant.setSize(findSize(variantRequest.getSizeId()));
            retainedIds.add(variant.getId());
        }

        product.getVariants().removeIf(variant -> variant.getId() != null
                && !retainedIds.contains(variant.getId()));
    }

    private Product findById(Integer id) {
        return productRepository.findByIdAndStatus(id, ACTIVE_STATUS)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private Size findSize(Integer id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
    }

    private Color findColor(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
    }
}