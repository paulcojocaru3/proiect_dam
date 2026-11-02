package telecom_suport.config;

import org.springframework.context.annotation.*;
import java.time.Clock;
@Configuration
public class DomainConfiguration { @Bean public Clock clock() { return Clock.systemDefaultZone(); } }
