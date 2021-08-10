package com.nao.selectshop.service;

import com.nao.selectshop.models.ItemDto;
import com.nao.selectshop.models.Product;
import com.nao.selectshop.models.ProductMypriceRequestDto;
import com.nao.selectshop.models.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long update(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        product.update(requestDto);
        return product.getId();
    }

    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다")
        );
        product.updateByItemDto(itemDto);
        return product.getId();
    }

}
