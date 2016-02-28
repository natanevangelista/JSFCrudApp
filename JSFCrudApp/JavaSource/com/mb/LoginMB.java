package com.mb;

import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.facade.UserFacade;
import com.model.User;
import com.util.JSFMessageUtil;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractMB {
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		UserFacade userFacade = new UserFacade();

		User user = userFacade.isValidLogin(email, password);
		
		if(user != null){
			userMB.setUser(user);
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			return "/pages/protected/index.xhtml";
		}

		//Recuperando as Mensagens da Base. >>>>> Utilizar o modelo de Classe MensagemUtil.java
		
		displayErrorMessageToUser(JSFMessageUtil.getMensagem("errorLogin"));
		
		return null;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}	
}