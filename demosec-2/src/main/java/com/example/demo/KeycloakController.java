package com.example.demo;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class KeycloakController {

	@Autowired
	private KeycloakClientRequestFactory keycloakClientRequestFactory;
	
    @GetMapping("/login")
    public String login() {
        return "Welcome to login screen";
    }

    @GetMapping("/homepage")
    public String homepage(Principal principal) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = token.getAccount().getKeycloakSecurityContext().getToken();
       
        //&redirect_uri=http%3A%2F%2Flocalhost%3A8082%2Fsso%2Flogin&state=d236b9a2-9071-472a-9067-26999b513903&login=true&scope=openid
        return "Welcome to homepage, " +accessToken.getPreferredUsername()+" successfully logged in"+"   <a href='/logout'>logout</a>";
    }

    
    
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request,Principal principal) throws IOException {
//    	KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) principal;
//        AccessToken accessToken = token.getAccount().getKeycloakSecurityContext().getToken();
//       
//       // String referer = request.getHeader("Referer");
//        request.getSession().invalidate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken.getAccessTokenHash());
//        //HttpEntity<Void> entity = new HttpEntity<>(headers);
//        URI uri=URI.create("http://localhost:8080/auth/realms/SpringBootKeycloak/protocol/openid-connect/logout");
//		keycloakClientRequestFactory.createRequest(uri, HttpMethod.GET).execute();
//        return "redirect:" ;
//    }
    
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse rep)  {
        
        request.getSession().invalidate();
        try {
			request.logout();
			rep.sendRedirect("/abc");
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
}