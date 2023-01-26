package com.exam.tournament.model.personal;

import com.exam.tournament.model.Player;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@Getter
public abstract class PersonalResult {
    private Player player;
}
