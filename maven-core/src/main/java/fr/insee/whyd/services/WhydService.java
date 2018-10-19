package fr.insee.whyd.services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import fr.insee.whyd.WhydConfig;
import fr.insee.whyd.dao.MessageDao;
import fr.insee.whyd.dao.UserDao;
import fr.insee.whyd.model.Message;
import fr.insee.whyd.model.User;

public class WhydService {
  private static UserDao userDao = new UserDao();
  private static MessageDao messageDao = new MessageDao();

  public static Optional<User> findUserByMail(String email) {
    return userDao.getAll().stream().filter(u -> email.equals(u.getEmail())).findFirst();
  }

  public static void createUser(User u) {
    userDao.createOrUpdate(u.getUsername(), u);
  }

  public static List<User> getAllUser() {
    return userDao.getAll();
  }

  public static List<Message> getAllMessagesForUser(User user) {
    return messageDao.getAll().stream()
        .filter(message -> message.getAuthor().equals(user) || !message.isPrivate()
            || user.equals(message.getRecipient()))
        .sorted(Comparator.comparing(Message::getCreationDate).reversed())
        .collect(Collectors.toList());
  }


  public static void addMessage(Message m) {
    m.setId(UUID.randomUUID().toString());
    messageDao.createOrUpdate(m.getId(), m);
  }

  public static void exportAll(OutputStream outputStream) {
    try {
      zip(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"), outputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private static void zip(String sourceDirPath, OutputStream os) throws IOException {
    try (ZipOutputStream zs = new ZipOutputStream(os)) {
      Path pp = Paths.get(sourceDirPath);
      Files.walk(pp).filter(path -> !Files.isDirectory(path)).forEach(path -> {
        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
        try {
          zs.putNextEntry(zipEntry);
          Files.copy(path, zs);
          zs.closeEntry();
        } catch (IOException e) {
          System.err.println(e);
        }
      });
    }
  }

}
