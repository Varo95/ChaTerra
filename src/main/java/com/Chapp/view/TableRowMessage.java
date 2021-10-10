package com.Chapp.view;


import com.Chapp.models.beans.Message;
import com.Chapp.utils.emoji.EmojiTextFlow;
import com.Chapp.utils.emoji.EmojiTextFlowParameters;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;

public class TableRowMessage extends EmojiTextFlow implements ObservableValue<EmojiTextFlow> {


    public TableRowMessage(EmojiTextFlowParameters parameters, String s, TableColumn<Message,EmojiTextFlow> tc) {
        super(parameters);
        parseAndAppend(s);
        setPrefHeight(this.prefHeight(tc.getWidth()) + 4);
        tc.widthProperty().addListener((v, o, n) ->
                setPrefHeight(this.prefHeight(n.doubleValue()) + 4));
    }

    @Override
    public EmojiTextFlow getValue() {
        return this;
    }

    @Override
    public void addListener(ChangeListener<? super EmojiTextFlow> changeListener) {

    }

    @Override
    public void removeListener(ChangeListener<? super EmojiTextFlow> changeListener) {

    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
