package com.exam.tournament.dataProviders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.StringJoiner;

@Component
@ActiveProfiles("test")
public class FileDataProvider {
    @Value("${files.location}")
    private String dir;

    public final File getBasketBallTestFile() {
        StringJoiner joiner = new StringJoiner("//");
        joiner.add(dir);
        joiner.add("b1.csv");

        return new File(joiner.toString());
    }

    public final File getHandBallTestFile() {
        StringJoiner joiner = new StringJoiner("//");
        joiner.add(dir);
        joiner.add("g1.csv");

        return new File(joiner.toString());
    }
}
