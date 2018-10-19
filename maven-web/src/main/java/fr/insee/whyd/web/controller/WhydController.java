package fr.insee.whyd.web.controller;


import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import fr.insee.whyd.model.Message;
import fr.insee.whyd.model.User;
import fr.insee.whyd.services.WhydService;

@Controller
public class WhydController {

  Logger logger = LoggerFactory.getLogger(WhydController.class);

  @GetMapping("/")
  public String welcome(ModelMap model,
      @RequestAttribute(required = false, name = "user") User user) {
    logger.info("Welcome " + user.getUsername());

    model.addAttribute("messages", WhydService.getAllMessagesForUser(user));
    model.addAttribute("message", new Message());
    return "welcome";
  }


  @PostMapping("/message")
  public String postMessage(@RequestAttribute(required = false) User user,
      @ModelAttribute Message message) {

    message.setAuthor(user);
    message.setCreationDate(LocalDateTime.now());
    WhydService.addMessage(message);
    return "redirect:/";
  }
}
