package com.gbcontentagency.arlo.travels;

import com.gbcontentagency.arlo.users.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "travels")
@Entity
public class TravelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. 여행 기간
    @Column
    private String travelDuration;  // 여행 기간 (예: "1박 2일", "2박 3일")

    // 2. 이동 수단
    @Column
    private String transportType;  // 이동 수단 (예: "자차 이용", "대중교통 이용", "렌터카 이용")

    // 3. 여행 목적
    @ElementCollection
    private List<String> mainPurpose;  // 주요 목적 (예: ["휴식 및 힐링", "역사 및 문화 탐방"])

    // 4. 맛집 선호도
    @ElementCollection
    private List<String> foodType;  // 음식 종류 (예: ["한식", "일식"])

    @ElementCollection
    private List<String> diningStyle;  // 식사 스타일 (예: ["현지인 추천 맛집", "미슐랭/고급 레스토랑"])

    // 5. 관광지 선호도
    @Column
    private String outdoorPreference;  // 야외 활동 여부 (예: "야외 활동 선호", "실내 활동 선호")

    // 6. 숙박 선호도
    @Column
    private String accommodationType;  // 숙박 형태 (예: "호텔/리조트", "전통 한옥")

    @ElementCollection
    private List<String> accommodationFacilities;  // 숙소 편의시설 (예: ["수영장/스파", "조식 제공"])

    @Column
    private String accommodationBudget;  // 예산 (예: "저가형", "중간 가격대", "고가형")

    // 7. 기타 선호 사항
    @ElementCollection
    private List<String> specialConsiderations;  // 기타 선호 사항 (예: ["어린이 동반", "반려동물 동반"])

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public TravelEntity(String travelDuration, String transportType, List<String> mainPurpose,
                        List<String> foodType, List<String> diningStyle, String outdoorPreference,
                        String accommodationType, List<String> accommodationFacilities, String accommodationBudget,
                        List<String> specialConsiderations, UserEntity user) {
        this.travelDuration = travelDuration;
        this.transportType = transportType;
        this.mainPurpose = mainPurpose;
        this.foodType = foodType;
        this.diningStyle = diningStyle;
        this.outdoorPreference = outdoorPreference;
        this.accommodationType = accommodationType;
        this.accommodationFacilities = accommodationFacilities;
        this.accommodationBudget = accommodationBudget;
        this.specialConsiderations = specialConsiderations;
        this.user = user;
    }

}
