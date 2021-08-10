package com.nao.selectshop.utils;

import com.nao.selectshop.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component //컴포넌트 등록
public class NaverShopSearch {
    //Java에서 url에 한글로 입력을 해도 인코딩을 알아서 해줌
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //네이버 API가 정의한대로 헤더를 넘겨줌
        headers.add("X-Naver-Client-Id", "Fzj17ucnTdxEPXlsLH8O");
        headers.add("X-Naver-Client-Secret", "Tpspa8UeG_");
        String body = ""; //응답받은 결과물이 들어갈 변수

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value(); //200이나 404 이런게 들어감
        String response = responseEntity.getBody(); //Request에 대한 Response가 문자열로 들어감
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    public List<ItemDto> fromJSONtoItems(String result) {
        JSONObject resultJson = new JSONObject(result);
        System.out.println("resultJson :\n" + resultJson); //출력 결과 중 [] : json 배열(JSONArray 클래스)
        JSONArray items = resultJson.getJSONArray("items"); //매개변수는 key:value 중 key값
        List<ItemDto> itemDtoList = new ArrayList<>(); //클래스를 담을 List 선언
        for (int i=0; i<items.length(); i++) { //JSONArray 클래스에서는 size()가 아닌 length()
            JSONObject itemJson = items.getJSONObject(i); //= (JSONObject) items.get(i)
            System.out.println("itemJson :\n" + itemJson);
            ItemDto itemDto = new ItemDto(itemJson);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

}
