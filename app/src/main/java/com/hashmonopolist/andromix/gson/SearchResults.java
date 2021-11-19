package com.hashmonopolist.andromix.gson;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SearchResults {
    @Expose
    Tracks TRACK;
    @Expose
    Artists ARTIST;
    @Expose
    Albums ALBUM;

    public class Tracks {
        @Expose
        List<Track> data;
        public class Track {
            @Expose
            String SNG_ID;
            @Expose
            String SNG_TITLE;
            @Expose
            String ART_NAME;
            @Expose
            String ALB_PICTURE;

            public String getSNG_ID() {
                return SNG_ID;
            }

            public String getSNG_TITLE() {
                return SNG_TITLE;
            }

            public String getART_NAME() {
                return ART_NAME;
            }

            public String getALB_PICTURE() {
                return "https://e-cdns-images.dzcdn.net/images/cover/"+ALB_PICTURE+"/156x156-000000-80-0-0.jpg";
            }
        }
        public List<Track> getData() {
            return data;
        }
    }
    public class Albums {
        @Expose
        List<Album> data;
        public class Album {
            @Expose
            String ALB_ID;
            @Expose
            String ALB_TITLE;
            @Expose
            String ALB_PICTURE;
            @Expose
            String ART_NAME;

            public String getALB_ID() {
                return ALB_ID;
            }

            public String getALB_TITLE() {
                return ALB_TITLE;
            }

            public String getALB_PICTURE() {
                return "https://e-cdns-images.dzcdn.net/images/cover/"+ALB_PICTURE+"/156x156-000000-80-0-0.jpg";
            }

            public String getART_NAME() {
                return ART_NAME;
            }
        }

        public List<Album> getData() {
            return data;
        }
    }
    public class Artists {
        @Expose
        List<Artist> data;
        public class Artist {
            @Expose
            String ART_ID;
            @Expose
            String ART_NAME;
            @Expose
            String ART_PICTURE;

            public String getART_ID() {
                return ART_ID;
            }

            public String getART_NAME() {
                return ART_NAME;
            }

            public String getART_PICTURE() {
                return "https://e-cdns-images.dzcdn.net/images/artist/"+ART_PICTURE+"/156x156-000000-80-0-0.jpg";
            }
        }

        public List<Artist> getData() {
            return data;
        }
    }
    public Tracks getTRACK() {
        return TRACK;
    }

    public Albums getALBUM() {
        return ALBUM;
    }

    public Artists getARTIST() {
        return ARTIST;
    }
}

