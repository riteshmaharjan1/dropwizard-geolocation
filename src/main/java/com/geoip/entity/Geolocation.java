package com.geoip.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Geolocation {
    @Id
    private String ipAddress;

    private String query;
    private String status;
    private String countryCode;
    private String country;
    private String region;
    private String regionName;
    private String city;
    private String zip;
    private String timeZone;
    private float lon;
    private float lat;
    private String isp;
    private String org;
    @Column(name = "_as")
    private String as;

    @CreationTimestamp
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
