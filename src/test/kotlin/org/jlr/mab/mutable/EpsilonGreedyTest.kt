package org.jlr.mab.mutable

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.floats.shouldBeLessThan


class EpsilonGreedyTest : BehaviorSpec({

    // We assume the MAB selects the arm 0 by default,
    // hence we can test the exploitation / exploration probability.
    given("an epsilon-greedy MAB with epsilon 20%") {
        val epsilon = 0.2f
        var cntDefaultArm = 0
        var cntAlternativeArm = 0
        val mab = ControlEpsilonGreedy(2, epsilon)

        `when`("running enough iterations of the MAB") {
            for (i in 0..ITER_CNT) {
                val selectedArm = mab.selectArm()
                if (selectedArm == 0) cntDefaultArm++
                else cntAlternativeArm++
            }

            then("the non-default arm should be explored at least 10% of the time") {
                val pctDefaultArm = cntDefaultArm / ITER_CNT.toFloat();
                val pctAlternativeArm = cntAlternativeArm / ITER_CNT.toFloat();
                val pctExploration: Float = epsilon / 2.toFloat() * 0.90f

                pctAlternativeArm.shouldBeGreaterThan(pctExploration)
                pctDefaultArm.shouldBeGreaterThan(epsilon + (1 - epsilon) / 2.toFloat() * 0.9f)
            }
        }
    }

    // We assume the MAB selects the arm 0 by default,
    // hence we can test the exploitation / exploration probability.
    given("an epsilon-greedy MAB with epsilon 0%") {
        val epsilon = 0.0f
        var cntDefaultArm = 0
        var cntAlternativeArm = 0
        val mab = ControlEpsilonGreedy(2, epsilon)

        `when`("running enough iterations of the MAB") {
            for (i in 0..ITER_CNT) {
                val selectedArm = mab.selectArm()
                if (selectedArm == 0) cntDefaultArm++
                else cntAlternativeArm++
            }

            then("the best arm should be selected ALL the time") {
                val pctDefaultArm = cntDefaultArm / ITER_CNT.toFloat();
                val pctAlternativeArm = cntAlternativeArm / ITER_CNT.toFloat();
                pctAlternativeArm.shouldBeLessThan(0.0001f)
                pctDefaultArm.shouldBeGreaterThan(0.9999f)
            }
        }
    }

}) {
    companion object Config {
        const val ITER_CNT = 10_000
    }
}
