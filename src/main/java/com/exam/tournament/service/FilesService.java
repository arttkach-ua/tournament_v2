package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.providers.MapperProvider;
import com.exam.tournament.mappers.PlayerInfoContainerMapper;
import com.exam.tournament.model.container.GameInfoContainer;
import com.exam.tournament.model.container.player.PlayerInfoContainer;
import com.exam.tournament.util.messages.ErrorMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class FilesService {
    @Getter
    @Value("${files.location}")
    private String dir;

    private final MapperProvider mapperProvider;

    private static final String COMMA_DELIMITER = ";";

    public Set<File> getFileNames() {
        log.debug("Get files names call");
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getAbsoluteFile)
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    public List<List<String>> readCSV(File file) {

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }

    @SneakyThrows
    public GameInfoContainer transformCSV(final File file){
        String gameType;
        Set<PlayerInfoContainer> personalContainers;

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            gameType = br.lines()
                    .findFirst()
                    .orElseThrow(()->new TournamentProcessingException(ErrorMessages.WRONG_FILE_DATA));
            PlayerInfoContainerMapper mapper = mapperProvider.getMapper(gameType);
            personalContainers = br.lines()
                    .map(line->(PlayerInfoContainer)mapper.toContainer(line.split(COMMA_DELIMITER)))
                    .collect(Collectors.toSet());
        }
        return new GameInfoContainer(gameType,personalContainers);
    }

    public String identifyFileType(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else {
            log.error("Could not identify type of file {}", fileName);
            throw new IllegalArgumentException(String.format("Could not identify type of file %s", fileName));
        }
    }
}
