package com.webakruti.railwaytemperature.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 5/22/2019.
 */

public class DeviceDetails {

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("deviceid")
        @Expose
        private String deviceid;
        @SerializedName("doi")
        @Expose
        private String doi;
        @SerializedName("train_name")
        @Expose
        private String trainName;
        @SerializedName("coach_name")
        @Expose
        private String coachName;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("is_deleted")
        @Expose
        private String isDeleted;
        @SerializedName("devicelog_id")
        @Expose
        private String devicelogId;
        @SerializedName("temperature")
        @Expose
        private String temperature;
        @SerializedName("humidity")
        @Expose
        private String humidity;
        @SerializedName("external_temp")
        @Expose
        private String externalTemp;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("date")
        @Expose
        private String date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getDoi() {
            return doi;
        }

        public void setDoi(String doi) {
            this.doi = doi;
        }

        public String getTrainName() {
            return trainName;
        }

        public void setTrainName(String trainName) {
            this.trainName = trainName;
        }

        public String getCoachName() {
            return coachName;
        }

        public void setCoachName(String coachName) {
            this.coachName = coachName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getDevicelogId() {
            return devicelogId;
        }

        public void setDevicelogId(String devicelogId) {
            this.devicelogId = devicelogId;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getExternalTemp() {
            return externalTemp;
        }

        public void setExternalTemp(String externalTemp) {
            this.externalTemp = externalTemp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }


        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }


}
