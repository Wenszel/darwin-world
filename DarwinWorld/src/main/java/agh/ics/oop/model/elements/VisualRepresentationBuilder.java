package agh.ics.oop.model.elements;

import agh.ics.oop.model.Builder;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class VisualRepresentationBuilder implements Builder<VisualRepresentation> {
    private Color background;
    private Color border;
    private String text;
    private Image image;
    private double borderWidth = 1;

    public VisualRepresentationBuilder setBackground(Color background) {
        this.background = background;
        return this;
    }

    public VisualRepresentationBuilder setBorder(Color border) {
        this.border = border;
        return this;
    }

    public VisualRepresentationBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public VisualRepresentationBuilder setImage(Image image) {
        this.image = image;
        return this;
    }

    public VisualRepresentationBuilder setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }
    @Override
    public VisualRepresentation build() {
        VisualRepresentation visualRepresentation = new VisualRepresentation();
        visualRepresentation.setBackground(background);
        visualRepresentation.setBorder(border);
        visualRepresentation.setText(text);
        visualRepresentation.setImage(image);
        visualRepresentation.setBorderWidth(borderWidth);
        return visualRepresentation;
    }
}
