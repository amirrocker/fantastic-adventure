package de.amirrocker.fantasticadventure.data

/**
 * A base class for Graph implementations. Each Graph can receive
 * any hashable object as node.
 * Also a graph can associate key/value attribute pairs with each unidirected edge.
 * self loops are allowed on node but multiple edges are not.
 *
 * A Node(value:Int, presentation:String) represents a node inside the graph
 */

data class Node(val value: Int, val presentation: String, val leafs: MutableList<Node>)

fun String.asNode() = Node(
    value=this.hashCode(),
    presentation = this,
    leafs = mutableListOf()
)

//fun String.asEdge(origin:Node) = Edge(
//    from=origin.value,
//    to=this.hashCode(), // validate this Node exists ?
//    presentation = "{${origin.value} => ${this.hashCode()}}"
//)

open class Graph {

    private val _nodes:MutableSet<Node> by lazy { mutableSetOf() }
    protected val nodes: Set<Node> = _nodes

    fun addNode(node:String) {
        addNode(node.asNode())
    }

    fun addNode(node:Node) {
        _nodes.add(node)
    }

    fun getNode(index:Int):Node = nodes.elementAt(index)

    fun getNode(key:String):Node = nodes.find { it.presentation == key } ?: emptyNode()

    fun getAllNodes():Set<Node> = nodes

    fun emptyNode() = Node(0, "", mutableListOf())

    fun addEdge(from:Node, to:Node) = nodes.find { node ->
        from.value == node.value
    }?.leafs?.add(to)

    // _edges.add(to.asEdge(from.asNode()))
    // yet undecided on how to store nodes and edges
    // option a: each Node 'owns' its edges - like a linked list
    // option b: the graph stores nodes and edges separately -> seems networkx used a similar approach
//    fun addEdge(from:String, to:String) = nodes.find { node ->
//        node.value == from.hashCode()
//    }?.edges?.add(to.asEdge(from.asNode()))

}

/**
 * This is playing alongside a youtube course:
 * "Graph Algorithms for Technical Interviews"
 * @author FreeCodeCamp.org
 */
class SimpleGraph {

    val nodes = listOf("a", "b", "c", "d", "e", "f")
    val adjancyList = HashMap<String, Array<String>>()

    init {

        adjancyList["a"] = arrayOf("c", "b")
        adjancyList["b"] = arrayOf("d")
        adjancyList["c"] = arrayOf("e")
        adjancyList["d"] = arrayOf("f")
        adjancyList["e"] = emptyArray()
        adjancyList["f"] = emptyArray()

    }

    // a first shot at DFS
    fun runDepthFirst( startNode: String ) {
        val stack = mutableListOf(startNode)
        while(stack.isNotEmpty()) {
            val current = stack.removeLast()
            println("lastFromStack now current: $current")
            val neighbors:Array<String> = adjancyList.getValue(current)
            neighbors.forEach {
                stack.add(it)
            }
            println("stack after pushing children: $stack")
        }
    }

    fun runDepthFirstRec(startNode: String) {
        val stack = mutableListOf(startNode)
        val current = stack.removeLast()
        println("lastFromStack now current: $current")
        searchForNeighbors(current).forEach {
            runDepthFirstRec(it)
        }
    }
    fun searchForNeighbors(current:String):Array<String> = adjancyList.getValue(current)

    // Breadth-First-Search BFS

    fun runBreadthFirst(source:String) {
        val queue = mutableListOf(source)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            println("current[$current] from queue[$queue]")
            searchForNeighbors(current).forEach {
                queue.add(it)
                println("queue after add: $queue")

            }
        }
    }

    // as a final note on both skeleton implementations
    // look at how similar both algorithms work. It basically
    // comes down to how the queue / stack are accessed and items removed.
    // Other than that impl. is similar.

}



typealias Mapping = Map<String, String>

/**
 * An AtlasView is a Read-only Mapping of Mappings.
 * It is a view into a dict-of-dict data structure, a map of maps.
 * Note: The inner level of the dict is read-write. But
 * the outer layer is read-only.
 *
 * See also:
 * ========
 * AdjacencyView : a view into a dict-of-dict-of-dict
 * MultiAdjacencyView : a view into a dict-of-dict-of-dict-of-dict
 * it's multi alright :)
 */
class AtlasView(val mapping:List<Mapping>) {

    // impl. in python as a Union which is an Either in our world
    // __slots__: Union[str, Iterable[str]]
    protected val __slots__:MutableList<String> by lazy { mutableListOf("atlas",) }

}