package com.cyberspeed.util;

import static com.cyberspeed.util.Util.appendToAppliedWinCombination;

import com.cyberspeed.domain.Config;
import com.cyberspeed.domain.WinCombination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WinCombinationChecker {

  public static Map<String, List<String>> check(String[][] matrix,
      Map<String, WinCombination> winCombinations,
      Map<String,Integer> symbolCount) {

    Map<String,List<String>> appliedCombination = new HashMap<>();

    evaluateSameSymbolConditions(winCombinations, symbolCount, appliedCombination);
    evaluateLinearSymbolConditions(winCombinations, matrix, symbolCount, appliedCombination);

    return appliedCombination;
  }

  private static void evaluateSameSymbolConditions(Map<String, WinCombination> winCombinations,
      Map<String, Integer> symbolCount, Map<String, List<String>> appliedCombination) {
    winCombinations.entrySet().stream()
        .filter(entry -> entry.getValue().getWhenCondition().equals("same_symbols"))
        .forEach(sameSymbolCondition -> {
          for(String symbol: symbolCount.keySet()){
            if(symbolCount.get(symbol) >= sameSymbolCondition.getValue().getCount()){
              appendToAppliedWinCombination(appliedCombination, sameSymbolCondition, symbol);
            }
          }
        });
  }

  private static void evaluateLinearSymbolConditions(Map<String, WinCombination> winCombinations,
      String[][] output, Map<String, Integer> symbolCount,
      Map<String, List<String>> appliedCombination) {
    winCombinations.entrySet().stream()
        .filter(entry -> entry.getValue().getWhenCondition().equals("linear_symbols"))
        .forEach(linearSymbol -> {
          for(String s : symbolCount.keySet()){
            if (isInCoveredArea(output, s , linearSymbol)){
              appendToAppliedWinCombination(appliedCombination, linearSymbol, s);
            }
          }
        });
  }

  private static boolean isInCoveredArea(String[][] output, String symbol ,
      Entry<String, WinCombination> linearSymbol) {
    for(var coveredArea: linearSymbol.getValue().getCoveredAreas()){
      for(String coordinate : coveredArea){
        String[] coordinates = coordinate.split(":");
        int row = Integer.parseInt(coordinates[0]);
        int column = Integer.parseInt(coordinates[1]);
        if(output[row][column] == null || !output[row][column].equals(symbol)){
          return false;
        }
      }
    }
    return true;
  }

  public static List<String> checkBonus(Map<String, Integer> bonusCount) {
    return bonusCount.entrySet().stream()
        .map(Entry::getKey)
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
  }

  public static Map<String, List<String>> filterWinCombination(Map<String, List<String>> appliedWinCombination, Config config) {
    HashMap<String, Double> maxMultiplier = new HashMap<>();
    Map<String, List<String>> maxCombinations = new HashMap<>();
    for(var entry : appliedWinCombination.entrySet()){
      List<String> maxCombination = new ArrayList<>();
      for(var winCombination : entry.getValue()){
        String group = config.getWinCombinations().get(winCombination).getGroup();
        double configMultiplier = config.getWinCombinations().get(winCombination).getRewardMultiplier();
        double multiplier = maxMultiplier.getOrDefault(group,0.0);
        if (configMultiplier > multiplier){
          maxCombination.add(winCombination);
          maxMultiplier.put(group,configMultiplier);
        }
      }
      maxCombinations.put(entry.getKey(), maxCombination);
    }
    return maxCombinations;
  }
}
