package de.amirrocker.fantasticadventure.scratchpad

import java.lang.Double.sum
import kotlin.math.pow

/**
 * Tabular Learning and the Bellman Equation of Optimality
 *
 * Value of State:
 *
 * The whole concept revolves around Value and how to best calculate it.
 * Value was defined as total reward that is obtainable by the agent from the state.
 * In a formal way, already well described in another comment :), Value is
 * V(s) = sum(rt*sigma**t )
 * where rs is the reward obtained at timestep t
 * Lets start with a simple three state deterministic* environment.
 *   *where all actions succeed
 *
 * S1 -> agents initial state
 * s2 -> agent goes left
 * s3 -> agent goes down
 *
 * Starting State S1 = 1.0
 * s1 -> S2 // S2 is a terminal state and the reward is 1.0
 * |
 * v
 * S3 // S3 is a terminal state and the reward is 2.0
 *
 * this simple example leaves us with several possible states the agent can end up in.
 *
 * 1. agent always goes left
 * 2. agent always goes down
 * 4. agent goes left with a .5 probability and goes down with a .5 probability
 * 3. agent goes left with a 10% probability and goes down with a 90% probability
 * These are random chance values for demonstration, so lets calculate the states for the agent
 *
 * 1. Value = 1.0  = 1
 * 2. Value = 2.0  = 2
 * 3. Value = 0.5 + 1.0 = 1.5
 * 3. Value = 1.0 * 0.1 + 2.0 * 0.9 = 0.1 + 1.8 = 1.9
 *
 * Given this, we have the formula:
 *
 * V0(a=ai|0..i) = ri + Vi
 *
 * using this formula the agent needs to calculate all posible outcomes and
 * use the MAX of the sum of all 'paths' the agent can take.
 *
 * V0(a=ai|0..i) = max(sum(ri + Vi))
 *
 * Note we are not using any discount factor here.
 * With a discount factor, basically "teach" the agent not to be too greedy,
 * the formula looks like this:
 *
 * V0(a=ai|0..i) = max(sum(ri + sigma*Vi))
 *
 * Richard Bellmann proved that with the "sigma" extension, the agent will receive the
 * best possible outcome. Now, this is the formula for the deterministic agent.
 * To use the agent in a stochastic environment we can adjust the formula a bit and
 * include the probabilities, like so:
 * V0(a=1)=p1(r1+sigma*V1) + p2(r2+sigma*V2) + p3(r3+sigma*V3)
 * given we have a possible three state probability diagram like the abomination below:
 *
 *                      s0
 *                      | a = 1
 *              /p1     |p2     \p3
 *             s1      s2       s3
 *             r1      r2       r3
 *             V1      V2       V3
 *
 * To formalize the above formula we end up with:
 * V0(a) = max(a€A) €s~S[rs,a + sigma*Vs] = max(a€A) SUM(Pa,i->j(rs,a + sigma * Vs))
 *
 * The probability of action a, issued in state i, to finish in state j is defined as
 * Pa, i->j in above formula is our interpretation that the optimal value of the state is
 * equal to the action which gives us the maximum possible expected immediate reward, plus
 * the discounted(sigma) long-term reward for the next state. This definition is recursive.
 * The value of the state is defined via the values of the immediate reachable states.
 */

/**
 * Value of Action:
 *
 * TO make life easier, we define a different quantity from Value of State Vs. We define
 * Value of Action Qs,a (Q of s and a)
 * Qs,a equals the total reward the agent can get by executing Action a in State s and can
 * be defined via Vs.The goal is to get values of Q for every pair of State and Action.
 *
 * Qs,a = SUM(rs,a + sigma*Vs')
 *
 * imagine a finite 4 state environment like this:
 *
 *                   S1
 *                    |
 *               S2- S0 - S4
 *                    |
 *                   S3
 *
 * calculate the values of actions first. S1, S2, S3 and S4 are terminal states with no
 * outbound connection. A simple example to test the intuition.
 *
 * Lets calculate the values for the four states the agent can end up in given the agent can
 * go in any direction but has a 33% chance to deviate to either left or right. That would look
 * like this:
 *
 *               S1
 *               |
 *         S2 --   -- S4
 *               |
 *               S0
 *
 * and given the State values to be
 * V1 = 1, V2 = 2, V3 = 3, V4 = 4
 * then we would end up with the following calculation:
 * for the possibility of the agent going up:
 * Q(S0, up) = 0.33 * V1 + 0.33 * V2 + 0.33 * V4
 * going down is:
 * Q(S0, up) = 0.33 * V3 + 0.33 * V2 + 0.33 * V4
 *
 *
 */

fun runSimpleValueIteration() {

    val sigma = 0.9

    val endResult = (0 until 50).map { i ->
        val result = (sigma.pow(2*i) + 2 * sigma.pow(2*i + 1))
        println("result: $result")
        result
    }.sum()

    println("EndResult: $endResult")

}

fun List<Double>.sum() = this.fold(0.0) { acc: Double, value: Double ->
    acc + value
}



/**
 * we need the following data structures to implement a Value-Iteration algorithm.
 */










































