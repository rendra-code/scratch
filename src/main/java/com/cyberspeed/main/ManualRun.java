package com.cyberspeed.main;

import static com.cyberspeed.util.JsonUtil.loadConfig;
import static com.cyberspeed.util.JsonUtil.toJson;
import static com.cyberspeed.util.Util.runGame;

import com.cyberspeed.domain.Config;
import com.cyberspeed.domain.Output;
import java.io.IOException;

public class ManualRun {
  public static void main(String[] args) {
    String configFile = "config.json";
    double betAmount = 100;


    try {

      Config config = loadConfig(configFile);
      Output output = new Output();

      runGame(config, betAmount, output);
      System.out.println(toJson(output));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
