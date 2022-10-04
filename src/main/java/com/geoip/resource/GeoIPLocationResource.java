package com.geoip.resource;

import com.geoip.caching.GeolocationCaching;
import com.geoip.dao.GeoIPLocationDAO;
import com.geoip.entity.Geolocation;
import com.geoip.entity.dto.ApiResponse;
import com.geoip.entity.dto.GeolocationDTO;
import com.geoip.service.GeolocationService;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.http.HttpStatus;

import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/geolocation")
@Produces(MediaType.APPLICATION_JSON)
public class GeoIPLocationResource {
    private final GeolocationService geolocationService;

    public GeoIPLocationResource(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GET
    @Path("/{ipAddress}")
    @UnitOfWork
    public Response getGeolocation(@PathParam("ipAddress") String ipAddress) {
        GeolocationDTO geolocationDTO = GeolocationCaching.getInstance().getGeolocationDataFromCache(ipAddress);
        ApiResponse<Object, Object> response;
        if (geolocationDTO.getStatus().equalsIgnoreCase("success")) {
            response = ApiResponse.success(geolocationDTO, "Geolocation found");
        } else
            response = ApiResponse.failed(HttpStatus.SC_NOT_FOUND, "Geolocation not found in API");

        return Response.status(200).entity(response).build();
    }
}
