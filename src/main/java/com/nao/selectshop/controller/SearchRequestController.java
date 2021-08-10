package com.nao.selectshop.controller;

import com.nao.selectshop.models.ItemDto;
import com.nao.selectshop.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//이 어노테이션을 넣음으로써 컴포넌트로 등록하는 것
@RequiredArgsConstructor // final 로 선언된 클래스를 자동으로 생성합니다. 컴포넌트로 등록되어있어야 가능한 기능
@RestController // JSON으로 응답함을 선언합니다.
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;

    @GetMapping("/api/search")
    //@RequestParam: 요청 시 파라미터 중에 query라는 게 있으면 넣어주라는 뜻
    public List<ItemDto> search(@RequestParam String query) {
        String result = naverShopSearch.search(query); //리턴 String
        List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(result);
        return itemDtoList;
    }
}
