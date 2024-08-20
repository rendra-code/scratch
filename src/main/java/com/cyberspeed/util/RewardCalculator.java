package com.cyberspeed.util;

import com.cyberspeed.domain.Config;
import java.util.List;
import java.util.Map;

public class RewardCalculator {

  public static double calculateWinCombination(double betAmount, Map<String, List<String>> appliedWinCombinations, List<String> appliedBonusSymbols,
      Config config) {
    double reward = 0;
    for (var entry : appliedWinCombinations.entrySet()) {
      var rewardMultiplier = config.getSymbols().get(entry.getKey()).getRewardMultiplier();
      var temp = betAmount * rewardMultiplier;
      for(var win: entry.getValue()){
        var multiplier = config.getWinCombinations().get(win).getRewardMultiplier();
        temp *= multiplier;
      }
      reward += temp;
    }
    return reward;
  }


  public static double calculateBonus(double reward, List<String> appliedBonusSymbols, Config config) {

    for (var bonus : appliedBonusSymbols) {
      var bonusSymbol = config.getSymbols().get(bonus);

      if (bonusSymbol.getImpact().equals("multiply_reward")){
        reward *= bonusSymbol.getRewardMultiplier();
      }

      if(bonusSymbol.getImpact().equals("extra_bonus")){
        reward += bonusSymbol.getExtra();
      }

    }
    return reward;
  }
}
