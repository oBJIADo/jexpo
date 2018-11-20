package ru.tsystems.divider.service.api;

import org.springframework.stereotype.Service;
import ru.tsystems.divider.entity.Nature;

@Service
public interface NatureService {

    Nature getOrAddNatureByTitle(String title);

}
