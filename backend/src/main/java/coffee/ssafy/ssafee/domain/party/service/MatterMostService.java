package coffee.ssafy.ssafee.domain.party.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MatterMostService {

    private final WebClient webClient;

    @Autowired
    public MatterMostService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void sendMMNotification(String webhookUrl, String message) {
        System.out.println("응ㅁㄹㄴ옮맨ㅁㅁㄴㅇㅍㅁㄴㅇㅍㅁㄴㅇㄹ");
//        webClient.post()  -> POST 요청 시작
//                .uri(webhookUrl) -> 요청할 URL 설정
//                .bodyValue("{\"text\": \"" + message + "\"}") -> 요청 분문에 전송할 데이터를 JSON형식으로 설정
//                .retrieve() -> 요청을 실행하고 응답을 검색하기 위한 준비
//                .toBodilessEntity() -> 응답 본문을 가져오지 않고, 응답상태와 헤더만을 처리하기 위해 사용
//                .block(); -> 요청이 완료될 때까지 기다린 후, 결과를 반환
        webClient.post()
                .uri(webhookUrl)
                .bodyValue("{\"text\": \"" + message + "\"}")
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
