package com.explore.services;

import com.explore.domains.DiscoveryTimelineAgg;
import com.explore.domains.Planet;

import java.util.List;

public interface DataManager {
    int getOrphanPlanetCount();
    List<Planet> findPlanetOrbitingHottestStar();
    List<DiscoveryTimelineAgg> getDiscoverTimelineByYear();

}
