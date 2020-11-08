package org.jlr.mab.mutable

import kotlin.random.Random

abstract class BaseEpsilonGreedy(protected val numArms: Int,
                                 protected val epsilon: Float,
                                 protected val random: Random = Random.Default) : MultiArmedBandit {

    protected var selectionCountArms = IntArray(numArms)
    protected var rewardMeans = FloatArray(numArms)

    /**
     * Resets this mutable Epsilon/Greedy MAB instance.
     *
     * The internal metrics will be reset, although the number of arms
     * and epsilon remains the same.
     *
     * The Random instance is not reset and hence different results
     * will be achieved even by passing identical results to this
     * MAB after a [reset].
     */
    override fun reset() {
        selectionCountArms = IntArray(numArms)
        rewardMeans = FloatArray(numArms)
    }

    /**
     * Select an arms on either an exploitation or exploratory basis.
     *
     * The exploratory mode will be selected based on a probability
     * of the provide epsilon parameter.
     *
     * On an exploitation mode, the default arm will be selected when
     * no arms were yet rewarded as the best one.
     *
     * @return The selected arm index.
     */
    override fun selectArm(): Int {
        val flippedCoin = random.nextFloat()
        return if (flippedCoin > epsilon) {
            selectBestArm()
        } else {
            selectRandomArm()
        }
    }

    /**
     * Rewards the selected arm with the provided float.
     *
     * The rewards will be aggregated to previous results
     * based on a running average.
     *
     * @param selectedArm The arm to reward.
     * @param reward The reward to award as a float.
     */
    override fun update(selectedArm: Int, reward: Float) {
        if (selectedArm < 0 || selectedArm >= numArms) {
            throw IllegalArgumentException("The selected arm is outbound to the $numArms registered number of arms.")
        }

        val countArm = selectionCountArms[selectedArm] + 1
        selectionCountArms[selectedArm] = countArm

        val oldReward = rewardMeans[selectedArm]
        val newReward = ((countArm - 1) / countArm.toFloat()) * oldReward + (1 / countArm.toFloat()) * reward
        rewardMeans[selectedArm] = newReward
    }

    /**
     * Should be implemented with the best arm selection algorithm.
     */
    protected abstract fun selectBestArm(): Int

    protected open fun selectRandomArm(): Int {
        return random.nextInt(numArms)
    }

}