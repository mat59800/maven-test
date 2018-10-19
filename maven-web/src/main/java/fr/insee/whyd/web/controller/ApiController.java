package fr.insee.whyd.web.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import fr.insee.whyd.model.User;
import fr.insee.whyd.services.WhydService;

@Controller
public class ApiController {


  @GetMapping(value = "/api/users", produces = "application/json")
  @ResponseBody
  public List<User> getUsers() {
    return WhydService.getAllUser();

  }
}
