package com.belavus.sportsresult.config;


import com.belavus.sportsresult.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp; // TODO: n.kvetko: unused import statement
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.parameters.P; // TODO: n.kvetko: unused import statement
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // TODO: n.kvetko: unused import statement
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired // TODO: n.kvetko: unnecessary annotation. Bean will be injected without it (the annotation)
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
//                .antMatchers("/event-create","event-delete/{id}","/event-update/{id}","/event-update").hasRole("ADMIN")
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
//                .antMatchers("/auth/login", "/error").permitAll()
//                .anyRequest().hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/first", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");

        // TODO: n.kvetko: "/auth/login" - string literals should not be duplicated.
        // TODO: n.kvetko: Duplicated string literals make the process of refactoring error-prone, since you must be
        // TODO: n.kvetko: sure to update all occurrences.
    }

    // Setting up authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder()); // TODO: n.kvetko: тоже напишу на русском, для более точного
        // понимания. Нижешь ты написал метод getPasswordEncoder() с аннотацией @Bean. Это значит, что при старте
        // приложения, если текущий файл сканируется (попадает в поле зрение приложения), то этот бин будет создан.
        // (Все, что возвращает какой-то тип данных, имеет аннотацию @Bean и модификтор доступа public - все это
        // создается при старте приложения). В твом случае ты создаешь бин на 70 строке, но ты его не переиспользуешь
        // в 62 строке, ты заново вызываешь этот метод, но уже не как бин, а как обычный вызов метода, который вернет
        // тебе не синглтон объект как бин, а новый объект (который никак с бином связан не будет). В этом случае
        // тебе нужно либо напрямую писать тут .passwordEncoder(new PasswordEncoder) (нужная тебе реализация,
        // например BCryptPasswordEncoder) либо в поле этого класса инджектить бин как ты это сделал для
        // personDetailsService сервиса
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() { // TODO: n.kvetko: it's not a getter, method name === bean name,
        // TODO: n.kvetko: so it should just be passwordEncoder()
        // TODO: n.kvetko: напишу на русском, чтобы было понятнее.
        //  Тут ты объявил бин (bean declaration).
        //  Если где-то в другом компоненте, ты захочешь внедрить в него этот бин, то оно будет искать по имени этот
        //  бин, а имя ты задал getPasswordEncoder() (имя метода, без скобок --- это имя Бина)
        // то есть в компоненте тебе нужно будет писать сеттер setGetPasswordEncoder или в конструкторе писать
        // public ClassName(PasswordEncoder getPasswordEncoder), так как по дефолту бины ищет по имени
        // Надеюсь так чуть-чуть понятней
        return new BCryptPasswordEncoder();
    }

    // TODO: n.kvetko: Programmers should not comment out code as it bloats programs and reduces readability.
    // TODO: n.kvetko: Unused code should be deleted and can be retrieved from source control history if required.
//    do without encryption // TODO: n.kvetko: Passwords must always be encrypted. Never do without encryption, or
//     rather without hashing. Passwords need to be hashed, it means, an algorithm that converts the data into some kind
//     of hash that cannot be "decrypted" back in any way, it's like one-way encryption.
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

}