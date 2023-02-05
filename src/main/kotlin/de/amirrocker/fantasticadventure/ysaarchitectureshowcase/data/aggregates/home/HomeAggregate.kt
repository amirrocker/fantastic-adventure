package de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data

import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.model.Contract
import de.amirrocker.fantasticadventure.ysaarchitectureshowcase.data.model.Customer

sealed class Aggregate // if there are overarching concerns, these could be inserted here, like authorization or authentication.

class HomeAggregate(
    customer: Customer,
    contract: Contract
): Aggregate() {

    lateinit var customer: Customer
    lateinit var contract: Contract

//    lateinit var adress: Address
//    lateinit var communications: Communication
//    lateinit var personalData: PersonalData
//    lateinit var paymentData: PaymentData
//      ...

    fun validate() {
        println("validate all components inside the aggregate to ensure consistency")
    }

    fun validateContract() {
        println("validate the contract and its leafs for invalid entries")

    }

    fun editAddress() {
        // ....
    }

    fun editName() {
        // ....
    }

}