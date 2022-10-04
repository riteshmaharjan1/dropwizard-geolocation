package com.geoip.dao;

import com.geoip.entity.Geolocation;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.validation.Valid;

public class GeoIPLocationDAO extends AbstractDAO<Geolocation> {
    public GeoIPLocationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(@Valid Geolocation geolocation) {
        persist(geolocation);
    }

    public Geolocation findByIpAddress(String ipAddress) {
        return get(ipAddress);
    }

}
