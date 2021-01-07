package client.tools;


import javafx.scene.image.ImageView;

public class ListData {
    private String name;
    private ImageView imageView;

    public ListData(String name, ImageView imageView) {
        this.name = name;
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
