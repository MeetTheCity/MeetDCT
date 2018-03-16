package com.example.user.meetthect.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static com.example.user.meetthect.data.model.City.CITIES_TABLE;

/**
 * Created by User on 3/16/2018.
 */

@SuppressWarnings("unused")
@Entity(tableName = CITIES_TABLE)
public class CountriesElectricity implements Parcelable {

    public static final String COUNTRIES_TABLE = "countriesTable";
    private static final String COUNTRIES_ID_COLUMN = "countriesIdColumn";
    private static final String COUNTRY_NAME_COLUMN = "countryNameColumn";
    private static final String COUNTRY_VOLTAGE_COLUMN = "countryVoltageColumn";
    private static final String COUNTRY_PLUG_COLUMN = "countryPlugColumn";


    public CountriesElectricity() {
        // required by room
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COUNTRIES_ID_COLUMN)
    private String id = "";

    @ColumnInfo(name = COUNTRY_NAME_COLUMN)
    private String countryName;

    @ColumnInfo(name = COUNTRY_VOLTAGE_COLUMN)
    private String voltage;

    @ColumnInfo(name = COUNTRY_PLUG_COLUMN)
    private String plug;

    private CountriesElectricity(Builder builder) {
        setCountryName(builder.countryName);
        setVoltage(builder.voltage);
        setPlug(builder.plug);
        setId(builder.id);
    }

    public static ICountryName builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@NonNull String countryName) {
        this.countryName = countryName;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getPlug() {
        return plug;
    }

    public void setPlug(String plug) {
        this.plug = plug;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.countryName);
        dest.writeString(this.voltage);
        dest.writeString(this.plug);
    }

    protected CountriesElectricity(Parcel in) {
        this.id = in.readString();
        this.countryName = in.readString();
        this.voltage = in.readString();
        this.plug = in.readString();
    }

    public static final Creator<CountriesElectricity> CREATOR = new Creator<CountriesElectricity>() {
        @Override
        public CountriesElectricity createFromParcel(Parcel source) {
            return new CountriesElectricity(source);
        }

        @Override
        public CountriesElectricity[] newArray(int size) {
            return new CountriesElectricity[size];
        }
    };

    public interface IBuild {
        IBuild withId(String val);

        CountriesElectricity build();
    }

    public interface IPlug {
        IBuild withPlug(String val);
    }

    public interface IVoltage {
        IPlug withVoltage(String val);
    }

    public interface ICountryName {
        IVoltage withCountryName(String val);
    }

    public static final class Builder implements IPlug, IVoltage, ICountryName, IBuild {
        private String id;
        private String plug;
        private String voltage;
        private String countryName;

        private Builder() {
        }

        @Override
        public IBuild withPlug(String val) {
            plug = val;
            return this;
        }

        @Override
        public IPlug withVoltage(String val) {
            voltage = val;
            return this;
        }

        @Override
        public IVoltage withCountryName(String val) {
            countryName = val;
            return this;
        }

        @Override
        public IBuild withId(String val) {
            id = val;
            return this;
        }

        public CountriesElectricity build() {
            return new CountriesElectricity(this);
        }
    }
}


