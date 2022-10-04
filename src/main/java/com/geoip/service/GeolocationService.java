package com.geoip.service;

import com.geoip.dao.GeoIPLocationDAO;
import com.geoip.entity.Geolocation;
import com.geoip.entity.dto.GeolocationDTO;
import com.geoip.service.adapter.GeolocationAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.time.LocalDateTime;

public class GeolocationService implements IGeolocationService {

    private final String getURL = "http://ip-api.com/json/";
    private final GeoIPLocationDAO geoIPLocationDAO;
    private Client client;
    private static Logger log = LoggerFactory.getLogger(GeolocationService.class);

    public GeolocationService(GeoIPLocationDAO geoIPLocationDAO, Client client) {
        this.geoIPLocationDAO = geoIPLocationDAO;
        this.client = client;
    }

    @Override
    public GeolocationDTO getGeoDataFromAPI(String ipAddress) {
        GeolocationDTO geolocationDTO;
        Geolocation geoData;
        try {
            log.info("Ip address Searching in Database: " + ipAddress);
            geoData = getGeoDataFromDB(ipAddress);
            if (LocalDateTime.now().minusMinutes(5l).isAfter(geoData.getUpdateTime())) {
                log.info("Refreshing data in 5 minutes by calling external API");
                geoData = client.target(getURL + ipAddress).request().get().readEntity(Geolocation.class);
            }
            geolocationDTO = GeolocationAdapter.get(geoData);
        } catch (Exception c) {
            log.info("External API is called to search IP address: " + ipAddress);
            geoData = client.target(getURL + ipAddress).request().get().readEntity(Geolocation.class);
            if (geoData == null) {
                System.out.println("not found in api");
            }
            if (geoData.getStatus().equalsIgnoreCase("success")) {
                saveGeoData(ipAddress, geoData);
            }
            geolocationDTO = GeolocationAdapter.get(geoData);
        }
        return geolocationDTO;
    }

//    @Override
    public void saveGeoData(String ipAddress, Geolocation geolocation) {
        log.info("save geolocation data in database with ip address: " + ipAddress);
        geolocation.setIpAddress(ipAddress);
        geoIPLocationDAO.save(geolocation);
    }

//    @Override
    public Geolocation getGeoDataFromDB(String ipAddress) {
        log.info("Database is called to search Ip address: " + ipAddress);
        Geolocation geolocationOpt = geoIPLocationDAO.findByIpAddress(ipAddress);
        if (geolocationOpt != null) {
            log.info("Record found in database");
        } else {
            log.info("Record not found in database: need to call external API");
//            throw new Exception("Ip address not present in database");
            System.out.println("Ip address not present in database");
        }
        return geolocationOpt;
    }

}
