package com.example.myapplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.events.ApplicationEvent.BrowserResizeEvent;
import com.evolucao.rmlibrary.events.ApplicationEvent.CloseOpenWindowsEvent;
import com.evolucao.rmlibrary.events.ApplicationEventBus;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationNavigator;
import com.evolucao.weblibrary.ApplicationUI;
import com.evolucao.weblibrary.view.FooterColumnsView;
import com.evolucao.weblibrary.view.HeaderBar;
import com.evolucao.weblibrary.view.LoginLogoutBar;
import com.evolucao.weblibrary.view.LoginView;
import com.evolucao.weblibrary.view.MegaMenu;
import com.evolucao.weblibrary.view.SystemView;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import net.sf.dynamicreports.examples.adhoc.TesteColumnGridReport;
import net.sf.dynamicreports.examples.adhoc.TesteSubReport;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends ApplicationUI {
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    	@Override
    	protected void servletInitialized() throws ServletException {
    		FileInputStream d;
    		
    		super.servletInitialized();
    		
    		getService().addSessionInitListener(new SessionInitListener() {
    			@Override
    			public void sessionInit(SessionInitEvent event) throws ServiceException {
    				event.getSession().addBootstrapListener(new BootstrapListener() {
    	               @Override
    	               public void modifyBootstrapPage(BootstrapPageResponse response) {
    	                  // With this code, Vaadin servlet will add the line:
    	                  //
    	                  // <script type="text/javascript" 
    	                  // src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" />
    	                  //
    	                  // as the first line inside the document's head tag in the generated html document
    	                  //response.getDocument().head().prependElement("script").attr("type", "text/javascript").attr("src", "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js");
    	                  
    	                  //String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
    	                  //response.getDocument().head().prependElement("script").attr("type", "text/javascript").attr("src", contextPath + "/VAADIN/themes/mytheme/js/jquery/1.12.4/jquery.min.js");
    	                  //response.getDocument().head().prependElement("script").attr("type", "text/javascript").attr("src", contextPath + "/VAADIN/themes/mytheme/js/inputmask/jquery.inputmask.bundle.js");
    	                  //response.getDocument().head().prependElement("script").attr("type", "text/javascript").attr("src", contextPath + "/VAADIN/themes/mytheme/js/inputmask/jquery.inputmask.bundle2.js");
    	   
    	            	   //final Element head = response.getDocument().head();
    	            	   /*
    	            	   response.getDocument().head().prependElement("script").attr("type", "text/javascript").attr("src", "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js");
    	            	   response.getDocument().head().appendElement("meta").attr("name", "viewport").attr("content", "width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no");
    	            	   response.getDocument().head().appendElement("meta").attr("name", "apple-mobile-web-app-capable").attr("content", "yes");
    	            	   response.getDocument().head().appendElement("meta").attr("name", "apple-mobile-web-app-status-bar-style").attr("content", "black-translucent");

    	                   String contextPath = response.getRequest().getContextPath();
    	                   response.getDocument().head().appendElement("link").attr("rel", "apple-touch-icon").attr("href", contextPath + "/VAADIN/themes/dashboard/img/app-icon.png");
    	                   */
    	            	   
    	            	   //response.getDocument().head().appendElement("meta").attr("http-equiv", "pragma").attr("content", "no-cache");
    	            	   
    	            	   //response.getDocument().head().setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    	            	   //response.getDocument().head().setHeader("Pragma", "no-cache"); // HTTP 1.0.
    	            	   //response.setHeader("Expires", "0"); // Proxies.
    	            	   
    	            	   response.getDocument().head().appendElement("meta").attr("ramatis", "no-cache, no-store, must-revalidate");
    	               }

    	               @Override
    	               public void modifyBootstrapFragment(BootstrapFragmentResponse response) {}
    				});
    			}
    		});
    	}    	
    }
    
	@Override   
	protected void init(VaadinRequest request) {
		super.init(request);

		//AdhocCustomizerReport teste = new AdhocCustomizerReport();
		//TesteSubReport teste = new TesteSubReport();
		//TesteColumnGridReport teste2 = new TesteColumnGridReport();

		database = Utils.createDatabaseConnection();
		
		//database = new Database("127.0.0.1", "prosaude", "usuario", "rataplan");
		this.setProjectName("clinica");
		
		//Database database = (Database) VaadinSession.getCurrent().getAttribute("database");
		DatabaseApp databaseApp = new DatabaseApp(database);

		String conteudo = "Ramatis Soares de Melo";
		String campo = conteudo.substring(conteudo.indexOf("Soares"), conteudo.indexOf("de")-2);
		
        // Apresenta a url que esta chamando a aplicacao
        //VaadinServletRequest vaadinServletRequest = null;
        //VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
        //HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();
        //String requestUrl = httpServletRequest.getRequestURL().toString();
        //System.out.println(requestUrl);
        //Utils.ShowMessageWindow("UrlAplicacao", requestUrl, 400, 200, MessageWindowType.INFO);

        // Apresenta o contexto da aplicacao
        //String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
        //System.out.println("CONTEXT: " + contextPath);
        //Utils.ShowMessageWindow("Context:", contextPath, 400, 200, MessageWindowType.INFO);
        
        //System.out.println(VaadinService.getCurrent().getBaseDirectory().getPath());
		//Utils.addJavascripts(true, "https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js");
        
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/jquery.inputmask.bundle.min.js");
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/inputmask.date.extensions.min.js");
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/inputmask.numeric.extensions.min.js");
		
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/inputmask.js");
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/inputmask.date.extensions.js");
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/inputmask.numeric.extensions.js");

		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/jquery/1.12.4/jquery.min.js");
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/jquery.inputmask.bundle.js");

		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/jquery/1.12.4/jquery.min.js");
		//Utils.addJavascripts(true, contextPath + "/VAADIN/themes/mytheme/js/inputmask/jquery.inputmask.bundle.js");
		
		Utils.addJavascripts(true, "http://206.130.120.205:8090/php-app/download/jquery.min.js");
		Utils.addJavascripts(true, "http://206.130.120.205:8090/php-app/download/jquery.inputmask.bundle.js");
		
		//this.Instance = this;

		ApplicationEventBus.register(this);
		Responsive.makeResponsive(this);
		this.getPage().setTitle("Sociedade PRÓ-SAÚDE.");
		
		// Carrega valores padroes para filtragem de produtos
		/*
		filtroPesquisa.setValue("ufestado", "AM");
		filtroPesquisa.setValue("cidade", "Manaus");
		filtroPesquisa.setValue("bairro", "");
		filtroPesquisa.setValue("uidespe", "");
		filtroPesquisa.setValue("tipoespe", 1);
		*/

		System.out.println(Utils.getJustDate(new Date()));
		System.out.println(new Date());
		
		Atualizacao.execute();
		
		updateContent();
		
        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
        	@Override
        	public void browserWindowResized(final BrowserWindowResizeEvent event) {
        		ApplicationEventBus.post(new BrowserResizeEvent());
        		updateContent();
        	}
        });
        
        VaadinSession.getCurrent().addRequestHandler(new RequestHandler() {
			@Override
			public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) throws IOException {
				System.out.println(request.getPathInfo());
				if (request.getPathInfo().contains(".js")) {
					System.out.println("Solicitou js");
				}

				if (request.getPathInfo().contains("/postData")) {
					System.out.println("Processamento detectado");
					return true;
				}

				if (request.getPathInfo().contains("file=")) {
        			// if you want to use some parameter in your application
        			String param = request.getParameter("file");
        			String filePath = "c:\\temp-2018\\report-teste.pdf";
        			String fileName = "report-teste.pdf";
        			response.setHeader("Expires", "0");
        			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        			response.setHeader("Pragma", "public");
        			response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        			response.setContentType("application/pdf");
        			OutputStream os = response.getOutputStream();
        			byte[] buffer = new byte[1024];
        			InputStream is = new FileInputStream(filePath);
        			int len;
        			while ((len = is.read(buffer)) > 0) {
        				os.write(buffer, 0, len);
        			}
        			is.close();
        			os.flush();
        			os.close();
        			return true;
       			} else
       				return false;
			}
        });
	}
	
	public void updateContent() {
		boolean requestLogin = true;

		//this.login = (SimpleRecord) VaadinSession.getCurrent().getAttribute("login");
		
		/*
		String login = Utils.getCookieString("login", null);
		
		if (login!=null) {
			String conteudo = Utils.getCookieString("login");
			this.login = SimpleRecord.decode(conteudo);
		}
		*/
		
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		if ((ui.login!=null) && (ui.login.fieldExists("uid"))) {
			//Database database = (Database) VaadinSession.getCurrent().getAttribute("database");
			Table tblSysUsers = null;
			try {
				tblSysUsers = database.loadTableByName("sysusers");
				
				database.openConnection();
				tblSysUsers.select("passport");
				tblSysUsers.setFilter("uid", ui.login.getString("uid"));
				tblSysUsers.loadData();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				database.closeConnection();
			}
			
			if ((tblSysUsers!=null) && (tblSysUsers.getString("passport").equals(ui.login.getString("passport")))) {
				requestLogin=false;
			}
		}
		
		if (requestLogin) {
			setContent(loginView());
		}
		else {
			ui.setSystemView(new SystemView());
			setContent(ui.getSystemView());
		}
		//setContent(WebApp());
		
		//getNavigator().navigateTo(getNavigator().getState());
    	/*
        User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
        if (user != null && "admin".equals(user.getRole())) {
            // Authenticated user
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            setContent(new LoginView());
            addStyleName("loginview");
        }
        */
    	
    	/*
    	VerticalLayout mainView = new MainView();
    	this.main=mainView;
    	setContent(mainView);
        getNavigator().navigateTo(getNavigator().getState());
        */
    	
    	/*
    	Styles styles = Page.getCurrent().getStyles();
    	styles.add(".main-content {margin: 10px}");
    	
		final VerticalLayout layout = new VerticalLayout();
		layout.addStyleName("main-content");
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		layout.addComponent(button);
		*/
	}
	
	public VerticalLayout WebApp() {
		VerticalLayout mainContainer = new VerticalLayout();
		//mainContainer.setSizeFull();
		mainContainer.setMargin(false);
		mainContainer.addStyleName("main-container");
		{
			mainContainer.addComponent(new LoginLogoutBar());
			mainContainer.addComponent(new HeaderBar());
			mainContainer.addComponent(new MegaMenu());
			
			VerticalLayout body = new VerticalLayout();
			body.addStyleName("body");
			body.setSizeFull();
			body.setMargin(false);
			mainContainer.addComponent(body);
			mainContainer.setExpandRatio(body, 1);
	        new ApplicationNavigator(body);
			getNavigator().navigateTo(getNavigator().getState());
			{
				/*
				body.addComponent(new BannerSlider());
				body.addComponent(new SectionTitleView("QUEM JÁ É ZapDoctor"));
				body.addComponent(new ProdutosCarrouselView());
				body.addComponent(new SuporteProdutosView());
				body.addComponent(new ParalaxView());
				body.addComponent(new SectionTitleView("INFORMAÇÕES DO BLOG"));
				body.addComponent(new InformacoesBlogView());
				*/
			}
			
			mainContainer.addComponent(new FooterColumnsView());
			
			/*
			VerticalLayout tail = new VerticalLayout();
			mainContainer.addComponent(tail);
			mainContainer.setExpandRatio(tail, 1);
			*/
		}
		
		return mainContainer;
	}
	
	public CssLayout loginView() {
		LoginView loginView = new LoginView();
		return loginView;
		
		/*
		CssLayout loginView = new CssLayout();
		loginView.addStyleName("login-view");
		//loginView.addStyleName("block");
		loginView.setResponsive(true);
		{
			CssLayout loginContainer = new CssLayout();
			loginContainer.addStyleName("login-container");
			//loginContainer.addStyleName("centered");
			loginView.addComponent(loginContainer);
			{
				Label boasVindas = new Label("Bem Vindo");
				boasVindas.addStyleName("lbl-boasvindas");
				boasVindas.setResponsive(true);
				loginContainer.addComponent(boasVindas);
				
				Label tituloSistema = new Label("Sociedade PRÓ-SAÚDE");
				tituloSistema.addStyleName("lbl-titulo");
				tituloSistema.setResponsive(true);
				loginContainer.addComponent(tituloSistema);
			}
		}
		return loginView;
		*/
	}
	
	/*
    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        User user = getDatabase().authenticate(event.getUserName(), event.getPassword());
        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
        updateContent();
    }
    */

	/*
    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }
    */
	
    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
    	// Caso esteja marcado para fechar as janelas que estiverem abertas
    	//System.out.println("Numero de janelas detectadas: " + getWindows().size());
    	/*
    	if (this.getCloseAllWindow()) {
            for (Window window : getWindows()) {
                window.close();
            }
    		this.setCloseAllWindow(false);
    	}
    	*/
    }
	
    public ApplicationEventBus getApplicationEventbus() {
    	Database database = (Database) VaadinSession.getCurrent().getAttribute("database");
    	//ApplicationUI ui = (ApplicationUI) UI.getCurrent();
    	return database.getApplicationEventbus();
    }
    
    /*
    public void scrollTop() {
    	if (main!=null) {
        	scrollIntoView(main.getComponent(0));
    	}
    }
    */
}