package de.amirrocker.fantasticadventure.evolution

/**
 * Evolution:
 *
 * 1. Self-replication
 * 2.
 *
 * 1. self replication:
 * A simple datastructure with
 *  - Coord location
 *  - Genome genome
 *  - NeuralNet brain
 * To self replicate we copy these datastructures over a number of evolutionary iterations.
 * Location is the 'physical' location of the Organism on a square based map, defined as x and y positions.
 * Genome is a sequence of DNASegment items, a list of ABCD items.
 *
 *
 */

enum class DNA {
    Undefined,
    D,
    A,
    C,
    E
}

data class DNASegment(
    val dna: DNA = DNA.Undefined,
)

data class Coord(
    val x: Int = 0,
    val y: Int = 0
)

data class Genom(
    val sequence: List<DNASegment>
)
data class NeuralNet<T:Number>(
    val fn: (T)->T
)

data class Organism(
    val location: Coord,
    val genom: Genom,
    val brain: NeuralNet<Double>
)





