package fr.insee.whyd.web.interceptor;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import fr.insee.whyd.WhydConfig;
import fr.insee.whyd.model.User;
import fr.insee.whyd.services.WhydService;

public class UserInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (request.getUserPrincipal() != null) {
      request.setAttribute("authenticated", true);
      Optional<User> ou = WhydService.findUserByMail(request.getUserPrincipal().getName());
      User u;
      if (!ou.isPresent()) {
        KeycloakSecurityContext securityContext =
            (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        AccessToken token = securityContext.getToken();

        u = new User();
        u.setEmail(token.getEmail());
        u.setNom(token.getName());
        u.setUsername(token.getPreferredUsername());
        WhydService.createUser(u);
      } else {
        u = ou.get();
      }
      request.setAttribute("user", u);
      MDC.put("user", u.getUsername());
    } else {
      request.setAttribute("authenticated", false);

    }
    request.setAttribute("version",
        WhydConfig.getInstance().getConfig().getString("whyd.version", "none"));
    return true;
  }



}
