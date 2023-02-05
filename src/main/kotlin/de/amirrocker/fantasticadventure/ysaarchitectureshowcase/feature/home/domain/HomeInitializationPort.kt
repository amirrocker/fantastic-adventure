package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.feature.home.domain

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.HomeAggregate

interface HomeInitializationPort {

    suspend fun initialize(): HomeAggregate

}