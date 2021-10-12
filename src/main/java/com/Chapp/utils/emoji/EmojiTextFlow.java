package com.Chapp.utils.emoji;

import com.Chapp.App;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Queue;

/**
 * Esta clase ha sido Joseada y modificada de https://github.com/pavlobu/emoji-text-flow-javafx
 */
public class EmojiTextFlow extends TextFlow {
    private static final Logger logger = LoggerFactory.getLogger(EmojiTextFlow.class);

    private final EmojiTextFlowParameters parameters;

    public EmojiTextFlow(EmojiTextFlowParameters parameters) {
        this.parameters = parameters;
        if (parameters.getTextAlignment() != null) {
            this.setTextAlignment(parameters.getTextAlignment());
        }
    }

    public void parseAndAppend(String message) {
        Queue<Object> obs = EmojiParser.getInstance().toEmojiAndText(message);
        while (!obs.isEmpty()) {
            Object ob = obs.poll();
            if (ob instanceof String) {
                addTextNode((String) ob);
                continue;
            }
            if (ob instanceof Emoji emoji) {
                try {
                    addEmojiImageNode(createEmojiImageNode(emoji));
                } catch (NullPointerException e) {
                    logger.error("Image with hex code: " + emoji.getHex() + " appear not to exist in resources path");
                    addTextNode(emoji.getUnicode());
                }
            }
        }
    }

    private ImageView createEmojiImageNode(Emoji emoji) throws NullPointerException {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(parameters.getEmojiFitWidth());
        imageView.setFitHeight(parameters.getEmojiFitHeight());
        imageView.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(emoji.getHex())));
        return imageView;
    }

    private void addTextNode(String text) {
        Text textNode = new Text();
        textNode.setText(text);
        textNode.setFont(parameters.getFont());
        if (parameters.getTextColor() != null) {
            textNode.setFill(parameters.getTextColor());
        }
        textNode.maxHeight(parameters.getFont().getSize());
        this.getChildren().add(textNode);
    }

    private void addEmojiImageNode(ImageView emojiImageNode) {
        this.getChildren().add(emojiImageNode);
    }

    private String getEmojiImagePath(String hexStr) throws NullPointerException {
        return Objects.requireNonNull(App.class.getResource("emotes/" + hexStr + ".png")).toExternalForm();
    }
}
