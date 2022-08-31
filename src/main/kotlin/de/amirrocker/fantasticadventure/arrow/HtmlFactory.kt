package de.amirrocker.fantasticadventure.arrow


/**
 * the most basic element is the
 * Element interface from which all html elements used must derive from.
 * It offers a single render method that is used by derived TextElement classes
 * to draw the text on screen using a StringBuilder.
 */
interface Element {
    fun render(builder: StringBuilder, indent: String)
//    fun renderX(indent: String, builder: (StringBuilder, String) -> StringBuilder): StringBuilder
}

class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }

//    override fun renderX(indent: String, builder: (StringBuilder, String) -> StringBuilder): StringBuilder =
//        builder(StringBuilder(), indent)

}

/**
 * controlling Scope and validating receiver type is done
 * using these Annotations.
 * @DSLMarker
 * @HtmlTagMarker
 *
 */
@DslMarker
annotation class HtmlTagMarker

@HtmlTagMarker
abstract class Tag(val name: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    // methods marked with X are experimental rewrites
    protected fun <T : Element> initTagX(tag: T, init: T.() -> Unit): T = tag.apply {
        tag.init()
        children.add(tag)
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name${renderAttributes()}>\n")
        children.forEach { element ->
            element.render(builder, "$indent ")
        }
        builder.append("$indent</$name>\n")
    }

//    override fun renderX(builder: StringBuilder, indent: String): StringBuilder =
//        with(builder) {
//            append("$indent<$name${renderAttributes()}>\n")
//            children.forEach { element ->
//                element.render(builder, "$indent ")
//            }
//            append("$indent</$name>\n")
//        }


    private fun renderAttributes(): String {
        val builder = StringBuilder()
        for ((attr, value) in attributes) {
            builder.append(" $attr=\"$value\"")
        }
        return builder.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}

/**
 * overloading the unaryPlus operator to concatenate strings inside the function.
 * It adds a {@see TextElement} as a child of the parent html tag.
 */
abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

abstract class BodyTag(name: String) : TagWithText(name) {
    fun b(init: B.() -> Unit) = initTag(B(), init)
    fun p(init: P.() -> Unit) = initTag(P(), init)
}



/**
 * base classes are now defined and the Specialized Node classes are next.
 */
data class Html(val tag: String = "", val value: String = "") : TagWithText("html") {

    fun head(init: Head.() -> Unit): Head = initTag(Head(), init)

    fun body(init: Body.() -> Unit): Body = initTag(Body(), init)
}

data class Head(val tag: String = "", val value: String = "") : TagWithText("head") {
    fun title(init: Title.() -> Unit): Title = initTag(Title(), init)
}

data class Title(val tag: String = "", val value: String = "") : TagWithText("title")

class Body : BodyTag("body")
class B : BodyTag("b")
class P : BodyTag("p")


//
//data class Body(val tag: String = "", val value: String = "") : TagWithText("body")
//
//fun html(init: Html.() -> Unit): Html = Html().apply(init)
//
//
//fun testIt() {
//
//    html {
//        println("html builder called")
//        head {
//            println("head builder called")
//        }
//        body {
//            println("body builder called")
//        }
//    }
//}

