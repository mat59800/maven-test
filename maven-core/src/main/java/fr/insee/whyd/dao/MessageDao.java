package fr.insee.whyd.dao;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.insee.whyd.model.Message;

public class MessageDao extends AbstractDao<Message> {

  @Override
  String getRelativeFolderPath() {
    return "messages";
  }

  @Override
  ObjectReader getReader() {
    return mapper.readerFor(Message.class);
  }

  @Override
  ObjectWriter getWriter() {
    return mapper.writerFor(Message.class);

  }

}
