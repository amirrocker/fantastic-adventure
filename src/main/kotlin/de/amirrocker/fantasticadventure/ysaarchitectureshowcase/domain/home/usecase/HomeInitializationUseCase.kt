package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.domain.home.usecase

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.HomeAggregate
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.datasource.customer.CustomerRepositoryPort
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.domain.repository.ContractRepositoryPort
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.feature.home.domain.HomeInitializationPort

class HomeInitializationUseCase : HomeInitializationPort {

    lateinit var customerRepository: CustomerRepositoryPort
    lateinit var contractRepositoryPort: ContractRepositoryPort

    override fun initialize():HomeAggregate = viewModelScope.async {
        val customer = customerRepository.fetchCustomer()
        val contract = contractRepositoryPort.fetchContract()
        HomeAggregate(customer, contract)
    }



}