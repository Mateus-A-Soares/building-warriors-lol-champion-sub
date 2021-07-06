package br.com.iupp.buildingwarriors.core.service

import br.com.iupp.buildingwarriors.core.mapper.ChampionMapper.Companion.championToEntity
import br.com.iupp.buildingwarriors.core.model.Champion
import br.com.iupp.buildingwarriors.core.model.ChampionDifficulty.MODERATE
import br.com.iupp.buildingwarriors.core.model.ChampionRole.MAGE
import br.com.iupp.buildingwarriors.core.model.ChampionRole.TANK
import br.com.iupp.buildingwarriors.core.ports.ChampionDatabasePort
import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

@MicronautTest
class ChampionServiceImplTest : AnnotationSpec() {

    private val mockedRepository = mockk<ChampionDatabasePort>()

    private val service = ChampionService(mockedRepository)

    @Test
    fun `deve cadastrar champion`() {
        val champion = Champion(
            name = "Ahri",
            shortDescription = "Com uma conexão inata com o poder latente de Runeterra, Ahri é uma vastaya capaz de transformar magia em orbes de pura energia.",
            role = MAGE,
            difficulty = MODERATE
        )
        every { mockedRepository.save(championToEntity(champion)) } returns champion

        service.saveChampion(champion)

        verify { mockedRepository.save(championToEntity(champion)) }
    }

    @Test
    fun `deve atualizar champion`() {
        val champion = Champion(
            id = UUID.randomUUID(),
            name = "Shen",
            shortDescription = "Com uma conexão inata com o poder latente de Runeterra, Ahri é uma vastaya capaz de transformar magia em orbes de pura energia.",
            role = TANK,
            difficulty = MODERATE
        )
        val updateChampionRequest = Champion(
            id = champion.id,
            name = "Ahri",
            role = MAGE
        )
        every { mockedRepository.findById(champion.id!!) } returns Optional.of(champion)
        every {
            mockedRepository.update(
                championToEntity(champion.apply {
                    name = updateChampionRequest.name
                    role = updateChampionRequest.role
                }
                ))
        } returns champion

        service.updateChampion(updateChampionRequest)

        verify { mockedRepository.update(championToEntity(champion)) }
    }

    @Test
    fun `deve deletar campeao`() {
        val champion = Champion(id = UUID.randomUUID())
        every { mockedRepository.deleteById(champion.id!!) } returns Unit

        service.deleteChampion(champion)

        verify { mockedRepository.deleteById(champion.id!!) }
    }
}