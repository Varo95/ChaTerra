package com.Chapp.view;


import com.Chapp.models.beans.Message;
import com.Chapp.utils.emoji.EmojiTextFlow;
import com.Chapp.utils.emoji.EmojiTextFlowParameters;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;

/**
 * Esta clase sirve para poder pintar los mensajes en la columna de la vista de la Room
 * Esto es debido a que la implementación de emoticonos están en un TextFlow que no es un ObservableValue
 * Y por lo tanto no se puede insertar en cada celda, por eso tuve que hacer este carajote bueno
 */
public class TableRowMessage extends EmojiTextFlow implements ObservableValue<EmojiTextFlow> {
    //Se modifica el tamaño de la columna porque al ser nodos, la tabla los trata como tal y añade más espacio
    //vertical cuando estos nodos se unen, esto soluciona el problema de la vista para que no salgan mensajes
    //con las celdas MUY anchas.
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
