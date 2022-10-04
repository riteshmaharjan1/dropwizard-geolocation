package com.geoip.runner;

import com.geoip.caching.GeolocationCaching;
import com.geoip.config.GeoIPLocationConfiguration;
import com.geoip.dao.GeoIPLocationDAO;
import com.geoip.entity.Geolocation;
import com.geoip.resource.GeoIPLocationResource;
import com.geoip.service.GeolocationService;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.client.Client;

public class GeoIPLocationRunner extends Application<GeoIPLocationConfiguration> {

    public static void main(String[] args) throws Exception {
        new GeoIPLocationRunner().run(args);
    }

    @Override
    public String getName() {
        return "demo";
    }

    @Override
    public void initialize(Bootstrap<GeoIPLocationConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(GeoIPLocationConfiguration geoIPLocationConfiguration, Environment environment) throws Exception {
        System.out.println("Running");
        final GeolocationCaching cacheInstance = GeolocationCaching.getInstance();
        GeoIPLocationDAO geoIPLocationDAO = new GeoIPLocationDAO(hibernateBundle.getSessionFactory());
        //Create Jersey client
        final Client client = new JerseyClientBuilder(environment).using(geoIPLocationConfiguration.getJerseyClientConfiguration())
                .build(getName());
        final GeolocationService geolocationService = new GeolocationService(geoIPLocationDAO, client);
        cacheInstance.initGeoCache(geolocationService);
        final GeoIPLocationResource resource = new GeoIPLocationResource(geolocationService);
        environment.jersey().register(resource);

    }

    HibernateBundle<GeoIPLocationConfiguration> hibernateBundle = new HibernateBundle<GeoIPLocationConfiguration>(Geolocation.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(GeoIPLocationConfiguration geoIPLocationConfiguration) {
            return geoIPLocationConfiguration.getDataSourceFactory();
        }
    };
}
