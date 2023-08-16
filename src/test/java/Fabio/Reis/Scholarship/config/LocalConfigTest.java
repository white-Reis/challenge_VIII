package Fabio.Reis.Scholarship.config;

import Fabio.Reis.Scholarship.ScholarshipApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ScholarshipApplication.class, LocalConfig.class})
public class LocalConfigTest {

    @Autowired
    private LocalConfig localConfig;

    @Test
    public void testStartDB() {
        assertThat(localConfig).isNotNull();

    }

}