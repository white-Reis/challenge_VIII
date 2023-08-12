package Fabio.Reis.Scholarship.config;


import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import Fabio.Reis.Scholarship.model.studentEntity.Student;
import Fabio.Reis.Scholarship.model.teamEntity.Team;
import Fabio.Reis.Scholarship.repository.TeamRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Profile("local")
public class LocalConfig {

    private TeamRepo teamRepo;

    LocalConfig(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    @Bean
    public void startDB() {
        Team team = new Team();
        team.setName("SP - Spring Boot - Back-End Journey | AWS Cloud Context ");
        team.setLearning("Spring Boot - AWS");
        Set<Internal> internals = new HashSet<>();
        Internal internal1 = new Internal();
        internal1.setName("Maximiliano");
        internal1.setLastName("Calijurisz");
        internal1.setPosition("Analista de requisito");
        internal1.setRole("Scrum Master");
        internal1.setEmail("maxInterno@comapss.com");
        internals.add(internal1);

        Internal internal2 = new Internal();
        internal2.setName("Yago");
        internal2.setLastName("Lopessz");
        internal2.setPosition("Analista de requisito");
        internal2.setRole("Scrum Master");
        internal2.setEmail("YagoInterno@comapss.com");
        internals.add(internal2);

        Internal internal3 = new Internal();
        internal3.setName("Thais");
        internal3.setLastName("Nicodemussz");
        internal3.setPosition("general coordinator");
        internal3.setRole("Coordinator");
        internal3.setEmail("ThaisNicodemusInterno@comapss.com");
        internals.add(internal3);

        Internal internal4 = new Internal();
        internal4.setName("Edmar");
        internal4.setLastName("Millersz");
        internal4.setPosition("Back-end Developer Junior");
        internal4.setRole("Instructor");
        internal4.setEmail("EdmarMillerInterno@comapss.com");
        internals.add(internal4);

        Internal internal5 = new Internal();
        internal5.setName("Giovanni");
        internal5.setLastName("Agenorsz");
        internal5.setPosition("Back-end Developer Senior");
        internal5.setRole("Instructor");
        internal5.setEmail("GiovanniAgenorInterno@comapss.com");
        internals.add(internal5);

        Internal internal6 = new Internal();
        internal6.setName("Mateus");
        internal6.setLastName("Oliveira");
        internal6.setPosition("Back-end Developer Senior");
        internal6.setRole("Instructor");
        internal6.setEmail("MateusInterno@comapss.com");
        internals.add(internal6);

        team.setInternals(internals);

        Set<Student> students = new HashSet<>();
        Student student1 = new Student();
        student1.setName("Fabio");
        student1.setLastName("Reis");
        student1.setLevel(3);
        student1.setCourse("Information Systems");
        student1.setEmail("Fabio.Reis.pb@compass.com");
        students.add(student1);

        Student student2 = new Student();
        student2.setName("Ana");
        student2.setLastName("Silva");
        student2.setLevel(2);
        student2.setCourse("Information Systems");
        student2.setEmail("Ana.Silva@compass.com");
        students.add(student2);

        Student student3 = new Student();
        student3.setName("Rafael");
        student3.setLastName("Ferreira");
        student3.setLevel(2);
        student3.setCourse("Software Engineering");
        student3.setEmail("Rafael.Ferreira@compass.com");
        students.add(student3);

        Student student5 = new Student();
        student5.setName("Lucas");
        student5.setLastName("Santos");
        student5.setLevel(3);
        student5.setCourse("Information Technology");
        student5.setEmail("Lucas.Santos@compass.com");
        students.add(student5);

        Student student6 = new Student();
        student6.setName("Maria");
        student6.setLastName("Rodrigues");
        student6.setLevel(2);
        student6.setCourse("Software Development");
        student6.setEmail("Maria.Rodrigues@compass.com");
        students.add(student6);

        Student student7 = new Student();
        student7.setName("Pedro");
        student7.setLastName("Almeida");
        student7.setLevel(3);
        student7.setCourse("Computer Science");
        student7.setEmail("Pedro.Almeida@compass.com");
        students.add(student7);

        Student student8 = new Student();
        student8.setName("Fernanda");
        student8.setLastName("Oliveira");
        student8.setLevel(1);
        student8.setCourse("Information Systems");
        student8.setEmail("Fernanda.Oliveira@compass.com");
        students.add(student8);

        Student student9 = new Student();
        student9.setName("Gustavo");
        student9.setLastName("Sousa");
        student9.setLevel(3);
        student9.setCourse("Software Engineering");
        student9.setEmail("Gustavo.Sousa@compass.com");
        students.add(student9);

        Student student10 = new Student();
        student10.setName("Juliana");
        student10.setLastName("Ramos");
        student10.setLevel(2);
        student10.setCourse("Computer Engineering");
        student10.setEmail("Juliana.Ramos@compass.com");
        students.add(student10);
        Student student11 = new Student();
        student11.setName("Tiago");
        student11.setLastName("Machado");
        student11.setLevel(1);
        student11.setCourse("Information Technology");
        student11.setEmail("Tiago.Machado@compass.com");
        students.add(student11);

        Student student12 = new Student();
        student12.setName("Amanda");
        student12.setLastName("Costa");
        student12.setLevel(1);
        student12.setCourse("Software Development");
        student12.setEmail("Amanda.Costa@compass.com");
        students.add(student12);

        Student student13 = new Student();
        student13.setName("Ricardo");
        student13.setLastName("Gomes");
        student13.setLevel(3);
        student13.setCourse("Computer Science");
        student13.setEmail("Ricardo.Gomes@compass.com");
        students.add(student13);

        Student student14 = new Student();
        student14.setName("Isabela");
        student14.setLastName("Martins");
        student14.setLevel(2);
        student14.setCourse("Information Systems");
        student14.setEmail("Isabela.Martins@compass.com");
        students.add(student14);

        team.setStudents(students);

        teamRepo.save(team);
    }
}