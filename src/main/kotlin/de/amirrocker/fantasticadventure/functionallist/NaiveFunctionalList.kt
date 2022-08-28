package de.amirrocker.fantasticadventure.functionallist

// TODO what about diff. datastructures like LinkedList, DoubleLinkedList, Set and Map ?
sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Construct<T>(val head: T, val tail: FunList<T>) : FunList<T>()

    fun forEach(f:(T)->Unit) {
        tailrec fun processItem(list:FunList<T>, f:(T)->Unit) {
            when(list) {
                is Construct -> {
                    f(list.head)
                    processItem(list.tail, f)
                }
                is Nil -> Unit
            }
        }
        processItem(this, f)
    }

    fun <R> fold(init:R, f:(R, T)->R):R {
        tailrec fun processItem(list:FunList<T>, init:R, f:(R, T)->R):R =
            when(list) {
                is Nil -> init
                is Construct -> processItem(list.tail, f(init, list.head), f)
            }
        return processItem(this, init, f)
    }
}

fun funListOf(vararg strings: String): FunList<String> = if (strings.isEmpty()) {
    FunList.Nil
} else {
    FunList.Construct(
        head = strings.first(),
        tail = funListOf(*strings.drop(1).toTypedArray())
    )
}

fun test() {

    // the ugly way :)
//    val f = FunList.Construct(
//        head = "String1",
//        tail = FunList.Construct(
//            head = "String2",
//            tail = FunList.Construct(
//                head = "String3",
//                tail = FunList.Nil
//            ))
//    )

    // the good way
    val f = funListOf("String1", "String2", "String3", "String4")
    println("f: $f")

    f.forEach {
        println("it: $it")
    }

    val foldedList = f.fold("1") { stringA:String, stringB:String ->
        stringA.replaceFirstChar { if( it.isLowerCase() ) it.titlecase() else it.toString() }
            .plus(stringB.replaceFirstChar { if( it.isLowerCase() ) it.titlecase() else it.toString() })
    }
    println("foldedList: $foldedList")



}
