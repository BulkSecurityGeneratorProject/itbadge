package com.itescia.itbadge.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.itescia.itbadge.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Description.class.getName(), jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Groupe.class.getName(), jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Utilisateur.class.getName(), jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Badgeage.class.getName(), jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Cours.class.getName(), jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Groupe.class.getName() + ".listEleves", jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Groupe.class.getName() + ".listCours", jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Utilisateur.class.getName() + ".listBageages", jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Utilisateur.class.getName() + ".listCours", jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Cours.class.getName() + ".listProfesseurs", jcacheConfiguration);
            cm.createCache(com.itescia.itbadge.domain.Cours.class.getName() + ".listGroupes", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
