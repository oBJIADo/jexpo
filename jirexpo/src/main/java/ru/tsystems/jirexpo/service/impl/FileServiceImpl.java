package ru.tsystems.jirexpo.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.tsystems.jirexpo.components.api.MessageWorker;
import ru.tsystems.jirexpo.service.api.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

    private final String ATTACH_PATH;

    public FileServiceImpl(@Autowired MessageWorker messageWorker) {
        ATTACH_PATH = messageWorker.getSourceValue("files.folder.attachments");
        if (ATTACH_PATH == null) throw new NullPointerException("No attach folder");
    }


    /**
     * Get a file from attachments
     *
     * @param key Task's key
     * @return File {@link InputStreamResource}
     */
    public Resource getFile(String key, String fileName) {
        logger.info("Try to download Resource, key: " + key + "; file name: " + fileName);
        try {
            String path = ATTACH_PATH + "/" + key + "/" + fileName;
            return new InputStreamResource(new FileInputStream(path));
        } catch (IOException ioexc) {
            logger.error("Cannot to upload file! " + ioexc.getMessage() + "! Key: " + key + "; file name: " + fileName);
            return null;
        }
    }

    /**
     * Get all files names in folder which name like key.
     *
     * @param key Task's key
     * @return List with files names
     */
    public List<String> getFilesNames(String key) {
        logger.info("Reading files in folder: " + ATTACH_PATH + key);
        File folder = new File(ATTACH_PATH + "/" + key);
        List<String> files = new LinkedList<>();
        if (!folder.exists()) {
            logger.warn("Null folder; key: " + key);
            return null;
        }
        for (File file : folder.listFiles()) {
            files.add(file.getName());
        }
        return files;
    }

}
