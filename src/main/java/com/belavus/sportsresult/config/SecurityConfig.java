package com.belavus.sportsresult.config;


import com.belavus.sportsresult.service.impl.PersonDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsServiceImpl personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsServiceImpl personDetailsService) {
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

    }

    // Setting up authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder()); //  для более точного
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
    public PasswordEncoder getPasswordEncoder() { // it's not a getter, method name === bean name,
        //  so it should just be passwordEncoder()
        //  Тут объявил бин (bean declaration).
        //  Если где-то в другом компоненте, ты захочешь внедрить в него этот бин, то оно будет искать по имени этот
        //  бин, а имя ты задал getPasswordEncoder() (имя метода, без скобок --- это имя Бина)
        // то есть в компоненте тебе нужно будет писать сеттер setGetPasswordEncoder или в конструкторе писать
        // public ClassName(PasswordEncoder getPasswordEncoder), так как по дефолту бины ищет по имени
        // Надеюсь так чуть-чуть понятней
        return new BCryptPasswordEncoder();
    }

    //  Programmers should not comment out code as it bloats programs and reduces readability.
    //  Unused code should be deleted and can be retrieved from source control history if required.
//Passwords must always be encrypted. Never do without encryption, or
//     rather without hashing. Passwords need to be hashed, it means, an algorithm that converts the data into some kind
//     of hash that cannot be "decrypted" back in any way, it's like one-way encryption.


}