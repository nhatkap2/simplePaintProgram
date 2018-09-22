/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.model;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class Model {
    
    private FileChooser fileChooser;
    
    private File file;
    
    public Model() {
        
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png", "*.png"));
        fileChooser.setInitialFileName("Untitled");
        String path = System.getProperty("user.home") + "\\Desktop";
        //String path = "."; // // get the path where contains the jar (execute) file
        fileChooser.setInitialDirectory(new File(path));
    }
    public void writeImage(RenderedImage renderedImage) {
        file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Lỗi không ghi được file");
            }
        }
    }
}