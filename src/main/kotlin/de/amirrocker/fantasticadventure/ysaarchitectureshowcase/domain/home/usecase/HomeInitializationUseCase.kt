package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.domain.home.usecase

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.HomeAggregate
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.model.Contract
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.model.Customer
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.datasource.customer.CustomerRepositoryPort
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.domain.repository.ContractRepositoryPort
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.feature.home.domain.HomeInitializationPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeInitializationUseCase : HomeInitializationPort {

    lateinit var customerRepository: CustomerRepositoryPort
    lateinit var contractRepositoryPort: ContractRepositoryPort

    lateinit var viewModelScope: CoroutineScope

    override suspend fun initialize():HomeAggregate = viewModelScope.async {
        val customer = Customer() // customerRepository.fetchCustomer()
        val contract = Contract() // contractRepositoryPort.fetchContract()
        HomeAggregate(customer, contract)
    }.await()
}