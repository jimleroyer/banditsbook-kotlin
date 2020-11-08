package org.jlr.montecarlo

import kotlin.random.Random

/**
 * Utilities around data generation necessary for Monte Carlo runs.
 */
object Generators {

    fun genMeansCompetingWinners(): List<Float> {
        val means = arrayListOf(0.1f, 0.1f, 0.1f, 0.4f, 0.5f)
        means.shuffle()
        return means
    }

    fun genMeansRandom(threshold: Float, numCompetitors: Int): List<Float> {
        return (0 until numCompetitors).map { Random.nextFloat() * threshold }
    }

    fun genMeansSingleWinner(): List<Float> {
        val means = arrayListOf(0.1f, 0.1f, 0.1f, 0.1f, 0.9f)
        means.shuffle()
        return means
    }

}