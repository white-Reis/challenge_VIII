package Fabio.Reis.Scholarship.config;


import Fabio.Reis.Scholarship.repository.TeamRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalConfig {

    private TeamRepo teamRepo;

    LocalConfig(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    @Bean
    public void startDB() {

    }
}