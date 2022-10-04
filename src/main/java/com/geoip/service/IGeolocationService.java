package com.geoip.service;

import com.geoip.entity.Geolocation;
import com.geoip.entity.dto.GeolocationDTO;

public interface IGeolocationService {
    public GeolocationDTO getGeoDataFromAPI(String ipAddress);

    public void saveGeoData(String ipAddress, Geolocation geolocation);

    public Geolocation getGeoDataFromDB(String ipAddress);
}
