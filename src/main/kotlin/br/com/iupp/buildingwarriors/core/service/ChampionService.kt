package br.com.iupp.buildingwarriors.core.service

import br.com.iupp.buildingwarriors.core.mapper.ChampionMapper
import br.com.iupp.buildingwarriors.core.model.Champion
import br.com.iupp.buildingwarriors.core.ports.ChampionDatabasePort
import br.com.iupp.buildingwarriors.core.ports.ChampionServicePort
import javax.inject.Singleton

@Singleton
open class ChampionService(private val championDatabase: ChampionDatabasePort) : ChampionServicePort {

    override fun saveChampion(champion: Champion) {
        championDatabase.save(ChampionMapper.championToEntity(champion))
    }

    override fun updateChampion(
        champion: Champion
    ) {
        with(champion) {
            val optionalChampion = championDatabase.findById(id!!)
            optionalChampion.get().apply {
                if (!this@with.name.isNullOrBlank()) name = this@with.name
                if (!this@with.shortDescription.isNullOrBlank()) shortDescription = this@with.shortDescription
                if (this@with.role != null) role = this@with.role
                if (this@with.difficulty != null) difficulty = this@with.difficulty
                championDatabase.update(ChampionMapper.championToEntity(this))
            }
        }
    }

    override fun deleteChampion(champion: Champion) =
        championDatabase.deleteById(champion.id!!)
}