package org.jlr.mab.mutable

import org.jlr.maxValueIndex
import kotlin.random.Random

/**
 * This Epsilon Greedy algorithm variation will start exploration earlier than
 * its usual counterpart.
 *
 * In exploitation phase, when there are no arms yet rewarded or at the same
 * rewards, a random arm will get selected in order to increase rewards with
 * more exploration.
 *
 * Use this algorithm when all arms would stand a likely chance to win and
 * no bias for a control option is necessary.
 */
open class EarlyEpsilonGreedy(numArms: Int, epsilon: Float, random: Random = Random.Default)
    : BaseEpsilonGreedy(numArms, epsilon, random) {

    /**
     * Returns the index of the best arm.
     *
     * If not found, will rely back on random selection.
     */
    override fun selectBestArm(): Int {
        return maxValueIndex(rewardMeans) ?: selectRandomArm()
    }

}