package com.exam.tournament.model.personal;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

/**
 * Class is user to store information about player results in basketball one game
 */
@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BasketBallPersonalResult extends PersonalResult {
    int pointsScored;
    int rebounds;
    int assists;
}
