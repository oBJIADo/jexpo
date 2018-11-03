package ru.tsystems.jirexpo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.tsystems.jirexpo.dto.CommentDto;
import ru.tsystems.jirexpo.service.api.CommentService;

@RestController
@RequestMapping("rest")
@CrossOrigin
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * Get comments by 'keys' field
     * 
     * @param key
     *            {@link CommentDto}'s key
     * @return List with comments
     */
    @GetMapping("/comments/{key}")
    public List<CommentDto> getByTaskKey(@PathVariable("key") String key) {
        return commentService.findCommentByTask(key);
    }

}
