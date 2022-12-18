package com.spaarkz.jfx.fxpane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FXMLPane {
    private final Node node;
    private final FXMLLoader loader;

    public FXMLPane(Object controller) {
        Class<?> controllerClass = controller.getClass();

        String fxmlFile = controllerClass.getAnnotation(FxmlFile.class).value();
        Objects.requireNonNull(fxmlFile, "FXML File is not configured in Controller Class");

        URL fxmlFileURL = controllerClass.getResource(fxmlFile);
        Objects.requireNonNull(fxmlFileURL, "Unable to find FXML File " + fxmlFile);

        loader = new FXMLLoader(fxmlFileURL);
        loader.setControllerFactory(cls -> controller);
        loader.setClassLoader(controllerClass.getClassLoader());

        try {
            node = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Node node(Object controller) {
        return new FXMLPane(controller).node();
    }

    public <T> T node() {
        return (T) node;
    }

    public <T> T controller() {
        return loader.getController();
    }

    public FXMLLoader loader() {
        return loader;
    }
}