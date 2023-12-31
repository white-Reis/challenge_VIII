package Fabio.Reis.Scholarship.config;

import Fabio.Reis.Scholarship.ScholarshipApplication;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ScholarshipApplication.class, ModelMapperConfig.class})
public class ModelMapperConfigTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    public void testMapper() {

        Student source = new Student();
        source.setName("Fabio");
        source.setLastName("Reis");
        source.setLevel(3);
        source.setCourse("Information Systems");
        source.setEmail("Fabio.Reis.pb@compass.com");

        StudentDTO destination = mapper.map(source, StudentDTO.class);


        assertThat(destination).isNotNull();

        assertThat(destination.getName()).isEqualTo(source.getName());
        assertThat(destination.getLastName()).isEqualTo(source.getLastName());
        assertThat(destination.getLevel()).isEqualTo(source.getLevel());
        assertThat(destination.getCourse()).isEqualTo(source.getCourse());
        assertThat(destination.getEmail()).isEqualTo(source.getEmail());
    }

}