package br.com.iupp.buildingwarriors.infrastructure.database.entity

import java.util.*

data class ChampionEntity(
    var name: String,
    var shortDescription: String,
    var role: ChampionRole,
    var difficulty: ChampionDifficulty,
    var id : UUID? = null
)
