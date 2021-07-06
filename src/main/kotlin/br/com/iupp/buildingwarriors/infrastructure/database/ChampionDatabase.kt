package br.com.iupp.buildingwarriors.infrastructure.database

import br.com.iupp.buildingwarriors.core.mapper.ChampionMapper.Companion.championEntityToModel
import br.com.iupp.buildingwarriors.core.mapper.ChampionMapper.Companion.cqlRowToChampion
import br.com.iupp.buildingwarriors.core.model.Champion
import br.com.iupp.buildingwarriors.core.ports.ChampionDatabasePort
import br.com.iupp.buildingwarriors.infrastructure.database.entity.ChampionEntity
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import java.util.*
import javax.inject.Singleton

@Singleton
class ChampionDatabase(private val cqlSession: CqlSession) : ChampionDatabasePort {

    override fun findById(id: UUID): Optional<Champion> {
        return Optional.ofNullable(
            cqlSession.execute(SimpleStatement.newInstance("SELECT * from champion where id = ?", id)).one()
        ).map(::cqlRowToChampion)
    }

    override fun save(champion: ChampionEntity): Champion {
        champion.id = UUID.randomUUID()
        cqlSession.execute(
            SimpleStatement.newInstance(
                "INSERT INTO champion(id, name, shortDescription, role, difficulty) VALUES (?,?,?,?,?)",
                champion.id,
                champion.name,
                champion.shortDescription,
                champion.role.toString(),
                champion.difficulty.toString()
            )
        )
        return championEntityToModel(champion)
    }

    override fun update(champion: ChampionEntity): Champion {
        cqlSession.execute(
            SimpleStatement.newInstance(
                "UPDATE champion SET name = ?, shortDescription = ?, role = ?, difficulty = ? WHERE ID = ?",
                champion.name,
                champion.shortDescription,
                champion.role.toString(),
                champion.difficulty.toString(),
                champion.id
            )
        )
        return findById(champion.id!!).orElseThrow { RuntimeException() }
    }

    override fun deleteById(id: UUID) {
        cqlSession.execute(SimpleStatement.newInstance("DELETE from champion where id = ?", id))
    }
}
