package com.example.diana.ultimoproyecto;


public class Post{
    public String urlFoto;
    public String description;
    public String location;

    public Post(){

    }

    public Post(String urlFoto, String description, String location) {
        this.urlFoto = urlFoto;
        this.description = description;
        this.location = location;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
