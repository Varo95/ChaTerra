package com.Chapp.utils.emoji;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Esta clase ha sido Joseada y modificada de https://github.com/pavlobu/emoji-text-flow-javafx
 */
public class EmojiTextFlowParameters {

    private TextAlignment textAlignment;
    private Font font;
    private Color textColor;
    private double emojiScaleFactor;

    public EmojiTextFlowParameters() {
        this.font = Font.getDefault();
        this.emojiScaleFactor = 1D;
        this.textColor = Color.BLACK;
        this.textAlignment = TextAlignment.LEFT;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public double getEmojiFitWidth() {
        return font.getSize() * emojiScaleFactor;
    }

    public double getEmojiFitHeight() {
        return font.getSize() * emojiScaleFactor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setEmojiScaleFactor(double emojiScaleFactor) {
        this.emojiScaleFactor = emojiScaleFactor;
    }

    public double getEmojiScaleFactor() {
        return emojiScaleFactor;
    }
}
