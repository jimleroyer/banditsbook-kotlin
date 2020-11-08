package org.jlr.montecarlo

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.jlr.Tuple6
import org.jlr.mab.mutable.BernouilliArm
import org.jlr.mab.mutable.EarlyEpsilonGreedy
import org.jlr.mab.mutable.MultiArmedBandit
import org.jlr.mab.mutable.Softmax
import org.jlr.maxValueIndex
import org.jlr.montecarlo.Generators.genMeansSingleWinner
import org.jlr.plus

typealias ResultLine = Tuple6<Float, Int, Int, Int, Float, Float>

typealias TestLine = ArrayList<Any>
typealias TestWriter = (TestLine) -> Unit

fun main() {
//    monteCarloEpsilon()
    monteCarloSoftmax()
}

private fun monteCarloEpsilon() {
    val means = genMeansSingleWinner()
    val arms = means.map { BernouilliArm(it) }.toTypedArray()
    println("Best arm is ${maxValueIndex(means)}")
    println("Running Epsilon/Greedy MAB Monte Carlo simulation...")

    val fileName = "results/epsilon_greedy_results.csv"
    csvWriter().open(fileName) {
        for (modifier in arrayOf(0.1f, 0.2f, 0.3f, 0.4f, 0.5f)) {
            val mabFactory = { EarlyEpsilonGreedy(arms.size, modifier) }
            val writer: TestWriter = { line ->
                val row = modifier + line
                writeRow(row)
            }
            val monteCarlo = MabMonteCarlo(mabFactory, arms, writer)
            monteCarlo.testMab(5000, 250)
        }
    }

    println("Saved results into file $fileName")
}

private fun monteCarloSoftmax() {
    val means = genMeansSingleWinner()
    val arms = means.map { BernouilliArm(it) }.toTypedArray()
    println("Best arm is ${maxValueIndex(means)}")
    println("Running Softmax MAB Monte Carlo simulation...")

    val fileName = "results/softmax_results.csv"
    csvWriter().open(fileName) {
        for (temperature in arrayOf(0.1f, 0.2f, 0.3f, 0.4f, 0.5f)) {
            val mabFactory = { Softmax(arms.size, temperature) }
            val writer: TestWriter = { line ->
                writeRow(temperature + line)
            }
            val monteCarlo = MabMonteCarlo(mabFactory, arms, writer)
            monteCarlo.testMab(5000, 250)
        }
    }

    println("Saved results into file $fileName")
}

class MabMonteCarlo(private val mabFactory: () -> MultiArmedBandit,
                    private val arms: Array<BernouilliArm>,
                    private val writer: TestWriter) {

    fun testMab(numSims: Int, horizon: Int): Unit {
        val cumRewards = arrayListOf<Float>()
        for (sim in 0 until numSims) {
            val mab = mabFactory()
            for (t in 0 until horizon) {
                val chosenArm = mab.selectArm()
                val reward = arms[chosenArm].draw()

                if (t == 0) {
                    cumRewards.add(reward)
                } else {
                    cumRewards.add(cumRewards.last() + reward)
                }

                mab.update(chosenArm, reward)
                val testParams: ArrayList<Any> = arrayListOf(sim + 1, t + 1, chosenArm, reward, cumRewards.last())
                writer(testParams)
            }
        }
    }

}
