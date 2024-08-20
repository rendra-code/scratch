package com.cyberspeed.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

@Data
public class BonusSymbols {

  @JsonProperty("symbols")
  private Map<String, Integer> symbols;
}
