## Exoplanet Data Exploration
This small Spring Boot application download JSON dataset from `https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets` and try to answer following questions.
Created 3 api end point for following 3 question. 

- The number of orphan planets (no star).
    - As per this document (https://www.kaggle.com/mrisdal/open-exoplanet-catalogue), "TypeFlag" with value 3 are orphan planet. Blank values are ignored.
    - URL : http://localhost:9099/planets/orphan-count
- The name (planet identifier) of the planet orbiting the hottest star.
    - Find out maximum temperature of stars and find out how many planets orbiting star with that temperature.
    - URL : http://localhost:9099/planets/hottest-star
- A timeline of the number of planets discovered per year grouped by size.Use the following groups: “small” is less than 1 Jupiter radii, “medium” is less than 2 Jupiter radii, and anything bigger is considered “large”. For example, in 2004 we discovered 2 small planets, 5 medium planets, and 0 large planets.
    - Aggregate group by using Java stream's API and normalize to display.
    - URL : http://localhost:9099/planets/discovery-timeline-by-year-size

## Data Record Format
`
 {
    "PlanetIdentifier": "KOI-1843.03",
    "TypeFlag": 0,
    "PlanetaryMassJpt": 0.0014,
    "RadiusJpt": 0.054,
    "PeriodDays": 0.1768913,
    "SemiMajorAxisAU": 0.0048,
    "Eccentricity": "",
    "PeriastronDeg": "",
    "LongitudeDeg": "",
    "AscendingNodeDeg": "",
    "InclinationDeg": 72,
    "SurfaceTempK": "",
    "AgeGyr": "",
    "DiscoveryMethod": "transit",
    "DiscoveryYear": 2012,
    "LastUpdated": "13/07/15",
    "RightAscension": "19 00 03.14",
    "Declination": "+40 13 14.7",
    "DistFromSunParsec": "",
    "HostStarMassSlrMass": 0.46,
    "HostStarRadiusSlrRad": 0.45,
    "HostStarMetallicity": 0,
    "HostStarTempK": 3584,
    "HostStarAgeGyr": ""
  }
`
## How to run
    Download project from github repo, https://github.com/ssingwork/explore-exoplanet-data.git.  
     `
     cd c:/
     cd clone https://github.com/ssingwork/explore-exoplanet-data.git
     # code will be at c:/explore-exoplanet-data   
     `
    
    - Run on Windows Host
        - Go to project home from cmd prompt, i.e cd c:/explore-exoplanet-data
        - Make sure Java8+ installed and added on PATH
        - Run `mvnw clean install spring-boot:run`
        - Should build , test, and application serve on http://localhost:9099 port      
    - Run on Docker
         - Go to project home from cmd prompt, i.e cd c:/explore-exoplanet-data
         -  Make sure Docker & docker-compose installed, Linux Contener selected if Windows Host
         -  Run `run_docker.bat`, which will build Code,run test on docker container and run application with docker-compose  
         -  application should available on http://localhost:9099 port
    - Run on Kubernaties
         -  Go to project home from cmd prompt, i.e cd c:/explore-exoplanet-data
         -  Make sure kubectl installed and configure properly
         -  Will use Helm, for deploying kubernaties application. Make sure installed and initialized properly  
         -  Run `helm install exoplanet-chart` from cmd promt, which donwload docker image (https://cloud.docker.com/repository/docker/subhasatdocker/exoplanet-data) I already pushed to dockerhub.  
         -  As I choose NodePort (for easy testing) for service, application will be available o ramdom port on host. Instruction will be available after successfull helm install.
          
    
## Application Tour 
![Gif File is not loaded](./screenshots/application-tour.gif)

         