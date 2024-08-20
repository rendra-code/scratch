package com.cyberspeed.util;

import com.cyberspeed.domain.Config;
import com.cyberspeed.domain.Output;
import com.cyberspeed.domain.Probabilities;
import com.cyberspeed.domain.Symbol;
import com.cyberspeed.domain.SymbolWeight;
import com.cyberspeed.domain.WinCombination;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class Util {

  public static List<SymbolWeight> generateProbabilityPerCoordinate(Probabilities probabilities) {

    List<SymbolWeight> weightedSymbol = new ArrayList<>();

    for (SymbolWeight sym: probabilities.getStandardSymbols()){
      SymbolWeight temp = new SymbolWeight();
      temp.setRow(sym.getRow());
      temp.setColumn(sym.getColumn());
      Map<String, Integer> symbols = new HashMap<>(sym.getSymbols());
      symbols.putAll(probabilities.getBonusSymbols().getSymbols());
      temp.setSymbols(symbols);
      weightedSymbol.add(temp);
    }
    return weightedSymbol;
  }

  public static Map<String,Integer> getSymbolCount(String[][] matrix, Map<String, Symbol> symbols){
    Map<String,Integer> symbolCount = new HashMap<>();
    for (String[] row: matrix) {
      for (String symbol: row) {
        if(isStandardSymbol(symbols, symbol)){
          symbolCount.put(symbol,symbolCount.getOrDefault(symbol, 0)+1);
        }
      }
    }
    return symbolCount;
  }

  public static Map<String,Integer> getBonusSymbolCount(String[][] matrix, Map<String, Symbol> symbols){
    Map<String,Integer> symbolCount = new HashMap<>();
    for (String[] row: matrix) {
      for (String symbol: row) {
        if(isBonusSymbol(symbols, symbol)){
          symbolCount.put(symbol,symbolCount.getOrDefault(symbol, 0)+1);
        }
      }
    }
    return symbolCount;
  }

  private static boolean isStandardSymbol(Map<String, Symbol> symbols, String symbol) {
    return symbols.containsKey(symbol)
        && symbols.get(symbol).getType().equals("standard");
  }

  private static boolean isBonusSymbol(Map<String, Symbol> symbols, String symbol) {
    return symbols.containsKey(symbol)
        && symbols.get(symbol).getType().equals("bonus")
        && !symbols.get(symbol).getImpact().equals("miss");
  }

  public static void appendToAppliedWinCombination(Map<String, List<String>> appliedCombination,
      Entry<String, WinCombination> sameSymbolCondition, String symbol) {
    var list = appliedCombination.getOrDefault(symbol, new ArrayList<>());
    list.add(sameSymbolCondition.getKey());
    appliedCombination.put(symbol, list);
  }

  public static void appendToAppliedWinCombination(Map<String, List<WinCombination>> appliedCombination,
      List<WinCombination> sameSymbolCondition, String symbol) {
    appliedCombination.put(symbol, sameSymbolCondition);
  }

  public static void runGame(Config config, double betAmount, Output output) {
    var weightPerCoordinate = Util.generateProbabilityPerCoordinate(config.getProbabilities());
    var matrix = SymbolGenerator.generateSymbol(weightPerCoordinate, config);

    // calculate reward
    var symbolCount = Util.getSymbolCount(matrix, config.getSymbols());
    var bonusCount = Util.getBonusSymbolCount(matrix, config.getSymbols());

    var appliedWinCombination = WinCombinationChecker.check(matrix, config.getWinCombinations(),symbolCount);
    var filteredWinCombinations = WinCombinationChecker.filterWinCombination(appliedWinCombination,config);
    var appliedBonusSymbols = WinCombinationChecker.checkBonus(bonusCount);

    var reward = RewardCalculator.calculateWinCombination(betAmount, filteredWinCombinations, appliedBonusSymbols,
        config);


    output.setMatrix(matrix);
    output.setReward(reward);
    if (reward > 0){
      reward = RewardCalculator.calculateBonus(reward, appliedBonusSymbols, config);
      output.setAppliedWinCombinations(appliedWinCombination);
      output.setAppliedBonusSymbols(appliedBonusSymbols);
      output.setReward(reward);
     }
  }

  private static void filterWinCombination(Map<String, List<WinCombination>> appliedWinCombination,
      Map<String, List<WinCombination>> filteredWinCombinations) {
    appliedWinCombination.entrySet()
        .forEach(entry -> {
          var winCombinations = getMaxWinCombiPerGroup(entry);
          appendToAppliedWinCombination(filteredWinCombinations, winCombinations, entry.getKey());
        });
  }

  private static List<WinCombination> getMaxWinCombiPerGroup(Entry<String, List<WinCombination>> entry) {
    return entry.getValue().stream()
        .collect(Collectors.groupingBy(WinCombination::getGroup))
        .values()
        .stream()
        .map(combinations -> combinations.stream()
            .max(Comparator.comparingDouble(WinCombination::getRewardMultiplier))
            .orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
