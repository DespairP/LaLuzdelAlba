package teamHTBP.LaLuzdelAlba.JsonGenerator.Helper;

public enum EnumAxisDirections {
    X(90,90,"x"),Y(0,0,"y"),Z(90,0,"z");
    public int x = 0;
    public int y = 0;
    public String side;
    EnumAxisDirections(int x, int y,String side){
        this.x = x;
        this.y = y;
        this.side = side;
    }
}
