package com.exam.tournament.service;

import com.exam.tournament.util.messages.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Log4j2
@RequiredArgsConstructor
public class ValidationService {
    private static final String SUPPORTED_FORMAT = "csv";
    private final FilesService filesService;

    public void validateForCSV(File file) {
        String fileFormat = filesService.identifyFileType(file);
        if (!SUPPORTED_FORMAT.equals(fileFormat)) {
            throw new IllegalArgumentException(String.format(ErrorMessages.TYPE_OF_FILE_NOT_SUPPORTED, fileFormat));
        }
    }
}
