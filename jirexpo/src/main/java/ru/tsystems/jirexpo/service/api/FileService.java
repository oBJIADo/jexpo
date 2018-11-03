package ru.tsystems.jirexpo.service.api;

import java.util.List;

import org.springframework.core.io.Resource;

public interface FileService {

    /**
     * Get a file from attachments
     * 
     * @param key
     *            Task's key
     * @return File {@link Resource}
     */
    public Resource getFile(String key, String fileName);

    /**
     * Get all files names in folder which name like key.
     * 
     * @param key
     *            Task's key
     * @return List with files names
     */
    public List<String> getFilesNames(String key);

}
