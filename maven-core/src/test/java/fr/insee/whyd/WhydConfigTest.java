package fr.insee.whyd;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.MapConfiguration;
import org.junit.Test;

public class WhydConfigTest {

  WhydConfig conf = WhydConfig.getInstance();

  @Test
  public void testGetConfig() throws Exception {
    assertThat(conf).isNotNull();
    assertThat(conf.getConfig()).isNotNull().isInstanceOf(Configuration.class);

  }

  @Test
  public void testSetConfig() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("test", "test2");
    Configuration configs = new MapConfiguration(params);
    conf.setConfig(configs);

    assertThat(conf.getConfig().containsKey("test")).isTrue();
  }

}
