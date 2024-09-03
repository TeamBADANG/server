package com.gbcontentagency.arlo.apis;

import com.gbcontentagency.arlo.comments.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


}
