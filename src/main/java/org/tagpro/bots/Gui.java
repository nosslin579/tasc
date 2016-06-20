package org.tagpro.bots;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.tagpro.tasc.Command;
import org.tagpro.tasc.GameSubscriber;
import org.tagpro.tasc.data.Key;
import org.tagpro.tasc.data.KeyState;
import org.tagpro.tasc.data.Update;
import org.tagpro.tasc.starter.Starter;

import java.util.HashMap;
import java.util.Map;

public class Gui extends Application implements GameSubscriber, Command.KeyObserver {

    public static final int VECTOR_MIN_WIDTH = 40;
    private int id;
    private Label rx, ry, lx, ly, up, down, left, right;

    @Override
    public void start(Stage primaryStage) {
        int rowIndex = 0, columnIndex = 0;
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label header = new Label("Pos & Vel");
        grid.add(header, columnIndex, rowIndex++, 2, 1);

        grid.add(new Label("rx"), columnIndex, rowIndex);
        rx = new Label("n/a");
        grid.add(rx, columnIndex + 1, rowIndex++);

        grid.add(new Label("ry"), columnIndex, rowIndex);
        ry = new Label("n/a");
        grid.add(ry, columnIndex + 1, rowIndex++);

        grid.add(new Label("lx"), columnIndex, rowIndex);
        lx = new Label("n/a");
        grid.add(lx, columnIndex + 1, rowIndex++);

        grid.add(new Label("ly"), columnIndex, rowIndex);
        ly = new Label("n/a");
        grid.add(ly, columnIndex + 1, rowIndex++);

        up = new Label("-");
        up.setMinWidth(40);
        grid.add(up, columnIndex + 1, rowIndex++);

        right = new Label("-");
        right.setMinWidth(40);
        grid.add(right, columnIndex + 2, rowIndex);

        left = new Label("-");
        left.setMinWidth(40);
        grid.add(left, columnIndex, rowIndex++);

        down = new Label("-");
        grid.add(down, columnIndex + 1, rowIndex++);


        primaryStage.setTitle("Bot display");
        primaryStage.setScene(new Scene(grid, 700, 300));
        primaryStage.show();
    }

    @Override
    public void onUpdate(int step, Map<Integer, Update> updates) {
        Update self = updates.get(id);
        if (self != null && self.getBallUpdate() != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lx.setText(String.valueOf(self.getBallUpdate().getLx()));
                    ly.setText(String.valueOf(self.getBallUpdate().getLy()));
                    rx.setText(String.valueOf(self.getBallUpdate().getRx()));
                    ry.setText(String.valueOf(self.getBallUpdate().getRy()));
                }
            });
        }
    }

    @Override
    public void onId(int id) {
        this.id = id;
    }

    @Override
    public void onPreConnect() {
        //Someone is using a swing component
        //http://stackoverflow.com/questions/11273773/javafx-2-1-toolkit-not-initialized
        new JFXPanel();
        Platform.runLater(() -> start(new Stage()));
    }

    public static Gui create(Starter starter) {
        Gui ret = new Gui();
        starter.getCommand().addObserver(ret);
        return ret;
    }

    @Override
    public void keyChanged(Key key, KeyState keyState, int count) {
        if (key == Key.SPACE) {
            return;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Map<Key, Label> m = new HashMap<>(4);
                m.put(Key.LEFT, left);
                m.put(Key.RIGHT, right);
                m.put(Key.DOWN, down);
                m.put(Key.UP, up);
                m.get(key).setText(keyState.isPushed() ? key.name() : "--");
            }
        });
    }
}