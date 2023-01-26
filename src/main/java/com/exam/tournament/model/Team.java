package com.exam.tournament.model;

import com.exam.tournament.model.personal.PersonalResult;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

/**
 * Class is used to store information about teams
 */
@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Team {
    String name;
    Set<PersonalResult> personalResults;
}
