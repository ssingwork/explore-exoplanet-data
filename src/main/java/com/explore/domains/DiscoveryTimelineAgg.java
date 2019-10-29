package com.explore.domains;

public class DiscoveryTimelineAgg {
    private int year;
    private String size;
    private long planetCount;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getPlanetCount() {
        return planetCount;
    }

    public void setPlanetCount(long planetCount) {
        this.planetCount = planetCount;
    }

    @Override
    public String toString() {
        return "DiscoveryTimelineAgg{" +
                "year=" + year +
                ", size='" + size + '\'' +
                ", planetCount=" + planetCount +
                '}';
    }
}
