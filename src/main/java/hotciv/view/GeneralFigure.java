package hotciv.view;

import minidraw.standard.ImageFigure;

import java.awt.*;

public class GeneralFigure extends ImageFigure {
    private final String typeString;

    public GeneralFigure(String typeString) {
        this.typeString = typeString;
    }

    public GeneralFigure(Image image, Point position, String typeString) {
        super(image, position);
        this.typeString = typeString;
    }

    public GeneralFigure(String imagename, Point position, String typeString) {
        super(imagename, position);
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }
}