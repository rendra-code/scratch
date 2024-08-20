package com.cyberspeed.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

@Data
public class SymbolWeight {
  @JsonProperty("column")
  private int column;

  @JsonProperty("row")
  private int row;

  @JsonProperty("symbols")
  private Map<String, Integer> symbols;
}
