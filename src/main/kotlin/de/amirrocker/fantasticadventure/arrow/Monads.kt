package de.amirrocker.fantasticadventure.arrow

import arrow.continuations.Effect

// Monads
// https://arrow-kt.io/docs/patterns/monads/

// naive impl.
//class Speaker {
//    fun nextTalk() : Talk = TODO()
//}
//
//class Talk {
//    fun nextConference() : Conference = TODO()
//}
//
//class Conference {
//    fun getCity() : City = TODO()
//}
//
//class City
//
//// compose into a workflow
//fun workflow() = Speaker()
//    .nextTalk()
//    .nextConference()
//    .getCity()

// with Either<A, B> == Not ideal

//object NotFound
//
//class Speaker {
//    fun nextTalk() : Either<NotFound, Talk> = Either.Left(NotFound)
//}
//
//class Talk {
//    fun nextConference() : Either<NotFound, Conference> = Either.Left(NotFound)
//}
//
//class Conference {
//    fun getCity() : Either<NotFound, City> = Either.Left(NotFound)
//}
//
//class City
//
//// compose into a workflow
//fun workflow() = Speaker()
//    .nextTalk()         .flatMap { x -> x
//    .nextConference()   }
//                        .flatMap { x -> x
//    .getCity()          }

// using suspend functions

//class Speaker {
//    suspend fun nextTalk() : Talk = TODO()
//}
//
//class Talk {
//    suspend fun nextConference() : Conference = TODO()
//}
//
//class Conference {
//    suspend fun getCity() : City = TODO()
//}
//
//class City
//
//suspend fun workflow() = Speaker()
//    .nextTalk()
//    .nextConference()
//    .getCity()

// generalize the form:

//class Speaker {
//    fun nextTalk(): WorkflowThatReturns<Talk> = TODO()
//}
//
//class Talk {
//    fun nextConference(): WorkflowThatReturns<Conference> = TODO()
//}
//
//class Conference {
//    fun getCity(): WorkflowThatReturns<City> = TODO()
//}
//
//class City
//
///**
// *  Result so far: using suspend functions is most natural to read and use. That is because
// *  kotlin suspend is a form of Continuation Monad from which other monads can be generalized and
// *  composed thanks to its async and concurrent nature.
// *  source: https://arrow-kt.io/docs/patterns/monads/
// */
//
//abstract class WorkflowThatReturns<T> {
//    abstract fun <U> addStep(step: (T) -> WorkflowThatReturns<U>): WorkflowThatReturns<U>
//}
//
//fun workflowThat(speaker: Speaker): WorkflowThatReturns<City> =
//    speaker
//        .nextTalk()
//        .addStep { x ->
//            x
//                .nextConference()
//        }
//        .addStep { x ->
//            x
//                .getCity()
//        }

// This pattern is what is called a Monad!

// Fun Fact: this is a SAM(single abstract method) or Functional Interface
// already exists -> use the library version
// the overwritten CustomEffect is for learning only!
//fun interface CustomEffect<F> : Effect<F> {
//    override fun control(): DelimitedScope<F>
//}


class Speaker {
    fun nextTalk(): Talk = TODO()
}

class Talk {
    fun nextConference(): Conference = TODO()
}

class Conference {
    fun getCity(): City = TODO()
}

class City

// now define effect for data type that implements Monad bind.
// TODO dive deeper into Monad bind

// both interface and object already exist -> use the library version
// again the impl. is simply for educational purpose!
fun interface NullableEffect<A> : Effect<A?> {
    suspend fun <B> B?.bind(): B =
        this ?: control().shift(null) // where control().shift(value) can be seen as functional throw
}

object nullable {
    operator fun <A> invoke(func: suspend NullableEffect<*>.() -> A?): A? =
        Effect.restricted(eff = {
            NullableEffect {
                it
            }
        },
            f = func, just = { it }
        )
}

// ** All this allows us to use the nullable effect block and its bind function
// ** to compute over the happy path of nullable typed values
suspend fun nextTalkCity(maybeSpeaker: Speaker?): City? =
    de.amirrocker.fantasticadventure.arrow.nullable {
        val speaker = maybeSpeaker.bind()
        val talk = speaker.nextTalk().bind()
        val conference = talk.nextConference().bind()
        val city = conference.getCity().bind()
        city
    }

/**
 * The Effect Interface provides a restricted scope for pure computations (pure=no side effects)that do
 * NOT require suspension and a suspended block for those that do require suspension.
 */

/** OPTION

data class Customer(val addressId: Int)
data class Address(val id: Int, val lastOrder: Option<Order>)
data class Order(val id: Int, val shipper: Shipper = Shipper)
object Shipper

interface OptionRepository {
fun getCustomer(id: Int): Option<Customer> = TODO()
fun getAddress(id: Int): Option<Address> = TODO()
fun getOrder(id: Int): Option<Order> = TODO()

}

fun interface OptionEffect<A> : Effect<Option<A>> {
suspend fun <B> Option<B>.bind(): B =
fold({ control().shift(None) }, { it })
}

sealed class Option<T> {
object None : Option<Nothing>()
data class Success<T>(val value: T) : Option<T>()
}

suspend fun <T> Option<T>.fold(any: Any, function: () -> Unit): T {
TODO("Not yet implemented")
}
/** I DO NOT GET THE ABOVE CODE TO WORK! ME BAD!!! */

 */ // TODO GET IT! MAKE! IT! WORK!

// lets move on with Monad Laws

data class Just<out A>(val value: A)

fun interface JustEffect<A> : Effect<Just<A>> {
    suspend fun <B> Just<B>.bind(): B = value
}

object effect {
    operator fun <A> invoke(func: suspend JustEffect<*>.() -> A): Just<A> =
        Effect.restricted(eff = { JustEffect { it } }, f = func, just = { Just(it) })
}

// Left identity law
// define x && f
const val x = 1
fun f(x: Int): Just<Int> = Just(x)

// then
fun LeftIdentityLaw() =
    effect {
        val x2 = Just(x).bind()
        f(x2).bind()
    }

// same as
fun LeftIdentityLawShort() =
    effect {
        f(x).bind()
    }

fun runLeftIdentityLaw() {
    // prints: Just(value=1)
    println(LeftIdentityLaw())
}

val m = Just(0)

// Right identity Law:
// given
fun RightIdentityLaw() {
    // then
    effect {
        val x = m.bind()
        Just(x).bind()
    }
}

fun RightIdentityShort() = effect {
    m.bind()
}

// Associativity Law (Callorder does not matter)

// given
val mAssoc = Just(0)
fun fAssoc(x:Int): Just<Int> = Just(x)
fun gAssoc(x:Int): Just<Int> = Just(x+1)

fun assertAssociativityForward() =
    effect {
        val y = effect {
            val x = mAssoc.bind()
            fAssoc(x).bind()
        }.bind()
        gAssoc(y).bind()
    }

fun assertAssociativityReversed() =
    effect {
        val y = effect {
            val x = fAssoc(x).bind()
            gAssoc(m.bind()).bind()
        }.bind()
        mAssoc.bind()
    }





