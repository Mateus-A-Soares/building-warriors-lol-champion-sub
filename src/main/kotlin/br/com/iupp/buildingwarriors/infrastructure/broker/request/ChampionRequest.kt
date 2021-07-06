package br.com.iupp.buildingwarriors.infrastructure.broker.request

data class ChampionRequest(
    val id: String? = null,
    val name: String? = null,
    val shortDescription: String? = null,
    val role: String? = null,
    val difficulty: String? = null
)
