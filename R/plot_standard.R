library("plyr")
library("dplyr")
library("ggplot2")

results <- read.csv("results/epsilon_greedy_results.csv", header = F, 
                    colClasses = c("factor", "integer", "integer", "integer", "numeric", "numeric"))
names(results) <- c("Epsilon", "Sim", "T", "ChosenArm", "Reward", "CumulativeReward")

# Plot average reward as a function of time.
stats <- ddply(results,
               c("Epsilon", "T"),
               function (df) {mean(df$Reward)})
ggplot(stats, aes(x = T, y = V1, group = Epsilon, color = Epsilon)) +
  geom_line() +
  ylim(0, 1) +
  xlab("Time") +
  ylab("Average Reward") +
  ggtitle("Performance of the Epsilon Greedy Algorithm")
ggsave("R/graphs/standard_epsilon_greedy_average_reward.pdf")

# Plot frequency of selecting correct arm as a function of time.
# For example, if 2 was the correct arm.
stats <- ddply(results,
               c("Epsilon", "T"),
               function (df) {mean(df$ChosenArm == 2)})
ggplot(stats, aes(x = T, y = V1, group = Epsilon, color = Epsilon)) +
  geom_line() +
  ylim(0, 1) +
  xlab("Time") +
  ylab("Probability of Selecting Best Arm") +
  ggtitle("Accuracy of the Epsilon Greedy Algorithm")
ggsave("r/graphs/standard_epsilon_greedy_average_accuracy.pdf")

# Plot variance of chosen arms as a function of time.
stats <- ddply(results,
               c("Epsilon", "T"),
               function (df) {var(df$ChosenArm)})
ggplot(stats, aes(x = T, y = V1, group = Epsilon, color = Epsilon)) +
  geom_line() +
  xlab("Time") +
  ylab("Variance of Chosen Arm") +
  ggtitle("Variability of the Chosen Arm in the Epsilon Greedy Algorithm")
ggsave("r/graphs/standard_epsilon_greedy_variance_choices.pdf")

# Plot cumulative reward as a function of time.
stats <- ddply(results,
               c("Epsilon", "T"),
               function (df) {mean(df$CumulativeReward)})
ggplot(stats, aes(x = T, y = V1, group = Epsilon, color = Epsilon)) +
  geom_line() +
  xlab("Time") +
  ylab("Cumulative Reward of Chosen Arm") +
  ggtitle("Cumulative Reward of the Epsilon Greedy Algorithm")
ggsave("r/graphs/standard_epsilon_greedy_cumulative_reward.pdf")