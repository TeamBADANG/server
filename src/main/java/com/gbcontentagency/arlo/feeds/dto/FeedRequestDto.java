package com.gbcontentagency.arlo.feeds.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FeedRequestDto {

    private LocalDate createdDate;

    private LocalDate updatedDate;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String thumbnailUrl;

    private List<String> images;

    public FeedRequestDto(String title, String content, String thumbnailUrl, List<String> images) {
        LocalDate now = LocalDate.now();
        this.createdDate = now;
        this.updatedDate = now;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = (thumbnailUrl != null) ? thumbnailUrl : "https://cem.cnu.ac.kr/_custom/cnu/resource/img/tmp_gallery.png"; // default 이미지 삽입 필요
        this.images = (images != null) ? images : new ArrayList<>();
    }

}
