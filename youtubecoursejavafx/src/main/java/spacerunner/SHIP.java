package spacerunner;

public enum SHIP {
    BLUE("file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerShip3_blue.png", "file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerLife2_blue.png"),
    GREEN("file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerShip3_green.png", "file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerLife2_green.png"),
    ORANGE("file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerShip3_orange.png", "file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerLife2_orange.png"),
    RED("file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerShip3_red.png", "file:youtubecoursejavafx/src/main/java/spacerunner/resources/playerLife2_red.png");
    
    private String urlShip;
    private String urlLife;
    
    private SHIP(String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrl() {
        return this.urlShip;
    }
    public String getUrlLife(){
        return urlLife;
    }
}
