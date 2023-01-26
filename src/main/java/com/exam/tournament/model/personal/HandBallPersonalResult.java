package com.exam.tournament.model.personal;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
/**
 * Class is user to store information about player results in one handball game
 */
@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class HandBallPersonalResult extends PersonalResult{
    int goalsMade;
    int goalsReceived;
}
