package com.cyberspeed;

import static com.cyberspeed.util.JsonUtil.loadConfig;

import com.cyberspeed.domain.Config;
import com.cyberspeed.util.RewardCalculator;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameScenario {

  @Test
  public void testGameScenario1(){
    var betAmount = 100;
    String resourceFileName = "config.json"; // Path to the config file
    Config config = null;
    try {
      config = loadConfig(resourceFileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Map<String, List<String>> filteredWinCombinations = new HashMap<>();
    filteredWinCombinations.put("A", List.of("same_symbol_5_times","same_symbols_vertically"));
    filteredWinCombinations.put("B", List.of("same_symbol_3_times","same_symbols_vertically"));
    List<String> appliedBonusSymbols = List.of("+1000");

    var reward = RewardCalculator.calculateWinCombination(betAmount, filteredWinCombinations, appliedBonusSymbols,
        config);
    reward = RewardCalculator.calculateBonus(reward, appliedBonusSymbols, config);

    Assertions.assertEquals(6600, reward);

  }

  @Test
  public void testGameScenario2() {
    // Test the game scenario
    var betAmount = 100;
    String resourceFileName = "config.json"; // Path to the config file
    Config config = null;
    try {
      config = loadConfig(resourceFileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Map<String, List<String>> filteredWinCombinations = new HashMap<>();
    List<String> appliedBonusSymbols = List.of("5x");

    var reward = RewardCalculator.calculateWinCombination(betAmount, filteredWinCombinations, appliedBonusSymbols,
        config);

    Assertions.assertEquals(0, reward);
  }

  @Test
  public void testGameScenario3() {
    // Test the game scenario
    var betAmount = 100;
    String resourceFileName = "config.json"; // Path to the config file
    Config config = null;
    try {
      config = loadConfig(resourceFileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Map<String, List<String>> filteredWinCombinations = new HashMap<>();
    filteredWinCombinations.put("B", List.of("same_symbol_3_times"));
    List<String> appliedBonusSymbols = List.of("10x");

    var reward = RewardCalculator.calculateWinCombination(betAmount, filteredWinCombinations, appliedBonusSymbols,
        config);
    reward = RewardCalculator.calculateBonus(reward, appliedBonusSymbols, config);

    Assertions.assertEquals(3000, reward);
  }


}
