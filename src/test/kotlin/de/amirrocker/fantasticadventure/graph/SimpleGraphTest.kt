package de.amirrocker.fantasticadventure.graph

import de.amirrocker.fantasticadventure.data.SimpleGraph
import org.junit.Test

class SimpleGraphTest {

    @Test
    internal fun `test our next attempt at a usable graph`() {

        val simpleGraph = SimpleGraph()

        println("simple graph: $simpleGraph")

//        simpleGraph.runDepthFirst("a")

//        simpleGraph.runDepthFirstRec("a")

        simpleGraph.runBreadthFirst("a")

    }
}