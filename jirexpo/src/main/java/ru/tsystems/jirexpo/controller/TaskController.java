package ru.tsystems.jirexpo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.jirexpo.dto.TaskDto;
import ru.tsystems.jirexpo.service.api.FileService;
import ru.tsystems.jirexpo.service.api.TaskService;

import java.util.List;

@RestController
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FileService fileService;

    /**
     * Get page with a tasks.
     *
     * @param num         Page count.
     * @param itemsOnPage Items on a page.
     * @return List with {@link TaskDto}
     */
    @GetMapping(value = "tasks")
    public List<TaskDto> getPage(@RequestParam(name = "num") int num,
                                 @RequestParam(name = "itemsOnPage") int itemsOnPage) {
        return taskService.getTasks(num, itemsOnPage);
    }

    /**
     * Get page with a tasks.
     *
     * @param num           Page count.
     * @param itemsOnPage   Items on a page.
     * @param param         Param for searching
     * @param searchIndexes Indexes of a params for searching {@link ru.tsystems.jirexpo.components.impl.QueryBuilder}
     * @return List with {@link TaskDto}
     * @see ru.tsystems.jirexpo.components.impl.QueryBuilder
     */
    @GetMapping("tasks/search")
    public List<TaskDto> getPage(@RequestParam(name = "num") int num,
                                 @RequestParam(name = "itemsOnPage") int itemsOnPage, @RequestParam(name = "param") String param,
                                 @RequestParam(name = "searchIndexes") String searchIndexes,
                                 @RequestParam(name = "caseIgnore") Boolean caseIgnore) {
        if (searchIndexes == null || searchIndexes.isEmpty()) {
            return taskService.getTasks(num, itemsOnPage, param);
        } else {
            return taskService.getTasks(num, itemsOnPage, param, searchIndexes, caseIgnore);
        }
    }

    /**
     * Get a task with selected key
     *
     * @param taskKey Task's key
     * @return {@link TaskDto}
     */
    @GetMapping("tasks/{key}")
    public TaskDto getTask(@PathVariable("key") String taskKey) {
        return taskService.findByKey(taskKey);
    }

    /**
     * Get count of pages.
     *
     * @param itemsOnPage Tasks on one page
     * @return Count of a pages
     */
    @GetMapping(value = "pages")
    public int getPagesCount(@RequestParam(name = "itemsOnPage") Integer itemsOnPage) {
        return taskService.getPagesCount(itemsOnPage);
    }

    /**
     * Get count of pages.
     *
     * @param itemsOnPage   Tasks on one page
     * @param param         Param for searching
     * @param searchIndexes Indexes of a params for searching {@link ru.tsystems.jirexpo.components.impl.QueryBuilder}
     * @return Count of a pages
     */
    @GetMapping(value = "pages/search")
    public int getPagesCount(@RequestParam(name = "itemsOnPage") Integer itemsOnPage,
                             @RequestParam(name = "param") String param, @RequestParam(name = "searchIndexes") String searchIndexes,
                             @RequestParam(name = "caseIgnore") Boolean caseIgnore) {
        if (searchIndexes == null || searchIndexes.isEmpty()) {
            return taskService.getPagesCount(itemsOnPage, param);
        } else {
            return taskService.getPagesCount(itemsOnPage, param, searchIndexes, caseIgnore);
        }
    }

    @GetMapping(value = "files/{key}")
    public List<String> getFilesNames(@PathVariable(name = "key") String key) {
        return fileService.getFilesNames(key);
    }

    @GetMapping(value = "files/{key}/download")
    public ResponseEntity<Resource> getFile(@PathVariable(name = "key") String key, @RequestParam(name = "fileName") String fileName) {
        Resource resource = fileService.getFile(key, fileName);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }

}
