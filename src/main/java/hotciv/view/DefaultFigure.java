package hotciv.view;

import minidraw.standard.ImageFigure;

import java.awt.*;

public class DefaultFigure extends ImageFigure {
    private final String typeString;

    public DefaultFigure(String typeString) {
        this.typeString = typeString;
    }

    public DefaultFigure(Image image, Point position, String typeString) {
        super(image, position);
        this.typeString = typeString;
    }

    public DefaultFigure(String imagename, Point position, String typeString) {
        super(imagename, position);
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }
}