package ru.tsystems.jirexpo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.jirexpo.components.impl.Mapper;
import ru.tsystems.jirexpo.dao.api.TaskDao;
import ru.tsystems.jirexpo.dto.CommentDto;
import ru.tsystems.jirexpo.dto.TaskDto;
import ru.tsystems.jirexpo.entity.Task;
import ru.tsystems.jirexpo.service.api.CommentService;
import ru.tsystems.jirexpo.service.api.TaskService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@SpringBootTest
@Transactional
public class ServiceTest {

    private TaskDto[] testTaskDto;

    @Before
    public void init() {
        int count = 10;
        int startPosition = 100;
        testTaskDto = new TaskDto[10];

        for (int i = 0; i < count; i++) {
            testTaskDto[i] = Mapper.convertToDto(taskDao.find(i + startPosition, Task.class));
        }
    }

    private static final int COUNT_OF_TASKS = 13226;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Before
    public void createEntites() {

    }

    @Test
    public void getTaskByKey() {
        TaskDto actual = taskService.findByKey(testTaskDto[0].getKeys());
        assertEquals(testTaskDto[0], actual);
    }

    @Test
    public void getTaskByFakeKey() {
        TaskDto actual = taskService.findByKey("FakeKey");
        assertNull(actual);
    }

    @Test
    public void getCount() {
        int actual = taskService.getPagesCount(1);
        assertEquals(COUNT_OF_TASKS, actual);
    }

    @Test
    public void getPages() {
        int pagesOfThreeElements = COUNT_OF_TASKS / 3 + 1;
        int actual = taskService.getPagesCount(3);
        assertEquals(pagesOfThreeElements, actual);
    }

    @Test
    public void getPagesWithUnrealParam() {
        int actual = taskService.getPagesCount(1, "UnrealParamWhichNotInTasksIHopeSo");
        assertEquals(0, actual);
    }

    /**
     * Sql script for test in /sql_scripts/test/SCRIPT_1
     */
    @Test
    public void getDefaultPages() {
        int actual = taskService.getPagesCount(1, "ad-999");
        assertEquals(16, actual);
    }

    /**
     * Sql script for test in /sql_scripts/test/SCRIPT_2
     */
    @Test
    public void getByKeysOnly() {
        String indexes = "0";
        int actual = taskService.getPagesCount(1, "ad-999", indexes, true);
        assertEquals(10, actual);
    }

    @Test
    public void getCommentsByTaskKey() {
        int taskIndex = 5;
        List<CommentDto> actual = commentService.findCommentByTask(testTaskDto[taskIndex].getKeys());
        List<CommentDto> expected = testTaskDto[taskIndex].getComments();

        assertEquals(expected, actual);
    }

}
