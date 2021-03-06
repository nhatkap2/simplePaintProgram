package main.view;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class View {

    private final int SCENE_WIDTH = 1200;
    private final int SCENE_HEIGHT = 680;
    private final int X_MENUBAR = 0, Y_MENUBAR = 0;
    private final int W_MENUBAR = SCENE_WIDTH + 10, H_MENUBAR = 25;
    private final int X_TOOLBAR = 0, Y_TOOLBAR = H_MENUBAR;
    private final int W_TOOLBAR = 90, H_TOOLBAR = SCENE_HEIGHT - H_MENUBAR + 10;
    private final int X_PAINTPANE = W_TOOLBAR, Y_PAINTPANE = H_MENUBAR;
    private final int W_PAINTPANE = SCENE_WIDTH - W_TOOLBAR + 10, H_PAINTPANE = SCENE_HEIGHT - H_MENUBAR + 10;

    private Scene scene;
    private Pane backgroundPane;

    private PaintArea paintScrollPane;

    private MenuBarView menuBarView;

    private ToolBarView toolBarView;

    private SavingConfirmationDialog savingConfirmationDialog;

    private final AboutWindow aboutWindow;

    public View() {
        // khởi tạo scene, backgroundPane và primaryStage
        //<editor-fold defaultstate="collapsed" desc="Construct scene, stage, bgPane">
        backgroundPane = new Pane();
        backgroundPane.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
        scene = new Scene(backgroundPane, SCENE_WIDTH, SCENE_HEIGHT);
        //</editor-fold>
        // tạo menu bar
        //<editor-fold defaultstate="collapsed" desc="Menu Bar">
        menuBarView = new MenuBarView();
        menuBarView.setPrefSize(W_MENUBAR, H_MENUBAR);
        menuBarView.setLayoutX(X_MENUBAR);
        menuBarView.setLayoutY(Y_MENUBAR);
        backgroundPane.getChildren().add(menuBarView);
        //</editor-fold>
        // tạo vùng vẽ
        //<editor-fold defaultstate="collapse" desc="Paint Pane">

        paintScrollPane = new PaintArea();

        paintScrollPane.setPrefSize(W_PAINTPANE, H_PAINTPANE);
        paintScrollPane.setLayoutX(X_PAINTPANE);
        paintScrollPane.setLayoutY(Y_PAINTPANE);

        backgroundPane.getChildren().add(paintScrollPane);
        //</editor-fold>
        // tạo vùng chức năng 
        //<editor-fold defaultstate="collapse" desc="Tool Pane">
        toolBarView = new ToolBarView();
        toolBarView.setPrefSize(W_TOOLBAR, H_TOOLBAR);
        toolBarView.setLayoutX(X_TOOLBAR);
        toolBarView.setLayoutY(Y_TOOLBAR);

        backgroundPane.getChildren().add(toolBarView);
        //</editor-fold>

        savingConfirmationDialog = new SavingConfirmationDialog();

        aboutWindow = new AboutWindow();

        showCurrentImageSizeOnScreen((int) paintScrollPane.getPaintPaneWidth(), (int) paintScrollPane.getPaintPaneHeight());
    }

    public void newMenuAction(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInNewMenuItem(eventHandler);
    }

    public void openImageFromOutside(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInOpenMenuItem(eventHandler);
    }

    public void exitMenuAction(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInExitMenuItem(eventHandler);
    }

    public void saveAsMenuAction(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInSaveAsMenuItem(eventHandler);
    }

    public void undoMenuAction(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInUndoMenuItem(eventHandler);
    }

    public void redoMenuAction(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInRedoMenuItem(eventHandler);
    }

    public void pasteMenuAction(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInPasteMenuItem(eventHandler);
    }

    public void saveMenuAction(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInSaveMenuItem(eventHandler);
    }

    public void aboutMenuAcion(EventHandler<ActionEvent> eventHandler) {
        menuBarView.addEventHandlerInAboutMenuItem(eventHandler);
    }

    public <T extends Event> void addEventHandlerInScene(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        scene.addEventFilter(eventType, eventHandler);
    }

    public void addListenerInToggleGroup(ChangeListener<Toggle> listener) {
        toolBarView.addListenerInToggleGroup(listener);
    }

    public void addListenerInColorChooser(ChangeListener<Color> listener) {
        toolBarView.addListenerInColorChooser(listener);
    }

    public void addListenerInSlider(ChangeListener<Number> listener) {
        toolBarView.addListenerInSlider(listener);
    }

    public void setColorInColorChooser(Color color) {
        toolBarView.setColorInColorChooser(color);
    }

    // các hàm liên quan đến Paint Pane
    //<editor-fold defaultstate="collapsed" desc="Paint Pane Funtion">
    public <T extends Event> void addZoomableScrollPaneEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        paintScrollPane.addZoomNodeEventHandler(eventType, eventHandler);
    }

    public void zoomPaintPane(double wheelDelta, Point2D mousePoint) {
        paintScrollPane.zoomOnTarget(wheelDelta, mousePoint);
    }

    public void setSizePaintPane(double width, double height) {
        paintScrollPane.setSizePaintPane(width, height);
    }

    public <T extends Event> void addEventHandlerIntoPaintPane(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        paintScrollPane.addPaintPaneEventHandler(eventType, eventHandler);
    }

    public WritableImage getImageOfPane() {
        return paintScrollPane.getImageOfPane();
    }

    public WritableImage getCropImageOfPane(int x, int y, int width, int height) {
        return paintScrollPane.getCropImageOfPane(x, y, width, height);
    }

    public void addNodeToPaintPane(Node... nodes) {
        paintScrollPane.addNodeToPaintPane(nodes);
    }

    public void removeAllNodePaintPane() {
        paintScrollPane.removeAllNodePaintPane();
    }

    public boolean isPaintPaneEmpty() {
        return paintScrollPane.isPaintPaneEmpty();
    }

    public double getPaintPaneWidth() {
        return paintScrollPane.getPaintPaneWidth();
    }

    public double getPaintPaneHeight() {
        return paintScrollPane.getPaintPaneHeight();
    }

    public void setImageOfCursorInPaintPane(ImageCursor imageCursor) {
        paintScrollPane.setImageOfCursorInPaintPane(imageCursor);
    }
    //</editor-fold>

    public Scene getScene() { // mình ko thích cái hàm này lắm
        return scene;
    }

    public void showSavingConfirmationMessage() {
        savingConfirmationDialog.showToUser();
    }

    public boolean doUserPressYesInSavingConfirmationMessage() {
        return savingConfirmationDialog.doUserPressYes();
    }

    public boolean doUserPressNoInSavingConfirmationMessage() {
        return savingConfirmationDialog.doUserPressNo();
    }

    public void showCurrentPositionOfMouseOnScreen(int x, int y) {
        toolBarView.showCurrentPositionOfMouseOnScreen(x, y);
    }

    public void showCurrentImageSizeOnScreen(int x, int y) {
        toolBarView.showCurrentImageSizeOnScreen(x, y);
    }

    public void displayAboutWindow() {
        aboutWindow.show();
    }

    public void exitAboutWindow() {
        aboutWindow.close();
    }
}
