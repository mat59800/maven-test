package fr.insee.whyd.dao;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.insee.whyd.model.User;

public class UserDao extends AbstractDao<User> {

  @Override
  String getRelativeFolderPath() {
    return "users";
  }

  @Override
  ObjectReader getReader() {
    return mapper.readerFor(User.class);
  }

  @Override
  ObjectWriter getWriter() {
    return mapper.writerFor(User.class);
  }

}
