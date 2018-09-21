package com.evolucao.weblibrary;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum ApplicationViewType {
    DASHBOARD("dashboard", null, FontAwesome.HOME, false);
    //DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, false),
    //TESTE1("teste1", Teste1.class, FontAwesome.HOME, false),
    //TESTE2("teste2", Teste2.class, FontAwesome.HOME, false);
	
	/*
    DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, false),
	CATEGORYMEDICOS("categorymedicos", CategoryMedicosView.class, FontAwesome.HOME, false),
	CheckOutView("checkoutView", CheckoutView.class, FontAwesome.HOME, false),
	FecharPedidoView("fecharPedidoView", FecharPedidoView.class, FontAwesome.HOME, false),
	PedidoSucessoView("pedidoSucessoView", PedidoSucessoView.class, FontAwesome.HOME, false),
	QuemSomosView("quemSomosView", QuemSomosView.class, FontAwesome.HOME, false),
	CredenciamentoView("credenciamentoView", CredenciamentoView.class, FontAwesome.HOME, false ),
	CredenciamentoSucessoView("credenciamentoSucessoView", CredenciamentoSucessoView.class, FontAwesome.HOME, false ),
	SemResultadoView("semResultadoView", SemResultadoView.class, FontAwesome.HOME, false ),
	ProductDetailView("productDetailView", ProductDetailView.class, FontAwesome.HOME, false),
	PesquisaClinicasView("pesquisaClinicasView", PesquisaClinicasView.class, FontAwesome.HOME, false),
	clinicasDetailView("clinicasDetailView", ClinicasDetailView.class, FontAwesome.HOME, false),
	TesteView("testeView", TesteView.class, FontAwesome.HOME, false ),
	LoginCadastroView("loginCadastroView", LoginCadastroView.class, FontAwesome.HOME, false),
	CriarContaView("criarContaView", CriarContaView.class, FontAwesome.HOME, false),
	GerenciaView("gerenciaView", GerenciaView.class, FontAwesome.HOME, false),
	PainelAdministradorView("painelAdministradorView", PainelAdministradorView.class, FontAwesome.HOME, false);
	*/
	/*
    DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true), 
    SALES("sales", SalesView.class, FontAwesome.BAR_CHART_O, false), 
    TRANSACTIONS("transactions", TransactionsView.class, FontAwesome.TABLE, false), 
    REPORTS("reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true), 
    SCHEDULE("schedule", ScheduleView.class, FontAwesome.CALENDAR_O, false);
    */

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private ApplicationViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon, final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
       this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static ApplicationViewType getByViewName(final String viewName) {
        ApplicationViewType result = null;
        for (ApplicationViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }
}
