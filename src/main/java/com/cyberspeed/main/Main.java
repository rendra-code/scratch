package com.cyberspeed.main;

import static com.cyberspeed.util.JsonUtil.loadConfig;
import static com.cyberspeed.util.JsonUtil.toJson;
import static com.cyberspeed.util.Util.runGame;

import com.cyberspeed.domain.Config;
import com.cyberspeed.domain.Output;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage:   java -jar <your-jar-file> --config <config-file> --betting-amount <amount>");
      System.exit(1);
    }

    String configFile = "config.json";
    double betAmount = 0;

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--config")) {
        configFile = args[i + 1];
        i++;
      } else if (args[i].equals("--betting-amount")) {
        betAmount = Double.parseDouble(args[i + 1]);
        i++;
      }
    }

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
