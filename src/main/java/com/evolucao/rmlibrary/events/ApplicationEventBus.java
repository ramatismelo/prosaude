package com.evolucao.rmlibrary.events;

import com.evolucao.weblibrary.ApplicationUI;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.vaadin.ui.UI;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for
 * relevant actions.
 */
public class ApplicationEventBus implements SubscriberExceptionHandler {
    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
    	ApplicationUI ui = (ApplicationUI) UI.getCurrent();
    	//Database database = (Database) VaadinSession.getCurrent().getAttribute("database");
    	ui.database.getApplicationEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
    	ApplicationUI ui = (ApplicationUI) UI.getCurrent();
    	//Database database = (Database) VaadinSession.getCurrent().getAttribute("database");
    	ui.database.getApplicationEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
    	ApplicationUI ui = (ApplicationUI) UI.getCurrent();
    	//Database database = (Database) VaadinSession.getCurrent().getAttribute("database");
    	ui.database.getApplicationEventbus().eventBus.register(object);
    }

    @Override
    public final void handleException(final Throwable exception, final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
