package de.amirrocker.fantasticadventure.arrow

import arrow.core.*
import kotlin.random.Random

/**
 * just for the heck of it :) A minimizer for any function
 *
 */
//tailrec fun <I:Number, O:Number> minimize( fn: (I)->O, min: I, budget:Int):I =
//    if(budget <= 0) min
//    else minimize(fn, sample<I>().let { input ->
//        val resultA = fn(input)
//        val resultB = fn(min)
//        if( resultA.toDouble() <= resultB.toDouble()) input else min
//        }, budget - 1
//    )
//
////fun <T> sample( fn: (T) -> T, input: T):T = fn(input)
//fun <T:Number> sample():T = Random(12345).nextDouble(0.0, 2.0)

/**
 * Arrow (https://arrow-kt.io)
 *
 * Arrow is a functional library that was combined from
 * two previous functional libraries to avoid splitting the
 * kotlin functional community.
 * funKTional & Kategory (combined in late 2017)
 *
 * Arrow consists of these topics and more:
 * - function composition
 * - Partial application
 */

/**
 * lets start with function composition
 * arrow offers three types of function composition:
 * - compose => invoke result of right-hand func as parameter for left-hand func.
 * - forwardCompose => invoke result of left-hand func as parameter for right hand func.
 * - andThen => alias for forwardCompose
 */

val p: (String) -> String = { body -> "<p>$body</p>" }

val br: (String) -> String = { body -> "<br>$body</br>" }

val span: (String) -> String = { body -> "<span>$body</span>" }

val strong: (String) -> String = { body -> "<strong>$body</strong>" }

val div: (String) -> String = { body -> "<br>$body</br>" }

val randomNames: () -> String = {
    if (Random.nextInt() % 2 == 0) {
        "Foo"
    } else {
        "bar"
    }
}

fun testComposition() {

    // compose some
    val divStrong: (String) -> String = div compose strong

    val randomString: () -> String = randomNames andThen strong

    println(divStrong("Divstrong text"))
    println(randomString())

}

// (String) -> String

data class Quote(
    val value: Double,
    val client: String,
    val item: String,
    val quantity: Int,
) {
    companion object {
        fun valueOf(
            value: Double,
            client: String,
            item: String,
            quantity: Int,
        ) = Quote(
            value,
            client,
            item,
            quantity,
        )
    }
}

data class Bill(
    val value: Double,
    val client: String
)

data class PickingOrder(val item: String, val quantity: Int)

// compose the classes
fun calculatePrice(quote: Quote) = Bill(
    quote.value * quote.quantity,
    quote.client
) to PickingOrder(quote.item, quote.quantity)

fun filterBills(billAndOrder: Pair<Bill, PickingOrder>): Pair<Bill, PickingOrder>? {
    val (bill, _) = billAndOrder
    println("filterAndBills: received bill: $bill")
    println("filterAndBills: received bill: ${bill.value}")
    return if (bill.value >= 11) {
        println("filterAndBills: received bill: ${bill.value}")
        billAndOrder
    } else {
        println("filterAndBills: received bill: ${bill.value}")
        null
    }
}

fun warehouse(order: PickingOrder) {
    println("processing warehouse order: $order")
}

fun accounting(bill: Bill) {
    println("processing warehouse bill: $bill")
}

fun splitter(billAndOrder: Pair<Bill, PickingOrder>?) {
    println("splitter : $billAndOrder")
    if (billAndOrder != null) {
        warehouse(order = billAndOrder.second)
        accounting(bill = billAndOrder.first)
    }
}

fun calculatePrices() {

    val salesSystem: (Quote) -> Unit = ::calculatePrice andThen ::filterBills andThen ::splitter

    salesSystem(
        Quote.valueOf(
            value = 1.0,
            client = "String Client1",
            item = "Item 1",
            quantity = 25
        )
    )

    /*
    salesSystem(
        Quote.valueOf(
            value = 3.0,
            client = "String Client2",
            item = "Item 2",
            quantity = 10
        )
    )
    */
}

// partial application
/**
 * Function composition takes two functions to create a third.
 * With partial application a new function is created by passing a parameter to an existing function.
 * There are two flavors of partial application, implicit and explicit.
 * explicit uses a number of extension functions called partially1, partially2 ...
 * implicit uses a series of extensions overloading the invoke operator.
 */

fun partialApplication() {

    val strong: (String, String, String) -> String = { body, id, style ->
        "<strong id=$id style=$style>$body</strong>"
    }
    val redStrong: (String, String) -> String = strong.partially3("font: red")
    val blueStrong: (String, String) -> String = strong.partially3("font: blue")

    println(redStrong("Dragon Rising", "game1"))
    println(blueStrong("Wargame Red Dragon", "game2"))

    val redStrong1: (String, String) -> String = strong.partially1("Body Modified")
    println(redStrong1("Dragon Rising1", "game3"))

    val redStrong2: (String, String) -> String = strong.partially2("Id Modified")
    println(redStrong2("Dragon Rising2", "game4"))

}

fun partialSplitter(
    billAndOrder: Pair<Bill, PickingOrder>?,
    warehouse: (PickingOrder) -> Unit,
    accounting: (Bill) -> Unit
) {
    billAndOrder?.let { pair ->
        warehouse(pair.second)
        accounting(pair.first)
    } ?: println("no billAndOrder received.")
}

fun testPartialSplitter() {

    val splitter: (Pair<Bill, PickingOrder>?, (Bill) -> Unit) -> Unit = ::partialSplitter.partially2 { pickingOrder ->
        println("testingPartialSplitter: order: $pickingOrder")
    }

    val salesSystem: (quote: Quote) -> Unit = ::calculatePrice andThen ::filterBills andThen ::splitter

    salesSystem(Quote(150.0, "VeryImportantClient", "SuperGoo", 30))


}





