package com.exam.tournament.service;

import com.exam.tournament.dataProviders.FileDataProvider;
import com.exam.tournament.model.container.GameInfoContainer;
import com.exam.tournament.model.container.player.BasketballPlayerInfoContainer;
import com.exam.tournament.model.container.player.HandballPlayerInfoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class FilesServiceTest {
    @Autowired
    private FilesService filesService;
    @Autowired
    private FileDataProvider fileDataProvider;

    @Test
    void getFileNames() {
        Set<File> files = filesService.getFileNames();
        assertThat(files).
                size().isEqualTo(3);
    }

    @Test
    void readCSV() {
        List<List<String>> data = filesService.readCSV(fileDataProvider.getBasketBallTestFile());
        assertThat(data).size().isEqualTo(7);
        assertThat(data.get(0).get(0)).isEqualTo("BASKETBALL");
    }

    @Test
    void identifyFileType() {
        String csvFormat = "csv";
        Set<File> files = filesService.getFileNames();
        files.stream()
                .map(filesService::identifyFileType)
                .forEach(format -> assertThat(format).isEqualTo(csvFormat));
    }

    @Test
    void transformCSVBasketBallTest() {
        File file = fileDataProvider.getBasketBallTestFile();
        GameInfoContainer container = filesService.transformCSV(file);
        assertThat(container)
                .hasFieldOrPropertyWithValue("gameType", "BASKETBALL");
        assertThat(container.getPlayersInfo())
                .size().isEqualTo(6);
        container.getPlayersInfo()
                .forEach(info -> assertThat(info).isInstanceOf(BasketballPlayerInfoContainer.class));
    }

    @Test
    void transformCSVHandBallTest() {
        File file = fileDataProvider.getHandBallTestFile();
        GameInfoContainer container = filesService.transformCSV(file);
        assertThat(container)
                .hasFieldOrPropertyWithValue("gameType", "HANDBALL");
        assertThat(container.getPlayersInfo())
                .size().isEqualTo(6);
        container.getPlayersInfo()
                .forEach(info -> assertThat(info).isInstanceOf(HandballPlayerInfoContainer.class));

    }
}