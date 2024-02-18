package de.amirrocker.fantasticadventure.scratchpad

import de.amirrocker.fantasticadventure.math.Vector3


/**
 * A particle
 *
 * Basics:
 * The first two of the three laws of motion put forward by Newton.
 * 1. An object continues with a constant velocity unless a force acts upon it.
 * 2. a force acting on an object produces acceleration that is proportional to the object's mass.
 *
 * Another important law is newtons 'law of universal gravitation'
 * f = G * (m1 * m2)/r**2
 *
 * where the force f equals the product of G, the "universal gravitational constant", multiplied by the product
 * of the masses of both objects, mass m1 and mass m2, divided by the square of the distance between these two
 * objects' centers. 
 *
 */
data class Particle(
    /**
     * holds the linear position of the particle in world space
     */
    var position: Vector3,

    /**
     * holds the linear velocity of the particle in world space
     */
    var velocity: Vector3,

    /**
     * holds the linear acceleration of the particle in world space
     */
    var acceleration: Vector3,

    /**
     * holds the amount of damping to linear motion. Damping is required to remove
     * energy added through numerical instability in the integrator.
     */
    val damping: Double,

    /**
     * Holds the inverse of the mass of the particle.
     * It is more useful to hold the inverse mass because integration is simpler
     * and because in real-time sumulation it is more useful to have objects eith
     * infinite mass (immovable) than zero mass (unstable in numerical simulation)
     */
    val inverseMass: Double,
) {

    /**
     * Gravitation is the most important force in any physics engine. In the real world
     * gravity applies between every pair of objects, attracting them together with a force that
     * depends on their mass and distance.
     *
     * newtowns "law of universal gravitation":
     * f = G(m1*m2/r*r)
     * where
     * G = universal gravitational constant
     * m1, m2 = masses of the two objects
     *
     * We can simplify that equation by assuming m1 to be constant.
     * Since we are only interested in the pull of gravity on earth we can use
     * f = m * g
     * where
     * m is the mass of simulated object
     * f is the force
     * g is a constant that inclues the universal gravirational cvonstant, the mass of earth and its radius.
     * we arrive at g using
     *
     * g = G(m/(r*r))
     *
     * This received constant, g, is an acceleration which we shall measure in meters per second.
     * On earth this is around 10 m(s pow-2) // ten meters per second squared neg
     *
     * Value of g
     * gravity can be represented as
     *
     * accG = [0, -g, 0]
     *
     * which is an acceleration vector accG with the g component as the y value.
     *
     * Integration
     * integrating these formulas we can now update the position of our object.
     * At each frame the engine needs to look at each object in turn, work out its acceleration
     * and perform the integration step.The calculation of the acceleration in this case will be trivial since
     * we use the acceleration due to gravity only.
     * * The timing of frames needs to be calculated based on framerate *
     *
     * The Integrator
     * Integrator consists of two parts, one to update the position and one to update the velocity.
     * The position will depend on the velocity and acceleration,
     * while the velocity will depend only on the acceleration.
     *
     *
     *
     */
    fun doPositionUpdate() {
        // position update
        // p'= p + velP(t) + 1/2 accP(t*t)
    }

}