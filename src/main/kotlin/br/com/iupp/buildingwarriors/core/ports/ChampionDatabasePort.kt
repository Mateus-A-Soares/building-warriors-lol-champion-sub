package br.com.iupp.buildingwarriors.core.ports

import br.com.iupp.buildingwarriors.core.model.Champion
import br.com.iupp.buildingwarriors.infrastructure.database.entity.ChampionEntity
import java.util.*

interface ChampionDatabasePort {

    fun findById(id: UUID): Optional<Champion>
    fun save(champion: ChampionEntity): Champion
    fun update(champion: ChampionEntity): Champion
    fun deleteById(id: UUID)
}
