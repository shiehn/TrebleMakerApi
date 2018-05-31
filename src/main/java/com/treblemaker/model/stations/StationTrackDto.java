package com.treblemaker.model.stations;

public class StationTrackDto {

        public StationTrackDto(StationTrack stationTrack){
                this.setName(stationTrack.getFile());
                this.setStationId(stationTrack.getStationId());
        }

        private String name;

        private int stationId;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getStationId() {
                return stationId;
        }

        public void setStationId(int stationId) {
                this.stationId = stationId;
        }
}
