package ru.tsystems.jirexpo.components.impl;

        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
        import org.springframework.transaction.annotation.Transactional;
        import ru.tsystems.jirexpo.dao.api.TaskDao;
        import ru.tsystems.jirexpo.dto.TaskDto;
        import ru.tsystems.jirexpo.entity.Task;

        import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    TaskDao taskDao;

    @Test
    @Transactional
    public void convertToDto() {
        Task task = taskDao.getBykey("AD-12521");
        TaskDto taskDto = Mapper.convertToDto(task);
        assertNotNull(taskDto);
    }
}