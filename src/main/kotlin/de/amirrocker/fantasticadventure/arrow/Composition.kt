package de.amirrocker.fantasticadventure.arrow

enum class Currency {
    USD,
    EUR,
    JPY,
}
data class Price(val value: Double, val currency: Currency)

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val price: Price,
    val weight: Double,
    val isbn: String,
)

val aBookTitle = Book(8851, "Mobile Agents", "Have to look it up and I dont want to right now!", "How to use mobile agents on distributed systems", Price(29.99, Currency.EUR), 520.0, "isbn-6549")

val aBookTitleFun = fun(book:Book) = book.title
val aBookIdFun = fun(book:Book) = book.id
val aBookDescriptionFun = fun(book:Book) = book.description

fun testFunctionAsVal() = println(aBookTitleFun(aBookTitle))

/**
 * this is a fundamental concept.
 * **The Type of a function depends on the Input and Output types**
 * where the Input type is a set of possible values and the Output type is a set of possible values.
 * Most importantly, the Input and Output Types say nothing about how the mapping from Input to Output is done.
 * That gives functions their own kind of Polymorphism!
 */

typealias BookMapper<T> = (Book) -> T

//fun bookWeight(book:Book) = book.weight
val bookWeight = fun(book:Book) = book.weight

fun bookDescription(book:Book) = book.description

fun bookPrice(book:Book) = book.price.value

fun bookId(book:Book) = book.id

val testBookA = Book(8852, "The Sleepwalkers","Christopher Clark", "How Europe went to war in 1914", Price(49.99, Currency.EUR), 520.0, "isbn-1234")
val testBookB = Book(8853, "To Hell and back", "Ian Kershaw", "Europe 1914-1949", Price(59.99, Currency.EUR), 520.0, "isbn-1234")
val testBookC = Book(8854, "Neuromancer", "William Gibson", "Cyberpunk at its finest", Price(45.99, Currency.EUR), 520.0, "isbn-1234")

fun functionPolymorphism() {


    var mapper: BookMapper<Double> = bookWeight
    var description: BookMapper<String> = ::bookDescription
    println("weight: ${mapper(testBookA)} gram with description: ${description(testBookA)}")

    val id: BookMapper<Int> = { book -> book.id }
    val currency: BookMapper<String> = { book -> book.price.currency.name }
    println("id: ${id(testBookB)} gram with currency: ${currency(testBookB)}")


    mapper = ::bookPrice
    val title: BookMapper<String> = { book -> book.title }
    println("price: ${mapper(testBookC)} and title: ${title(testBookC)}")
}

fun List<Book>.total(fn: BookMapper<Double>): Double = fold(0.0) { acc: Double, book: Book -> acc + fn(book) }

val mustHaves = listOf(testBookA, testBookB, testBookC)

fun allBooksPrices() = mustHaves
    .total(::bookPrice)

// or when the mapper is a function

fun allBooksWeights() = mustHaves
    .total(bookWeight)



typealias Func<A, B> = (A) -> B

infix fun <A, B> Func<A, B>.after( fn: Func<A, B>):Func<A, B> = { x:A -> fn(x) }

fun test(a:Int, b:Int) {
    println("a and b")
}
fun test2() {
//    test(2, 4) after ({x:Int -> x*x})
    test(2, 4) after {i: Int ->i * i }
}
private infix fun Any.after(fn: (Int) -> Int) {
    TODO("Not yet implemented")
}
