package com.cyberspeed.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class Probabilities {
  @JsonProperty("standard_symbols")
  private List<SymbolWeight> standardSymbols;

  @JsonProperty("bonus_symbols")
  private BonusSymbols bonusSymbols;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(SymbolWeight standardSymbol : standardSymbols) {
      sb.append("column:"+standardSymbol.getColumn());
      sb.append(" row:"+standardSymbol.getRow());
      sb.append('\n');
      for(String symbol : standardSymbol.getSymbols().keySet()) {
        sb.append("symbol:"+symbol);
        sb.append(" probability:"+standardSymbol.getSymbols().get(symbol));
        sb.append('\n');
      }
    }
    return sb.toString();
  }
}
