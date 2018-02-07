package com.kaceper.config;

import com.kaceper.service.WorkerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Kaceper on 2017-11-29.
 */

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
@ComponentScan("com.kaceper")
@EnableJpaRepositories(value = "com.kaceper.repository")
@Configuration
@EnableWebSecurity
public class App extends WebSecurityConfigurerAdapter {


    private static final String MYSQL_USER = "timon";
    private static final String MYSQL_PASSWORD = "pumba";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String MAIL_USER = "hotelwaltertorun@gmail.com";
    private static final String MAIL_PASSWORD = "Wiktoria.123";
    private static final String MAIL_URL = "smtp.gmail.com";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    CustomAuthFailureHandler customAuthFailureHandler;

    public class CustomAuthenticationSuccessHandler extends
            SavedRequestAwareAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication authentication) throws IOException,ServletException {

            super.onAuthenticationSuccess(request, response, authentication);
            //Now add your custom logic to update database
        }
    }

    @Component
    public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
            getRedirectStrategy().sendRedirect(request, response, "/loginPage?error=" + exception.getMessage());
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/pracownik/**").hasAnyRole("WORKER", "ADMIN")
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .and()
                    .formLogin()
                    .loginPage("/loginPage")
                    .failureHandler(customAuthFailureHandler)
                    .permitAll()
                .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/loginPage?error=Nie%20masz%20dostępu")
                .and()
                    .headers()
                    .defaultsDisabled()
                    .frameOptions()
                    .sameOrigin()
                    .cacheControl();
    }



    @Autowired
    private WorkerDetailsService workerDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(workerDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder x = new BCryptPasswordEncoder();
        return x;
    }

    @Bean
    public ClassLoaderTemplateResolver webPageTemplateResolver(){
        ClassLoaderTemplateResolver webPageTemplateResolver = new ClassLoaderTemplateResolver();
        webPageTemplateResolver.setPrefix("templates/");
        webPageTemplateResolver.setSuffix(".html");
        webPageTemplateResolver.setTemplateMode("HTML5");
        webPageTemplateResolver.setCharacterEncoding("UTF-8");
        webPageTemplateResolver.setOrder(1);
        return webPageTemplateResolver;
    }


    @Bean
    public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(webPageTemplateResolver());
        engine.addDialect(securityDialect());
        engine.addDialect(new SpringSecurityDialect());
        return engine;
    }

    private IDialect securityDialect(){
        SpringSecurityDialect dialect = new SpringSecurityDialect();
        return dialect;
    }

    @Bean
    public ViewResolver viewResolver() {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");

        return viewResolver;
    }


    @Bean
    public EntityManagerFactory entityManagerFactory() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("myPersistenceUnit");
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.kaceper.model");

        HashMap<String, Object> map = new HashMap<>();
        map.put("hibernate.jdbc.batch_size", 100);
        map.put("hibernate.jdbc.batch_versioned_data", true);
        map.put("hibernate.order_inserts", true);
        map.put("hibernate.order_updates", true);
        // map.put("hibernate.show_sql", true);
        // map.put("hibernate.format_sql", true);
        map.put("hibernate.hbm2ddl.auto", "validate");
        map.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        factory.setJpaPropertyMap(map);

        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager getTM(EntityManagerFactory emf) {
        final JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        return tm;
    }


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(MYSQL_URL);
        ds.setUsername(MYSQL_USER);
        ds.setPassword(MYSQL_PASSWORD);
        return ds;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(MAIL_URL);
        mailSender.setPort(587);

        mailSender.setUsername(MAIL_USER);
        mailSender.setPassword(MAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }


}
