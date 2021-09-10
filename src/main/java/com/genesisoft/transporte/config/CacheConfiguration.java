package com.genesisoft.transporte.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.genesisoft.transporte.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.genesisoft.transporte.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.genesisoft.transporte.domain.User.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Authority.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.User.class.getName() + ".authorities");
            createCache(cm, com.genesisoft.transporte.domain.Countries.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Countries.class.getName() + ".stateProvinces");
            createCache(cm, com.genesisoft.transporte.domain.StateProvinces.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.StateProvinces.class.getName() + ".cities");
            createCache(cm, com.genesisoft.transporte.domain.StateProvinces.class.getName() + ".companies");
            createCache(cm, com.genesisoft.transporte.domain.StateProvinces.class.getName() + ".affiliates");
            createCache(cm, com.genesisoft.transporte.domain.StateProvinces.class.getName() + ".toInsurances");
            createCache(cm, com.genesisoft.transporte.domain.StateProvinces.class.getName() + ".forInsurances");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".companies");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".affiliates");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".customers");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".parkings");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".suppliers");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".employees");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".originVehicleControls");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".destinationVehicleControls");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".vehicleLocationStatuses");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".originVehicleControlExpenses");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".destinationVehicleControlExpenses");
            createCache(cm, com.genesisoft.transporte.domain.Cities.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.Companies.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Companies.class.getName() + ".affiliates");
            createCache(cm, com.genesisoft.transporte.domain.Companies.class.getName() + ".employees");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".insurances");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".positions");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".costCenters");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".administrativeFeesRanges");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".customersGroups");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".fees");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".customers");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".statusAttachments");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".statuses");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".parkings");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".suppliers");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".employees");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".vehicleControls");
            createCache(cm, com.genesisoft.transporte.domain.Affiliates.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.Insurances.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Positions.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Positions.class.getName() + ".employees");
            createCache(cm, com.genesisoft.transporte.domain.CostCenter.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.CostCenter.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.AdministrativeFeesRanges.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.CustomersGroups.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.CustomersGroups.class.getName() + ".customers");
            createCache(cm, com.genesisoft.transporte.domain.CustomersGroups.class.getName() + ".vehicleControls");
            createCache(cm, com.genesisoft.transporte.domain.Fees.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Fees.class.getName() + ".vehicleControlBillings");
            createCache(cm, com.genesisoft.transporte.domain.Customers.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Customers.class.getName() + ".customersContacts");
            createCache(cm, com.genesisoft.transporte.domain.Customers.class.getName() + ".customerAttachments");
            createCache(cm, com.genesisoft.transporte.domain.Customers.class.getName() + ".vehicleControls");
            createCache(cm, com.genesisoft.transporte.domain.Customers.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.CustomersContacts.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.CustomerAttachments.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.ServiceProvided.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.ServiceProvided.class.getName() + ".suppliers");
            createCache(cm, com.genesisoft.transporte.domain.StatusAttachments.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.StatusAttachments.class.getName() + ".customerAttachments");
            createCache(cm, com.genesisoft.transporte.domain.Status.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Status.class.getName() + ".vehicleControls");
            createCache(cm, com.genesisoft.transporte.domain.Status.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.Parking.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Parking.class.getName() + ".parkingSectors");
            createCache(cm, com.genesisoft.transporte.domain.Parking.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.ParkingSector.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.ParkingSector.class.getName() + ".parkingSectorSpaces");
            createCache(cm, com.genesisoft.transporte.domain.ParkingSector.class.getName() + ".housingVehicleItems");
            createCache(cm, com.genesisoft.transporte.domain.ParkingSectorSpace.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.ParkingSectorSpace.class.getName() + ".housingVehicleItems");
            createCache(cm, com.genesisoft.transporte.domain.Suppliers.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Suppliers.class.getName() + ".supplierBanksInfos");
            createCache(cm, com.genesisoft.transporte.domain.Suppliers.class.getName() + ".supplierContacts");
            createCache(cm, com.genesisoft.transporte.domain.Suppliers.class.getName() + ".vehicleControlExpenses");
            createCache(cm, com.genesisoft.transporte.domain.Suppliers.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.SupplierBanksInfo.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.SupplierContacts.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Employees.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Employees.class.getName() + ".employeeAttachments");
            createCache(cm, com.genesisoft.transporte.domain.Employees.class.getName() + ".employeeComponentsData");
            createCache(cm, com.genesisoft.transporte.domain.Employees.class.getName() + ".vehicleControls");
            createCache(cm, com.genesisoft.transporte.domain.Employees.class.getName() + ".vehicleControlHistories");
            createCache(cm, com.genesisoft.transporte.domain.Employees.class.getName() + ".housings");
            createCache(cm, com.genesisoft.transporte.domain.EmployeeAttachments.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.EmployeeComponentsData.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControls.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControls.class.getName() + ".vehicleLocationStatuses");
            createCache(cm, com.genesisoft.transporte.domain.VehicleControls.class.getName() + ".vehicleControlHistories");
            createCache(cm, com.genesisoft.transporte.domain.VehicleControls.class.getName() + ".vehicleControlBillings");
            createCache(cm, com.genesisoft.transporte.domain.VehicleControls.class.getName() + ".vehicleControlItems");
            createCache(cm, com.genesisoft.transporte.domain.VehicleControls.class.getName() + ".vehicleControlAttachments");
            createCache(cm, com.genesisoft.transporte.domain.VehicleControls.class.getName() + ".vehicleControlExpenses");
            createCache(cm, com.genesisoft.transporte.domain.VehicleLocationStatus.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControlHistory.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControlItem.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControlItem.class.getName() + ".vehicleInspections");
            createCache(cm, com.genesisoft.transporte.domain.VehicleControlItem.class.getName() + ".vehicleControlExpenses");
            createCache(cm, com.genesisoft.transporte.domain.VehicleInspections.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleInspections.class.getName() + ".vehicleInspectionsImagens");
            createCache(cm, com.genesisoft.transporte.domain.VehicleInspectionsImagens.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControlBilling.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControlExpenses.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.VehicleControlAttachments.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Housing.class.getName());
            createCache(cm, com.genesisoft.transporte.domain.Housing.class.getName() + ".housingVehicleItems");
            createCache(cm, com.genesisoft.transporte.domain.HousingVehicleItem.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
