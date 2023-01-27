package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.feature.contract

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.Aggregate
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.domain.home.usecase.ContractInitializationUseCase

class ContractViewModel {

    lateinit var homeAggregate: Aggregate

    fun start() {
        homeAggregate = ContractInitializationUseCase().initialize()
    }

}