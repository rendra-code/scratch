package com.cyberspeed.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Output {
  @JsonProperty("matrix")
  private String[][] matrix;

  @JsonProperty("reward")
  private double reward;

  @JsonInclude(Include.NON_EMPTY)
  @JsonProperty("applied_winning_combinations")
  private Map<String, List<String>> appliedWinCombinations;

  @JsonInclude(Include.NON_EMPTY)
  @JsonProperty("applied_bonus_symbol")
  private List<String> appliedBonusSymbols;
}
