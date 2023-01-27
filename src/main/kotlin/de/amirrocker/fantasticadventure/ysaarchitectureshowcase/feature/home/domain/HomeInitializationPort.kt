package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.feature.home.domain

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.HomeAggregate

interface HomeInitializationPort {

    fun initialize(): HomeAggregate

}