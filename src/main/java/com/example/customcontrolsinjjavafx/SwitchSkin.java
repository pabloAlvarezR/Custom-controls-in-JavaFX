package com.example.customcontrolsinjjavafx;

import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class SwitchSkin extends SkinBase<CustomControl> implements Skin<CustomControl> {
    private static final double PREFERRED_WIDTH = 76;
    private static final double PREFERRED_HEIGHT = 46;
    private Region switchBackground;
    private Region thumb;
    private Pane pane;
    private TranslateTransition translate;
    private CustomControl control;
    private InvalidationListener colorListener;
    private InvalidationListener state;
    private EventHandler<MouseEvent> mouseEventHandler;
    // ******************** Constructors **************************************
    public SwitchSkin(final CustomControl control) {
        super(control);
        this.control = control;
        colorListener = o -> handleControlPropertyChanged("COLOR");
        state = o -> handleControlPropertyChanged("STATE");
        mouseEventHandler = e -> this.control.setState(!this.control.getState());
        initGraphics();
        registerListeners();
    }
    // ******************** Initialization ************************************
    private void initGraphics() {
        switchBackground = new Region();
        switchBackground.getStyleClass().add("switch-background");
        switchBackground.setStyle(String.join("", "-color: ", control.getColor().toString().replace("0x", "#"), ";"));
        thumb = new Region();
        thumb.getStyleClass().add("thumb");
        thumb.setMouseTransparent(true);
        if (control.getState()) { thumb.setTranslateX(32); }
        translate = new TranslateTransition(Duration.millis(70), thumb);
        pane = new Pane(switchBackground, thumb);
        getChildren().add(pane);
    }
    private void registerListeners() {
        control.colorProperty().addListener(colorListener);
        control.stateProperty().addListener(state);
        switchBackground.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEventHandler);
    }
    // ******************** Methods *******************************************
    @Override public void layoutChildren(final double x, final double y, final double width, final double height) {
        super.layoutChildren(x, y, width, height);
        switchBackground.relocate((width - PREFERRED_WIDTH) * 0.5, (height - PREFERRED_HEIGHT) * 0.5);
        thumb.relocate((width - PREFERRED_WIDTH) * 0.5, (height - PREFERRED_HEIGHT) * 0.5);
    }
    protected void handleControlPropertyChanged(final String property) {
        if ("COLOR".equals(property)) {
            switchBackground.setStyle(String.join("", "-color: ", control.getColor().toString().replace("0x", "#"), ";"));
        } else if ("STATE".equals(property)) {
            if (control.getState()) {
                // move thumb to the right
                translate.setFromX(2);
                translate.setToX(32);
            } else {
                // move thumb to the left
                translate.setFromX(32);
                translate.setToX(2);
            }
            translate.play();
        }
    }
    @Override public void dispose() {
        control.colorProperty().removeListener(colorListener);
        control.stateProperty().removeListener(state);
        switchBackground.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseEventHandler);
    }
}