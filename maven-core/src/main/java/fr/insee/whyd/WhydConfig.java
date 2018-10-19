package fr.insee.whyd;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class WhydConfig {

  private Configuration config;
  private static WhydConfig instance;


  private WhydConfig() {

  }


  public static synchronized WhydConfig getInstance() {
    if (instance == null) {
      instance = new WhydConfig();
      Configurations configs = new Configurations();
      try {
        instance.setConfig(configs.properties(WhydConfig.class.getResource("/whyd.properties")));
      } catch (ConfigurationException e) {
        throw new RuntimeException("We can't load the configuration", e);
      }
    }
    return instance;
  }


  public Configuration getConfig() {
    return config;
  }


  public void setConfig(Configuration config) {
    this.config = config;
  }

}
