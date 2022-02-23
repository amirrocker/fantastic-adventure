package de.amirrocker.fantasticadventure.functionallist

sealed class FunctionalList<out T> {
    object Nil : FunctionalList<Nothing>()
    data class Construct<T>(val head: T, val tail: FunctionalList<T>): FunctionalList<T>()
}

fun listOfString(vararg strings:String):FunctionalList<String> = if(strings.isEmpty()) {
    FunctionalList.Nil
} else {
    FunctionalList.Construct(
        head = strings.first(),
        tail = listOfString(*strings.drop(1).toTypedArray())
    )
}


//fun main(): Unit = runBlocking {

//    val funList = listOfString("String 1", "String 2", "String 3")
//
//    println(funList)

//    val job:Job = coroutineScope {
//        launch(Dispatchers.Default) {
//            repeat(10) {
//                println("repeated ${this.isActive}: $it")
//                delay(1000)
//            }
//        }
//    }

//    async {
//        repeat(10) {
//            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
//    //            println("repeat main: $it")
//        }
//    }

//    launch { // context of the parent, main runBlocking coroutine
////        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
//    }
//    scope(1)
//    scope(2)
//    scope(3)
//    scope(4)
//    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
//        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
//    }
//    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
//        println("Default               : I'm working in thread ${Thread.currentThread().name}")
//        repeat(100) {
//            println("repeated ${this.isActive}: $it")
//            println("Default               : I'm working in thread ${Thread.currentThread().name}")
//            delay(10)
//        }
//    }
//    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
//        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
//        repeat(10) {
//            println("repeated ${this.isActive}: $it")
//            println("newSingleThreadContext               : I'm working in thread ${Thread.currentThread().name}")
//            delay(100)
//        }
//    }



//}

//suspend fun scope(index:Int) = coroutineScope {
//    val job1:Job = async {
//        repeat(2) {
//            measureMyTime {
//                delay(1000)
//                println("measureMyTime $index.1")
//            }
//        }
//    }
//    val job2 = async {
//        repeat(5) {
//            measureMyTime {
//                delay(500)
//                println("measureMyTime $index.2")
//            }
//        }
//    }
//    job1.cancelAndJoin()
//    job2.join()
//    println("job1: ${job1.isActive}")
//    println("job2: ${job2.isActive}")
//}
//
//suspend fun measureMyTime( block: suspend () -> Unit ) {
//    block()
//}







