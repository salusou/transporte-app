package com.genesisoft.transporte.config;

import com.genesisoft.transporte.domain.Authority;
import com.genesisoft.transporte.domain.User;
import com.genesisoft.transporte.repository.AuthorityRepository;
import com.genesisoft.transporte.repository.UserRepository;
import com.genesisoft.transporte.security.AuthoritiesConstants;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoadingDataConfiguration implements InitializingBean {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate = new RestTemplate();

    private Authority adminAuthority;
    private Authority userAuthority;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--- Carregando dados iniciais do Live ---");
        Optional<User> userAdmin = userRepository.findOneWithAuthoritiesByLogin("admin");
        if (!userAdmin.isPresent()) {
            loadAuthority();
            loadUsers();
        }

        System.out.println("--- Dados Carregados ---");
    }

    void loadAuthority() {
        List<Authority> authorityList = authorityRepository.findAll();
        if (authorityList.isEmpty()) {
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.ADMIN);
            adminAuthority = authorityRepository.save(authority);

            authority = new Authority();
            authority.setName(AuthoritiesConstants.USER);
            userAuthority = authorityRepository.save(authority);

            authority = new Authority();
            authority.setName(AuthoritiesConstants.CLIENT);
            authorityRepository.save(authority);

            authority = new Authority();
            authority.setName(AuthoritiesConstants.SUPERVISOR);
            authorityRepository.save(authority);

            authority = new Authority();
            authority.setName(AuthoritiesConstants.OPERATOR);
            authorityRepository.save(authority);

            authority = new Authority();
            authority.setName(AuthoritiesConstants.SUPPLIER);
            authorityRepository.save(authority);

            authority = new Authority();
            authority.setName(AuthoritiesConstants.VISITOR);
            authorityRepository.save(authority);

            authority = new Authority();
            authority.setName(AuthoritiesConstants.ANONYMOUS);
            authorityRepository.save(authority);
        }
    }

    void loadUsers() {
        User user = new User();
        user.setId(1L);
        user.setLogin("admin");
        user.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        user.setActivated(true);
        user.setEmail("admin@genesisoft.com.br");
        user.setFirstName("Administrator");
        user.setFirstName("Administrator");
        user.setLangKey("pt-br");

        Set<Authority> authorities = new HashSet<>();
        authorities.add(adminAuthority);
        authorities.add(userAuthority);
        user.setAuthorities(authorities);

        userRepository.save(user);

        user = new User();
        user.setId(2L);
        user.setLogin("user");
        user.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        user.setActivated(true);
        user.setEmail("suporte@genesisoft.com.br");
        user.setFirstName("User");
        user.setLastName("User");
        user.setLangKey("pt-br");

        authorities = new HashSet<>();
        authorities.add(userAuthority);
        user.setAuthorities(authorities);

        userRepository.save(user);
    }
}
