package com.exam.tournament.model.personal;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class HandBallPersonalResult extends PersonalResult{
    int goalsMade;
    int goalsReceived;
}
