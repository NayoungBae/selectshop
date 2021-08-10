package com.nao.selectshop.utils;

import com.nao.selectshop.models.ItemDto;
import com.nao.selectshop.models.Product;
import com.nao.selectshop.models.ProductRepository;
import com.nao.selectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
public class Scheduler {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final NaverShopSearch naverShopSearch;

    //실행 시각. 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 * * * *") //cron: 정해진 시간에 실행되는 것
    public void updatePrice() throws InterruptedException { //오류가 나면 에러메시지를 띄움
        System.out.println("----------Scheduler 실행) 가격 업데이트 실행----------");
        // 저장된 모든 관심상품을 조회합니다.
        List<Product> productList = productRepository.findAll();
        for (int i=0; i<productList.size(); i++) {
            // 1초에 한 상품 씩 조회합니다 (Naver 제한)
            //네이버에서 너무 짧은 시간 안에 요청이 자주 들어오면 막기 때문에 넣음
            TimeUnit.SECONDS.sleep(1);
            // i 번째 관심 상품을 꺼냅니다.
            Product p = productList.get(i);
            // i 번째 관심 상품의 제목으로 검색을 실행합니다.
            String title = p.getTitle();
            String resultString = naverShopSearch.search(title);
            // i 번째 관심 상품의 검색 결과 목록 중에서 첫 번째 결과를 꺼냅니다.
            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);
            ItemDto itemDto = itemDtoList.get(0);
            // i 번째 관심 상품 정보를 업데이트합니다.
            Long id = p.getId();
            productService.updateBySearch(id, itemDto);
        }
    }
}
