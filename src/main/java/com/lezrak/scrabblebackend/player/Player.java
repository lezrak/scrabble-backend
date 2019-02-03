package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {
}
