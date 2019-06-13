package demo.config.costom_component;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabaseFillerOnStartup implements ApplicationListener<ContextStartedEvent> {

	
	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
//		TODO
//		which one is better?
//		if (event.getApplicationContext().getParent() == null) {
//		}
//		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
//		}
	}

}
