package de.amirrocker.fantasticadventure.mystatquest

import kotlin.math.absoluteValue
import kotlin.math.floor

/**
 *
 * Reading notes for HeadFirst Statistics
 *
 * A Quartile anatomy
 * (p90+)
 *
 * Given a few keywords like
 * - lower bound
 * - upper bound
 * - first quartile aka lower quartile
 * - median
 * - third quartile aka upper quartile
 * - interquartile range
 *
 * Find the lower quartile by
 * first declare n / 4
 *
 * if the result is an integer then
 * the lower quartile lies between this position
 * and the next. Take the avg. of these two positions
 * to get the lower quartile.
 *
 * if the result is not an integer, then round it up. that
 * gives the position of the lower quartile.
 *
 * e.g. given you have 6 positions in the sample, then you
 * do n / 4, which is 6 / 4 = 1.5
 * then you round up 2 which gives us the lower quartile.
 *
 * Find the upper quartile by using
 * upper quartile = 3n / 4
 *
 * if the result is an integer, then
 * the upper quartile again lies between this and the next position.
 * Take the avg of these two to get the upper quartile, i.e. (n[i] + n[i+1])/2
 *
 * if the result is not an integer, then round it up. that
 * gives the position of the upper quartile.
 *
 */
fun <T:Number> List<T>.findSampleRange():Double = this.maxOf { t: T -> t.toDouble() }.minus(this.minOf { t: T -> t.toDouble() })

fun <T:Number> List<T>.findMean() = this.sumOf { t: T -> t.toDouble() }.div(this.size)

fun <T:Number> List<T>.lowerQuartile() =
    (this.size / 4.0).absoluteValue.let { it - floor(it) }


//this.sumOf { t: T -> t.toDouble() }.div(this.size)
