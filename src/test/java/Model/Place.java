package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {
    String placename;
    String longitude;
    String state;
    String stateabbreviation;
    String latitude;

    public String getPlacename() {
        return placename;
    }

    @JsonProperty("place name")
    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateabbreviation() {
        return stateabbreviation;
    }

    @JsonProperty("state abbreviation")
    public void setStateabbreviation(String stateabbreviation) {
        this.stateabbreviation = stateabbreviation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Place\n{" +
                "\n\tplacename='" + placename + '\'' +
                ",\n\tlongitude='" + longitude + '\'' +
                ",\n\tstate='" + state + '\'' +
                ",\n\tstateabbreviation='" + stateabbreviation + '\'' +
                ",\n\tlatitude='" + latitude + '\'' +
                "\n}";
    }
}
