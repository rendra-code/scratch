package com.cyberspeed.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class WinCombination {
  @JsonProperty("reward_multiplier")
  private double rewardMultiplier;

  @JsonProperty("when")
  private String whenCondition;

  @JsonProperty("count")
  private Integer count;

  @JsonProperty("group")
  private String group;

  @JsonProperty("covered_areas")
  private List<List<String>> coveredAreas;
}
