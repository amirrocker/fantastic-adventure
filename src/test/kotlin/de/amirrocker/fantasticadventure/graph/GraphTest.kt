package de.amirrocker.fantasticadventure.graph

import de.amirrocker.fantasticadventure.data.Graph
import de.amirrocker.fantasticadventure.data.asNode
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContainAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GraphTest {

    lateinit var graph: Graph

    @BeforeEach
    internal fun setUp() {
        // given
        graph = Graph() // graphOf {}
    }

    @Test
    internal fun `given a graph when a node is added then it becomes an entry in the nodes Set`() {

        // when
        graph.addNode("SomeHashableObject")

        // then
        val result = graph.getNode(0)
        println(result)
        result.value shouldBeEqualTo "SomeHashableObject".hashCode()
    }

    @Test
    internal fun `given a graph when many nodes are added then they become entries in the nodes Set`() {

        // when
        graph.addNode("A")
        graph.addNode("B")
        graph.addNode("C")
        graph.addNode("D")

        // then
        val result = graph.getAllNodes()
        println(result)
//        result shouldContainAll mutableSetOf("A", "B", "C", "D").asNodes() // TODO IMPLEMENT asNodes() on the Set
        result shouldContainAll mutableSetOf( "A".asNode(), "B".asNode(), "C".asNode(), "D".asNode())
    }

    @Test
    internal fun `given a graph when many nodes are added then the presentation matches the input`() {

        // when
        graph.addNode("A")
        graph.addNode("B")

        // then
        val result = graph.getNode("A")
        println(result.presentation)
        result.presentation shouldBeEqualTo "A".asNode().presentation
    }

    @Test
    internal fun `given a graph when an edge is added then the node owns the edge`() {

        // when
        graph.addNode("A")
        graph.addNode("B")

//        graph.addEdge("B".asEdge("A".asNode()))
//
//        // then
//        val result = graph.getNode("A")
//        val edges = result.edges
//        println("edges: $edges")
//        edges.elementAt(0) shouldBeEqualTo mutableSetOf(Edge("A".hashCode(), "B".hashCode(), ""))
    }



//    @Test
//    internal fun `given a graph when many nodes are added then they become entries in the nodes Set`() {
//
//        // when
//        graph.addNode("A")
//        graph.addNode("B")
//        graph.addNode("C")
//
//        // then
//        val result = graph.getAllNodes()
//        println(result)
//        result shouldBeEqualTo  setOf("A".hashCode(), "B".hashCode(), "C".hashCode())
//    }

//    @Test
//    internal fun `given a graph when edges are added then they connect states or nodes`() {
//
//        // when
//        graph.addNode("A")
//        graph.addNode("B")
//        graph.addNode("C")
//
//        // then
//
//    }



}