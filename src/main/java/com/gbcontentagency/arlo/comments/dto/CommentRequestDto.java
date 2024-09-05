package com.gbcontentagency.arlo.comments.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentRequestDto {

    private LocalDate createdDate;

    private LocalDate updatedDate;

    @NotBlank
    private String comment;

    public CommentRequestDto(String comment) {
        LocalDate now = LocalDate.now();
        this.createdDate = now;
        this.updatedDate = now;
        this.comment = comment;
    }

}
