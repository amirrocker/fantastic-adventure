package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.feature.home

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.Aggregate
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.domain.home.usecase.ContractInitializationUseCase

class HomeViewModel {

    lateinit var homeAggregate: Aggregate

    fun start() {
        homeAggregate = ContractInitializationUseCase().initialize()
    }

}