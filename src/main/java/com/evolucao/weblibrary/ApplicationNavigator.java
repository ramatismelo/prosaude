package com.evolucao.weblibrary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.evolucao.rmlibrary.events.ApplicationEventBus;
import com.evolucao.rmlibrary.events.ApplicationEvent;
import com.evolucao.rmlibrary.events.ApplicationEvent.PostViewChangeEvent;
import com.evolucao.rmlibrary.events.ApplicationEvent.BrowserResizeEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class ApplicationNavigator extends Navigator {
    private static final ApplicationViewType ERROR_VIEW = ApplicationViewType.DASHBOARD;
    private ViewProvider errorViewProvider;

    // Provide a Google Analytics tracker id here
    private static final String TRACKER_ID = null;// "UA-658457-6";
    //private GoogleAnalyticsTracker tracker;

    public ApplicationNavigator(final ComponentContainer container) {
        super(UI.getCurrent(), container);

        String host = getUI().getPage().getLocation().getHost();
        if (TRACKER_ID != null && host.endsWith("demo.vaadin.com")) {
            initGATracker(TRACKER_ID);
        }
        initViewChangeListener();
        initViewProviders();
    }

    private void initGATracker(final String trackerId) {
        //tracker = new GoogleAnalyticsTracker(trackerId, "demo.vaadin.com");

        // GoogleAnalyticsTracker is an extension add-on for UI so it is
        // initialized by calling .extend(UI)
        //tracker.extend(UI.getCurrent());
    }

    private void initViewChangeListener() {
        addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(final ViewChangeEvent event) {
                // Since there's no conditions in switching between the views
                // we can always return true.
            	//System.out.println("beforeViewChange: " + event.getViewName());
            	
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeEvent event) {
                ApplicationViewType view = ApplicationViewType.getByViewName(event.getViewName());
                // Appropriate events get fired after the view is changed.
                ApplicationEventBus.post(new PostViewChangeEvent(view));
                ApplicationEventBus.post(new BrowserResizeEvent());
                //ApplicationEventBus.post(new CloseOpenWindowsEvent());

                // Cria uma lista das janelas que devem ser fechadas;
                List<Window> windowToClose = new ArrayList<Window>();
                for (Window window: UI.getCurrent().getWindows()) {
                	windowToClose.add(window);
                }
                
                // Fecha efetivamente as janelas que estiverem abertas
                for (Window window: windowToClose) {
                	getUI().getCurrent().removeWindow(window);
                }

                //if (tracker != null) {
                //    // The view change is submitted as a pageview for GA tracker
                //    tracker.trackPageview("/dashboard/" + event.getViewName());
                //}
            }
        });
    }

    private void initViewProviders() {
        // A dedicated view provider is added for each separate view type
        for (final ApplicationViewType viewType : ApplicationViewType.values()) {
            ViewProvider viewProvider = new ClassBasedViewProvider(viewType.getViewName(), viewType.getViewClass()) {
            	// This field caches an already initialized view instance if the
                // view should be cached (stateful views).
                private View cachedInstance;

                @Override
                public View getView(final String viewName) {
                    View result = null;
                    if (viewType.getViewName().equals(viewName)) {
                        if (viewType.isStateful()) {
                            // Stateful views get lazily instantiated
                            if (cachedInstance == null) {
                                cachedInstance = super.getView(viewType.getViewName());
                            }
                            result = cachedInstance;
                        } else {
                            // Non-stateful views get instantiated every time
                            // they're navigated to
                            result = super.getView(viewType.getViewName());
                        }
                    }
                    return result;
                }
            };

            if (viewType == ERROR_VIEW) {
                errorViewProvider = viewProvider;
            }

            addProvider(viewProvider);
        }

        setErrorProvider(new ViewProvider() {
            @Override
            public String getViewName(final String viewAndParameters) {
                return ERROR_VIEW.getViewName();
            }

            @Override
            public View getView(final String viewName) {
                return errorViewProvider.getView(ERROR_VIEW.getViewName());
            }
        });
    }
}
