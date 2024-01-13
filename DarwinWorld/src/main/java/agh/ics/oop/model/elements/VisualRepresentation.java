package agh.ics.oop.model.elements;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class VisualRepresentation {
    private Color background;
    private Color border;
    private String text;
    private Image image;
    private double borderWidth;

    public VisualRepresentation() {
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }
}
