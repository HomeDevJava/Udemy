package com.cursospring.app.auth.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		SessionFlashMapManager flashManager = new SessionFlashMapManager();
		FlashMap flashMap = new FlashMap();

		flashMap.put("success", "Hola," + authentication.getName() + " Has iniciado sesión con Exito!");
		flashManager.saveOutputFlashMap(flashMap, request, response);

		if (authentication != null) {
			logger.info("El Usuario" + authentication.getName() + " Ha iniciado sesion con exito");
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}
}