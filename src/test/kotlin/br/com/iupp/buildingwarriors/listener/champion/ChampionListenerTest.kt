package br.com.iupp.buildingwarriors.listener.champion

import br.com.iupp.buildingwarriors.core.mapper.ChampionMapper.Companion.championEventToModel
import br.com.iupp.buildingwarriors.core.ports.ChampionServicePort
import br.com.iupp.buildingwarriors.infrastructure.broker.listener.ChampionListener
import br.com.iupp.buildingwarriors.infrastructure.broker.request.ChampionEvent
import br.com.iupp.buildingwarriors.infrastructure.broker.request.ChampionRequest
import br.com.iupp.buildingwarriors.infrastructure.broker.request.Operation.*
import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

@MicronautTest
class ChampionListenerTest : AnnotationSpec() {

    private val mockedService = mockk<ChampionServicePort>()

    private val listener = ChampionListener(mockedService)

    @Test
    fun `deve aceitar operação de cadastrado`() {
        val request = ChampionRequest(
            name = "Ahri",
            shortDescription = "Com uma conexão inata com o poder latente de Runeterra, Ahri é uma vastaya capaz de transformar magia em orbes de pura energia.",
            role = "MAGE",
            difficulty = "MODERATE"
        )
        val championEvent = ChampionEvent(operation = CREATE, championRequest = request)
        every { mockedService.saveChampion(championEventToModel(championEvent.championRequest)) } returns Unit

        listener.getMessage(championEvent)

        verify { mockedService.saveChampion(championEventToModel(championEvent.championRequest)) }
    }

    @Test
    fun `deve aceitar operação de atualização`() {
        val request = ChampionRequest(
            id = UUID.randomUUID().toString(), name = "Ahri",
            shortDescription = "Com uma conexão inata com o poder latente de Runeterra, Ahri é uma vastaya capaz de transformar magia em orbes de pura energia.",
            role = "MAGE",
            difficulty = "MODERATE"
        )
        val championEvent = ChampionEvent(operation = UPDATE, championRequest = request)
        every { mockedService.updateChampion(championEventToModel(championEvent.championRequest)) } returns Unit

        listener.getMessage(championEvent)

        verify { mockedService.updateChampion(championEventToModel(championEvent.championRequest)) }
    }

    @Test
    fun `deve aceitar operação de exclusão`() {
        val request = ChampionRequest(id = UUID.randomUUID().toString())
        val championEvent = ChampionEvent(operation = DELETE, championRequest = request)
        every { mockedService.deleteChampion(championEventToModel(championEvent.championRequest)) } returns Unit

        listener.getMessage(championEvent)

        verify { mockedService.deleteChampion(championEventToModel(championEvent.championRequest)) }
    }
}