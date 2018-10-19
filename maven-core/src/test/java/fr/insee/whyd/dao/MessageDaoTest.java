package fr.insee.whyd.dao;

import static org.assertj.core.api.Assertions.assertThat;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.insee.whyd.WhydConfig;
import fr.insee.whyd.dao.MessageDao;
import fr.insee.whyd.model.Message;
import fr.insee.whyd.model.User;

public class MessageDaoTest {

  MessageDao messageDao = new MessageDao();

  Message testMessage = new Message();
  User testUser = new User();

  @Before
  public void setup() {
    testUser.setUsername("testUser");
    testUser.setEmail("test@test.fr");
    testUser.setNom("Testy Test");
    testMessage.setAuthor(testUser);
    testMessage.setContent("blahblah");
  }

  @Test
  public void testGetRelativeFolderPath() throws Exception {
    assertThat(messageDao.getRelativeFolderPath()).isEqualTo("messages");
  }

  @Test
  public void testGetReader() throws Exception {
    assertThat(messageDao.getReader()).isInstanceOf(ObjectReader.class);
  }

  @Test
  public void testGetWriter() throws Exception {
    assertThat(messageDao.getWriter()).isInstanceOf(ObjectWriter.class);
  }

  @Test
  public void testGetById() throws Exception {
    messageDao.createOrUpdate("testGetById", testMessage);
    assertThat(messageDao.getById("testGetById")).isEqualTo(testMessage);
  }

  @Test
  public void testCreateOrUpdate() throws Exception {
    assertThat(messageDao.createOrUpdate("testCreateOrUpdate", testMessage)).isEqualTo(testMessage);
    assertThat(Paths.get(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"))
        .resolve(messageDao.getRelativeFolderPath()).toFile().list()).isNotEmpty()
            .contains("testCreateOrUpdate");
  }

  @Test
  public void testDelete() throws Exception {
    messageDao.createOrUpdate("testDelete", testMessage);
    messageDao.delete("testDelete");
    assertThat(Paths.get(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"))
        .resolve(messageDao.getRelativeFolderPath()).resolve("testDelete").toFile().exists())
            .isFalse();


  }

  @Test
  public void testGetAll() throws Exception {
    Arrays
        .asList(Paths.get(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"))
            .resolve(messageDao.getRelativeFolderPath()).toFile().listFiles())
        .stream().forEach(f -> f.delete());
    messageDao.createOrUpdate("test1", testMessage);
    messageDao.createOrUpdate("test2", testMessage);
    messageDao.createOrUpdate("test3", testMessage);
    messageDao.createOrUpdate("test4", testMessage);

    assertThat(messageDao.getAll()).size().isEqualTo(4);
  }

}
