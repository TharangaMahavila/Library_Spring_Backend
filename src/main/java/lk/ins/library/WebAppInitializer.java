package lk.ins.library;

import lk.ins.library.util.LogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-23
 **/

@SpringBootApplication
public class WebAppInitializer extends SpringBootServletInitializer {

   /* @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }*/

    public static void main(String[] args) {
        LogConfig.initLogging();
        SpringApplication.run(WebAppInitializer.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebAppInitializer.class);
    }

    /*@Bean
    CommandLineRunner run(StudentDAO studentDAO){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                IntStream.rangeClosed(1,50).forEach(i -> {
                    Student student = new Student();
                    student.setRegNo("S"+i);
                    student.setName(new Name("KP","Tharanga"+i,"Mahavia"));
                    student.setGuardianName("Kp kularathna");
                    student.setAddress(new Address("No 01","Arachchihena","Thiththagalla","Ahangama"));
                    student.setGender(Gender.MALE);
                    student.setContact("0777215112");
                    HashSet<Grade> grades = new HashSet<>();
                    grades.add(new Grade(1, 10, Section.A, 2021));
                    student.setGrades(grades);

                    studentDAO.save(student);
                });
            }
        };
    }*/
}
