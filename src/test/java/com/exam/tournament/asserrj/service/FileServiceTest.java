package com.exam.tournament.asserrj.service;

import com.exam.tournament.dataProviders.FileDataProvider;
import com.exam.tournament.model.container.GameInfoContainer;
import com.exam.tournament.model.container.player.BasketballPlayerInfoContainer;
import com.exam.tournament.model.personal.BasketBallPersonalResult;
import com.exam.tournament.service.FilesService;
import com.exam.tournament.service.impl.personal_result_service.BasketBallPersonalResultService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
@AllArgsConstructor
class FileServiceTest {
    @Autowired
    private FilesService filesService;
    @Autowired
    private FileDataProvider fileDataProvider;
    @Autowired
    private BasketBallPersonalResultService personalResultService;

    @Test
    void readCSV() {
        List<List<String>> data = filesService.readCSV(fileDataProvider.getBasketBallTestFile());
        assertThat(data)
                .size().isEqualTo(7);
        assertThat(data.get(0).get(0))
                .isEqualTo("BASKETBALL");
    }

    @Test
    void transformCSVTest(){
        GameInfoContainer container = filesService.transformCSV(fileDataProvider.getBasketBallTestFile());
        assertThat(container)
                .hasFieldOrPropertyWithValue("gameType", "BASKETBALL");
        assertThat(container.getPlayersInfo())
                .size().isEqualTo(6);
        Set<BasketBallPersonalResult> personalResultSet = container.getPlayersInfo()
                .stream()
                .map(info->personalResultService.createPersonalResult((BasketballPlayerInfoContainer) info))
                .collect(Collectors.toSet());
        int a = 0;
    }
}
