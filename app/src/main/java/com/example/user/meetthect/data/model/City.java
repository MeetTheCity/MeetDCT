package com.example.user.meetthect.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static com.example.user.meetthect.data.model.City.CITIES_TABLE;

/**
 * Created by artyomokun on 09/01/2018.
 */

@SuppressWarnings("unused")
@Entity(tableName = CITIES_TABLE)
public class City implements Parcelable {

    public static final String CITIES_TABLE = "citiesTable";
    private static final String CITIES_ID_COLUMN = "citiesIdColumn";
    private static final String CITIES_NAME_COLUMN = "citiesNameColumn";
    private static final String CITIES_LAT_COLUMN = "citiesLatColumn";
    private static final String CITIES_LON_COLUMN = "citiesLonColumn";
    private static final String CITIES_COUNTRY_COLUMN = "citiesCountryColumn";
    private static final String CITIES_ISO_2_COLUMN = "citiesIso2Column";


    public City() {
        // required by room
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = CITIES_ID_COLUMN)
    private String id = "";

    @ColumnInfo(name = CITIES_NAME_COLUMN)
    private String cityName;

    @ColumnInfo(name = CITIES_LAT_COLUMN)
    private Double lat;

    @ColumnInfo(name = CITIES_LON_COLUMN)
    private Double lon;

    @ColumnInfo(name = CITIES_COUNTRY_COLUMN)
    private String country;

    @ColumnInfo(name = CITIES_ISO_2_COLUMN)
    private String iso2;

    private City(Builder builder) {
        setCityName(builder.cityName);
        setLat(builder.lat);
        setLon(builder.lon);
        setCountry(builder.country);
        setIso2(builder.iso2);
        setId(builder.id);
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", cityName='" + cityName + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", country='" + country + '\'' +
                ", iso2='" + iso2 + '\'' +
                '}';
    }

    public static ICityName builder() {
        return new Builder();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public interface IBuild {

        City build();
    }

    public interface IIso2 {

        IBuild withIso2(String val);
    }

    public interface ICountry {

        IIso2 withCountry(String val);
    }

    public interface ILon {

        ICountry withLon(Double val);
    }

    public interface ILat {

        ILon withLat(Double val);
    }

    public interface ICityName {

        ILat withCityName(String val);
    }

    public static final class Builder implements IIso2, ICountry, ILon, ILat, ICityName, IBuild {
        private String id;
        private String iso2;
        private String country;
        private Double lon;
        private Double lat;
        private String cityName;

        private Builder() {
        }

        @Override
        public IBuild withIso2(String val) {
            iso2 = val;
            return this;
        }

        @Override
        public IIso2 withCountry(String val) {
            country = val;
            return this;
        }

        @Override
        public ICountry withLon(Double val) {
            lon = val;
            return this;
        }

        @Override
        public ILon withLat(Double val) {
            lat = val;
            return this;
        }

        @Override
        public ILat withCityName(String val) {
            cityName = val;
            return this;
        }

        public City build() {
            id = cityName + iso2;
            return new City(this);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cityName);
        dest.writeValue(this.lat);
        dest.writeValue(this.lon);
        dest.writeString(this.country);
        dest.writeString(this.iso2);
    }

    protected City(Parcel in) {
        this.id = in.readString();
        this.cityName = in.readString();
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.country = in.readString();
        this.iso2 = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {

        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
