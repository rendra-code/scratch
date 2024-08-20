package com.cyberspeed.util;

import com.cyberspeed.domain.Config;
import com.cyberspeed.domain.SymbolWeight;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SymbolGenerator {

  public static String[][] generateSymbol(List<SymbolWeight> probs, Config config) {
    String[][] matrix = new String[config.getRows()][config.getColumns()];

    for(var p : probs){
      matrix[p.getRow()][p.getColumn()] = generateSymbol(p.getSymbols());
    }

    return matrix;
  }

  private static String generateSymbol(Map<String, Integer> symbols) {
    var totalWeight = 0;
    for(var entry : symbols.entrySet()){
      totalWeight += entry.getValue();
    }
    Random random = new Random();
    int randomValue = random.nextInt(totalWeight);

    for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
      randomValue -= entry.getValue();
      if (randomValue <= 0) {
        return entry.getKey();
      }
    }

    return "A";
  }
}
