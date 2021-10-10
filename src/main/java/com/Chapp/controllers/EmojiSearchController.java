package com.Chapp.controllers;

import com.Chapp.App;
import com.Chapp.utils.emoji.Emoji;
import com.Chapp.utils.emoji.EmojiImageCache;
import com.Chapp.utils.emoji.EmojiParser;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EmojiSearchController {

    private static final boolean SHOW_MISC = false;
    @FXML
    private ScrollPane searchScrollPane;
    @FXML
    private FlowPane searchFlowPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<Image> boxTone;
    //Este text Area hace referencia al textArea de la sala
    private static TextArea textArea;

    @FXML
    void initialize() {
        if (!SHOW_MISC) {
            tabPane.getTabs().remove(tabPane.getTabs().size() - 2, tabPane.getTabs().size());
        }
        ObservableList<Image> tonesList = FXCollections.observableArrayList();

        for (int i = 1; i <= 5; i++) {
            Emoji emoji = EmojiParser.getInstance().getEmoji(":thumbsup_tone" + i + ":");
            Image image = EmojiImageCache.getInstance().getImage(getEmojiImagePath(emoji.getHex()));
            tonesList.add(image);
        }
        Emoji em = EmojiParser.getInstance().getEmoji(":thumbsup:"); //default tone
        Image image = EmojiImageCache.getInstance().getImage(getEmojiImagePath(em.getHex()));
        tonesList.add(image);
        boxTone.setItems(tonesList);
        boxTone.setCellFactory(e -> new ToneCell());
        boxTone.setButtonCell(new ToneCell());
        boxTone.getSelectionModel().selectedItemProperty().addListener(e -> refreshTabs());


        searchScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        searchFlowPane.prefWidthProperty().bind(searchScrollPane.widthProperty().subtract(5));
        searchFlowPane.setHgap(5);
        searchFlowPane.setVgap(5);

        txtSearch.textProperty().addListener(x -> {
            String text = txtSearch.getText();
            if (text.length() < 2) {
                searchFlowPane.getChildren().clear();
                searchScrollPane.setVisible(false);
            } else {
                searchScrollPane.setVisible(true);
                List<Emoji> results = EmojiParser.getInstance().search(text);
                searchFlowPane.getChildren().clear();
                results.forEach(emoji -> searchFlowPane.getChildren().add(createEmojiNode(emoji)));
            }
        });


        for (Tab tab : tabPane.getTabs()) {
            ScrollPane scrollPane = (ScrollPane) tab.getContent();
            FlowPane pane = (FlowPane) scrollPane.getContent();
            pane.setPadding(new Insets(5));
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            pane.prefWidthProperty().bind(scrollPane.widthProperty().subtract(5));
            pane.setHgap(5);
            pane.setVgap(5);

            tab.setId(tab.getText());
            ImageView icon = new ImageView();
            icon.setFitWidth(20);
            icon.setFitHeight(20);
            switch (tab.getText().toLowerCase()) {
                case "frequently used" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":heart:").getHex())));
                case "people" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":smiley:").getHex())));
                case "nature" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":dog:").getHex())));
                case "food" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":apple:").getHex())));
                case "activity" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":soccer:").getHex())));
                case "travel" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":airplane:").getHex())));
                case "objects" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":bulb:").getHex())));
                case "symbols" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":atom:").getHex())));
                case "flags" -> icon.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(EmojiParser.getInstance().getEmoji(":flag_eg:").getHex())));
            }

            if (icon.getImage() != null) {
                tab.setText("");
                tab.setGraphic(icon);
            }

            tab.setTooltip(new Tooltip(tab.getId()));
            tab.selectedProperty().addListener(ee -> {
                if (tab.getGraphic() == null) return;
                if (tab.isSelected()) {
                    tab.setText(tab.getId());
                } else {
                    tab.setText("");
                }
            });
        }
        boxTone.getSelectionModel().select(0);
        tabPane.getSelectionModel().select(1);
    }

    public static void setTextArea(TextArea ta) {
        textArea = ta;
    }

    private void refreshTabs() {
        Map<String, List<Emoji>> map = EmojiParser.getInstance().getCategorizedEmojis(boxTone.getSelectionModel().getSelectedIndex() + 1);
        for (Tab tab : tabPane.getTabs()) {
            ScrollPane scrollPane = (ScrollPane) tab.getContent();
            FlowPane pane = (FlowPane) scrollPane.getContent();
            pane.getChildren().clear();
            String category = tab.getId().toLowerCase();
            if (map.get(category) == null) continue;
            map.get(category).forEach(emoji -> pane.getChildren().add(createEmojiNode(emoji)));
        }
    }

    private Node createEmojiNode(Emoji emoji) {
        StackPane stackPane = new StackPane();
        stackPane.setMaxSize(32, 32);
        stackPane.setPrefSize(32, 32);
        stackPane.setMinSize(32, 32);
        stackPane.setPadding(new Insets(3));
        ImageView imageView = new ImageView();
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        try {
            imageView.setImage(EmojiImageCache.getInstance().getImage(getEmojiImagePath(emoji.getHex())));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stackPane.getChildren().add(imageView);

        Tooltip tooltip = new Tooltip(emoji.getShortname());
        Tooltip.install(stackPane, tooltip);
        stackPane.setCursor(Cursor.HAND);
        ScaleTransition st = new ScaleTransition(Duration.millis(90), imageView);

        stackPane.setOnMouseEntered(e -> {
            imageView.setOnMouseClicked(mouseEvent -> {
                if (textArea != null)
                    textArea.setText(textArea.getText() + emoji.getUnicode());
            });
            imageView.setEffect(new DropShadow());
            st.setToX(1.2);
            st.setToY(1.2);
            st.playFromStart();
            if (txtSearch.getText().isEmpty())
                txtSearch.setPromptText(emoji.getShortname());
        });
        stackPane.setOnMouseExited(e -> {
            imageView.setEffect(null);
            st.setToX(1.);
            st.setToY(1.);
            st.playFromStart();
        });
        return stackPane;
    }

    public static String getEmojiImagePath(String hexStr) throws NullPointerException {
        return Objects.requireNonNull(App.class.getResource("emotes/" + hexStr + ".png")).toExternalForm();
    }


    static class ToneCell extends ListCell<Image> {
        private final ImageView imageView;

        public ToneCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            imageView = new ImageView();
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
        }

        @Override
        protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setGraphic(null);
            } else {
                imageView.setImage(item);
                setGraphic(imageView);
            }
        }
    }

}
