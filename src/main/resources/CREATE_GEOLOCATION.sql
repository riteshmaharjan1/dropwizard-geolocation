CREATE TABLE geolocation (
                             ipAddress varchar(255) NOT NULL PRIMARY KEY,
                             _as varchar(255) DEFAULT NULL,
                             city varchar(255) DEFAULT NULL,
                             country varchar(255) DEFAULT NULL,
                             countryCode varchar(255) DEFAULT NULL,
                             creationTime timestamp DEFAULT NULL,
                             isp varchar(255) DEFAULT NULL,
                             lat float NOT NULL,
                             lon float NOT NULL,
                             org varchar(255) DEFAULT NULL,
                             query varchar(255) DEFAULT NULL,
                             region varchar(255) DEFAULT NULL,
                             regionName varchar(255) DEFAULT NULL,
                             status varchar(255) DEFAULT NULL,
                             timeZone varchar(255) DEFAULT NULL,
                             updateTime timestamp DEFAULT NULL,
                             zip varchar(255) DEFAULT NULL
)
