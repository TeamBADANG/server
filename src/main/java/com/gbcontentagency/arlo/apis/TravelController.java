package com.gbcontentagency.arlo.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gbcontentagency.arlo.chatGPT.ChatGPTService;
import com.gbcontentagency.arlo.travels.TravelService;
import com.gbcontentagency.arlo.travels.dto.TravelRequestDto;
import com.gbcontentagency.arlo.travels.dto.TravelResponseDto;
import com.gbcontentagency.arlo.users.oauth2.CustomOAuth2UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequestMapping("/travels")
@RestController
public class TravelController {

    private final TravelService travelService;

    private final ChatGPTService chatGPTService;

    public TravelController(TravelService travelService, ChatGPTService chatGPTService) {
        this.travelService = travelService;
        this.chatGPTService = chatGPTService;
    }

    @PostMapping("/preferences/v1")
    public ResponseEntity<String> saveUserPreferencesV1(@AuthenticationPrincipal CustomOAuth2UserEntity oauth2User
//                                                                 ,@RequestBody TravelRequestDto travelRequestDto) {
    ) throws JsonProcessingException {

        TravelRequestDto travelRequestDto = TravelRequestDto.builder()
                .travelDuration("3박 4일")
                .transportType("자차")
                .mainPurpose(Arrays.asList("가족여행", "힐링"))
                .foodType(Arrays.asList("한식", "양식"))
                .outdoorPreference("50% 선호")
                .accommodationType("민박")
                .accommodationBudget("20만 원")
                .specialConsiderations(Arrays.asList("반려동물 동반 가능"))
                .build();

        TravelResponseDto userPreferences = travelService.saveUserPreferences(travelRequestDto, oauth2User);


        StringBuilder userPreferencesList = new StringBuilder();
        userPreferencesList.append("예시: 사용자는 경북 지역에서 1박 2일 여행을 계획 중입니다. 당일치기 여행을 포함하며, 주된 목적은 휴식 및 힐링, 역사 및 문화 탐방입니다. 사용자는 자가용을 이용할 예정이며, 전통 한옥에서 숙박하고 싶어합니다. 음식은 현지인이 추천하는 한식을 선호하며, 자연 경관이 아름답고 전통 문화재가 있는 한적하고 조용한 곳을 방문하고 싶어합니다. 여행 중에는 맑은 날씨를 선호합니다.\n")
                .append("위의 예시처럼 다음 주어지는 데이터를 바탕으로 질문 템플릿을 생성해줘.\n")
                .append("현재 위치: ").append("영천시").append("\n")
                .append("현재 시간: ").append("2024-08-27 14:44:21").append("\n")
                .append("여행 기간: ").append(travelRequestDto.getTravelDuration()).append("\n")
                .append("교통 수단: ").append(travelRequestDto.getTransportType()).append("\n")
                .append("주요 여행 목적: ").append(travelRequestDto.getMainPurpose()).append("\n")
                .append("선호하는 음식 종류: ").append(travelRequestDto.getFoodType()).append("\n")
                .append("선호하는 식사 스타일: ").append(travelRequestDto.getDiningStyle()).append("\n")
                .append("야외 활동 선호 여부: ").append(travelRequestDto.getOutdoorPreference()).append("\n")
                .append("선호하는 숙박 형태: ").append(travelRequestDto.getAccommodationType()).append("\n")
                .append("고려하고 싶은 숙소 편의시설: ").append(travelRequestDto.getAccommodationFacilities()).append("\n")
                .append("숙박 예산 형태: ").append(travelRequestDto.getAccommodationBudget()).append("\n")
                .append("기타 여행에 고려할 사항: ").append(travelRequestDto.getSpecialConsiderations()).append("\n");
        System.out.println("질문 생성 전 프롬프트: \n" + userPreferencesList + "\n");

        String question = chatGPTService.createQuestionTemplate(userPreferencesList.toString());


        StringBuilder questionTemplate = new StringBuilder();
        questionTemplate.append("예시:    1일차 (당일치기 포함):\n" +
                        "   추천 코스: 안동 하회마을 → 월영교 → 도산서원\n" +
                        "\n" +
                        "    이동 거리:\n" +
                        "     안동 하회마을에서 월영교: 약 10km (차량으로 약 15분)\n" +
                        "     월영교에서 도산서원: 약 25km (차량으로 약 30분)\n" +
                        "\n" +
                        "    추천 맛집:\n" +
                        "     안동 하회마을 근처: **\"안동찜닭 거리\"**에 위치한 \"원조 안동찜닭\"\n" +
                        "     월영교 근처: 야경을 감상하며 차를 마실 수 있는 \"카페 월영\"\n" +
                        "\n" +
                        "    코스 설명:\n" +
                        "     안동 하회마을: 전통 한옥이 잘 보존된 마을로, ...\n" +
                        "     월영교: 저녁에는 아름다운 야경을 감상할 수 있는 장소로, 한적한 산책 코스로 추천됩니다.\n" +
                        "     도산서원: 조선 시대의 대표적인 서원으로, 자연과 어우러진 고즈넉한 분위기를 즐기며...\n" +
                        "\n" +
                        "  2일차:\n" +
                        "   추천 코스: 영주 부석사 → 영주 한정식 거리\n" +
                        "\n" +
                        "    이동 거리:\n" +
                        "     안동 숙소에서 부석사: 약 40km (차량으로 약 50분)\n" +
                        "     부석사에서 영주 한정식 거리: 약 12km (차량으로 약 20분)\n" +
                        "\n" +
                        "    추천 맛집:\n" +
                        "     부석사 근처: 전통 차를 즐길 수 있는 \"부석사 다향\"\n" +
                        "     영주 한정식 거리: 정갈한 한정식을 제공하는 \"삼정 한정식\"\n" +
                        "\n" +
                        "    코스 설명:\n" +
                        "     부석사: 경북 지역의 대표적인 불교 사찰로, 자연 경관과 함께 한국의 전통 건축미를 감상..\n" +
                        "     영주 한정식 거리: 전통 한식이 특화된 거리로, 현지인 추천 맛집에서 정갈한 한정식을 맛볼..\n")
                .append("위의 예시처럼 다음 주어지는 데이터를 바탕으로 여행 코스를 추천해주되 나는 예시라서 뒤에 ... 했지만, 정확한 문장으로 코스를 추천 및 설명해주고, 구체적인 상호명을 포함한 기념품샵 가능하다면 코스마다 최대한 많이 추천해줘.\n")
                .append(question);
        System.out.println("코스 추천 전 프롬프트: \n" + questionTemplate + "\n");

        chatGPTService.recommendationTravelCourse(questionTemplate.toString());

        return ResponseEntity.ok(question);
    }

}