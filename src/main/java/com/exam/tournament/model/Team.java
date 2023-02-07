package com.exam.tournament.model;

import com.exam.tournament.model.personal.PersonalResult;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Builder
@Value
public class Team {
    String name;
    Set<? extends PersonalResult> personalResults;
}
