/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller;

import drawer.NotClassifiedTool.*;
import drawer.PaintTool.ToolType;
import drawer.Pens.*;
import drawer.Shapes.*;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.model.Model;
import main.view.View;

/**
 *
 * @author Admin
 */
public class Controller {

    private Model model;
    private View view;

    private BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
    private BooleanProperty keySPressed = new SimpleBooleanProperty(false);
    private BooleanBinding combineKey = ctrlPressed.and(keySPressed);

    private Pencil pencil;
    private Eraser eraser;
    private FloodFiller floodFiller;
    private RectangleDrawer rectangleDrawer;
    private CurveLineDrawer curveLineDrawer;
    private Airbrush airbrush;
    private Brush brush;
    private CalligraphyPen calligraphyPen;
    private MarkerPen markerPen;
    private EllipseDrawer ellipseDrawer;
    private IsoscelesTriangleDrawer isoscelesTriangle;
    private LineDrawer lineDrawer;
    private RoundedRectangleDrawer roundedRectangleDrawer;
    private SquareTriangleDrawer squareTriangleDrawer;

    private ToolType currentTool;

    public Controller(View v, Model m) {
        model = m;
        view = v;

        pencil = new Pencil(view.getPaintPane());
        currentTool = ToolType.PENCIL;

        eraser = new Eraser(view.getPaintPane());

        floodFiller = new FloodFiller(view.getPaintPane());

        rectangleDrawer = new RectangleDrawer(view.getPaintPane());
        
        curveLineDrawer = new CurveLineDrawer(view.getPaintPane());
        
        airbrush = new Airbrush(view.getPaintPane());
        
        brush = new Brush(view.getPaintPane());
        
        calligraphyPen = new CalligraphyPen(view.getPaintPane());

        exitMenuAction();
        saveAsMenuAction();
        saveWithKeyBoard();
        toggleBtnGroupAction();
        colorPickerAction();
        sizeOfPenSliderAction();
        paintPaneActionHandler();
    }

    private void paintPaneActionHandler() {
        view.getPaintPane().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (null != currentTool) {
                    switch (currentTool) {
                        case PENCIL:
                            pencil.mousePressedHandling(event);
                            break;
                        case ERASER:
                            eraser.mousePressedHandling(event);
                            break;
                        case FLOOD_FILLER:
                            floodFiller.mousePressedHandling(event);
                            break;
                        case RECTANGLE:
                            rectangleDrawer.mousePressedHandling(event);
                            break;
                        case CURVE_LINE:
                            curveLineDrawer.mousePressedHandling(event);
                            break;
                        case AIRBRUSH:
                            airbrush.mousePressedHandling(event);
                            break;
                        case BRUSH:
                            brush.mousePressedHandling(event);
                            break;
                        case CALLIGRAPHY_PEN:
                            calligraphyPen.mousePressedHandling(event);
                        default:
                            break;
                    }
                }
            }

        });
        view.getPaintPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (null != currentTool) {
                    switch (currentTool) {
                        case PENCIL:
                            pencil.mouseDraggedHandling(event);
                            break;
                        case ERASER:
                            eraser.mouseDraggedHandling(event);
                            break;
                        case RECTANGLE:
                            rectangleDrawer.mouseDraggedHandling(event);
                            break;
                        case CURVE_LINE:
                            curveLineDrawer.mouseDraggedHandling(event);
                            break;
                        case AIRBRUSH:
                            airbrush.mouseDraggedHandling(event);
                            break;
                        case BRUSH:
                            brush.mouseDraggedHandling(event);
                            break;
                        case CALLIGRAPHY_PEN:
                            calligraphyPen.mouseDraggedHandling(event);
                            break;
                        default:
                            break;
                    }
                }
            }

        });
        view.getPaintPane().addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (null != currentTool) {
                    switch (currentTool) {
                        case CURVE_LINE:
                            curveLineDrawer.mouseReleasedHandling(event);
                            break;
                        case AIRBRUSH:
                            airbrush.mouseReleasedHandling(event);
                            break;
                        case BRUSH:
                            brush.mouseReleasedHandling(event);
                            break;
                        default:
                            break;
                    }
                }
            }

        });
    }

    private void sizeOfPenSliderAction() {
        view.sizeOfPenSliderAction(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pencil.setSizeOfPen(newValue.doubleValue());
                eraser.setSizeOfPen(newValue.doubleValue());
                rectangleDrawer.setSizeOfPen(newValue.doubleValue());
                curveLineDrawer.setSizeOfPen(newValue.doubleValue());
                airbrush.setSizeOfPen(newValue.doubleValue());
                brush.setSizeOfPen(newValue.doubleValue());
                calligraphyPen.setSizeOfPen(newValue.doubleValue());
            }
        });
    }

    private void colorPickerAction() {
        view.colorPickerAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                pencil.setColor(view.getColorOfColorPicker());
                floodFiller.setColor(view.getColorOfColorPicker());
                rectangleDrawer.setColor(view.getColorOfColorPicker());
                curveLineDrawer.setColor(view.getColorOfColorPicker());
                airbrush.setColor(view.getColorOfColorPicker());
                brush.setColor(view.getColorOfColorPicker());
                calligraphyPen.setColor(view.getColorOfColorPicker());
            }
        });
    }

    private void toggleBtnGroupAction() {
        view.toggleBtnGroupAction(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue.getUserData() != ToolType.CURVE_LINE) curveLineDrawer.resetPressCount();
                switch ((ToolType) newValue.getUserData()) {
                    case PENCIL:
                        currentTool = ToolType.PENCIL;
                        break;
                    case ERASER:
                        currentTool = ToolType.ERASER;
                        break;
                    case FLOOD_FILLER:
                        currentTool = ToolType.FLOOD_FILLER;
                        break;
                    case RECTANGLE:
                        currentTool = ToolType.RECTANGLE;
                        break;
                    case CURVE_LINE:
                        currentTool = ToolType.CURVE_LINE;
                        break;
                    case AIRBRUSH:
                        currentTool = ToolType.AIRBRUSH;
                        break;
                    case BRUSH:
                        currentTool = ToolType.BRUSH;
                        break;
                    case CALLIGRAPHY_PEN:
                        currentTool = ToolType.CALLIGRAPHY_PEN;
                        break;
                    default:
                        break;

                }

            }
        });
    }

    private void saveWithKeyBoard() {

        combineKey.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.writeImage(view.getRenderedImage());
            }
        });
        view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                if (event.getCode() == KeyCode.CONTROL) {
                    ctrlPressed.set(true);
                }
                if (event.getCode() == KeyCode.S) {
                    keySPressed.set(true);
                }
            }

        });
        view.getScene().addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                if (event.getCode() == KeyCode.CONTROL) {
                    ctrlPressed.set(false);
                }
                if (event.getCode() == KeyCode.S) {
                    keySPressed.set(false);
                }
            }

        });
    }

    private void exitMenuAction() {
        view.exitMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private void saveAsMenuAction() {
        view.saveAsMenuAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.writeImage(view.getRenderedImage());
            }
        });
    }

}