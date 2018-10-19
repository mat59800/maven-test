package fr.insee.whyd.web.converters;

import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import fr.insee.whyd.model.User;
import fr.insee.whyd.services.WhydService;

@Component
public class UserConverter implements Converter<String, User> {


  @Override
  public User convert(String email) {
    LoggerFactory.getLogger(UserConverter.class).info("Converting " + email);
    Optional<User> ou = WhydService.findUserByMail(email);
    return ou.isPresent() ? ou.get() : null;
  }

}
