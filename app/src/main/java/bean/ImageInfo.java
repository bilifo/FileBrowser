package bean;

public class ImageInfo {
//    int path = R.mipmap.ic_launcher;
    String name = "ssss";
    String path=null;

    public ImageInfo(){}
    public ImageInfo(String name,String path){
        this.name=name;
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path){
        this.path=path;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

}
