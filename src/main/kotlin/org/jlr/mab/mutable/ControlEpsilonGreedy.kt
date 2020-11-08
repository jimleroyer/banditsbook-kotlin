package org.jlr.mab.mutable

import org.jlr.nonNullMaxValueIndex
import kotlin.random.Random

/**
 * Classic implementation of the Epsilon Greedy algorithm.
 *
 * In exploitation phase, when there are no arms yet rewarded or at the same
 * rewards, the first arm will get selected as a default.
 *
 * Use this algorithm when a control option should be biased as arm 0, so that
 * no exploration shakes too much previous behavior.
 */
class ControlEpsilonGreedy(numArms: Int, epsilon: Float, random: Random = Random.Default)
    : BaseEpsilonGreedy(numArms, epsilon, random) {

    /**
     * Returns the index of the best arm.
     *
     * if not found, defaults to 0.
     */
    override fun selectBestArm(): Int {
        return nonNullMaxValueIndex(rewardMeans, 0)
    }

}