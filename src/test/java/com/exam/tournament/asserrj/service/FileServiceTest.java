package com.exam.tournament.asserrj.service;

import com.exam.tournament.dataProviders.FileDataProvider;
import com.exam.tournament.service.FilesService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
@AllArgsConstructor
class FileServiceTest {
    @Autowired
    private FilesService filesService;
    @Autowired
    private FileDataProvider fileDataProvider;

    @Test
    void readCSV() {
        List<List<String>> data = filesService.readCSV(fileDataProvider.getBasketBallTestFile());
        assertThat(data)
                .size().isEqualTo(7);
        assertThat(data.get(0).get(0))
                .isEqualTo("BASKETBALL");
    }
}
