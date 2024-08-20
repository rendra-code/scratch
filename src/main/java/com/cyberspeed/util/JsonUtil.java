package com.cyberspeed.util;

import com.cyberspeed.domain.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {

  public static Config loadConfig(String resourceFileName) throws IOException {
    try (InputStream inputStream = JsonUtil.class.getClassLoader().getResourceAsStream(resourceFileName)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("File not found: " + resourceFileName);
      }
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(inputStream, Config.class);
    }
  }

  public static String toJson(Object object) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
