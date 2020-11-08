package org.jlr.mab.mutable

import org.jlr.maxValueIndex
import kotlin.random.Random

/**
 * This Epsilon Greedy algorithm variation will lower its exploration phase
 * as the rewards are getting bigger for arms.
 *
 * Don't use this algorithm, it performs poorly and is used as a test.
 *
 * The Softmax algorithm is a better choice that attemps the same
 * much better.
 */
class AnnealingEpsilonGreedy(numArms: Int, epsilon: Float, random: Random = Random.Default)
    : EarlyEpsilonGreedy(numArms, epsilon, random) {

    override fun selectArm(): Int {
        val flippedCoin = random.nextFloat()
        return if (flippedCoin > getAnnealingEpsilon()) {
            selectBestArm()
        } else {
            selectRandomArm()
        }
    }

    // Annealed Epsilon = ( Total Arms - Strongest Arm ) / Total Arms * Initial Epsilon
    private fun getAnnealingEpsilon(): Float {
        val bestId = maxValueIndex(rewardMeans) ?: return epsilon
        val bestReward = rewardMeans[bestId]
        val totalRewards = rewardMeans.sum()

        return if (totalRewards == bestReward) {
            epsilon
        } else {
            return ((totalRewards - bestReward) / totalRewards) * epsilon
        }
    }

}