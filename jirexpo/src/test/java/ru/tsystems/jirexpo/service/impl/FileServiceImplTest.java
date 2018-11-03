package ru.tsystems.jirexpo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tsystems.jirexpo.service.api.FileService;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class FileServiceImplTest {

    @Autowired
    private FileService fileService;

    @Test
    public void getFiles(){
        List<String> actuals = fileService.getFilesNames("AD-1986");
        List<String> expected = new LinkedList<>();
        expected.add("Оnderung_Q00011808000_LD_LK_20061006_081214.doc");

        assertEquals(expected, actuals);

    }

    @Test
    public void getFiles2(){
        List<String> actuals = fileService.getFilesNames("AD-3241");
        List<String> expected = new LinkedList<>();
        expected.add("Error_SmartSlices.zip");
        expected.add("Error_Smart_Slices.doc");

        assertEquals(expected, actuals);

    }

}