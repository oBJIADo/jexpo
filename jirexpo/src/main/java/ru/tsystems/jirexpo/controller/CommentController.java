package ru.tsystems.jirexpo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.jirexpo.dto.CommentDto;
import ru.tsystems.jirexpo.service.api.CommentService;

import java.util.List;

@RestController
@RequestMapping("rest")
@CrossOrigin
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * Get comments by 'keys' field
     *
     * @param key {@link CommentDto}'s key
     * @return List with comments
     */
    @GetMapping("/comments/{key}")
    public List<CommentDto> getByTaskKey(@PathVariable("key") String key) {
        return commentService.findCommentByTask(key);
    }

}
