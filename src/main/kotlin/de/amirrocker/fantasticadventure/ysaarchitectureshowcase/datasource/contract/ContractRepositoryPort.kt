package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.datasource.contract

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.datasource.contract.local.ContractDataSource
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.datasource.contract.remote.ContractService
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.domain.repository.ContractRepositoryPort

class ContractRepositoryPort : ContractRepositoryPort {

    private var isOffline: Boolean = false

    lateinit var contractDataSource:ContractDataSource
    lateinit var contractService:ContractService

    override fun fetchContract() {
        if(isOffline) {
            contractDataSource.getContract()
        } else {
            contractService.getContract()
        }
    }
}