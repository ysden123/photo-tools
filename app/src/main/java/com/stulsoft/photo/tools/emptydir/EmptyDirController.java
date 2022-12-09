/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools.emptydir;

import com.stulsoft.photo.slib.emptydir.EmptyDirService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class EmptyDirController {
    private File lastDirectory = null;

    @FXML
    private TextField path;

    @FXML
    private TextArea emptyDirs;

    public void onFileSelect(MouseEvent event) {
        var directoryChooser = new DirectoryChooser();
        if (lastDirectory != null) {
            directoryChooser.setInitialDirectory(lastDirectory);
        }
        var node = (Node) event.getSource();
        var parentScene = node.getScene();
        File selectedFile = directoryChooser.showDialog(parentScene.getWindow());
        if (selectedFile != null) {
            lastDirectory = selectedFile.getParentFile();
            path.setText(selectedFile.getAbsolutePath());
        }
    }

    public void onFind(){
        if (path.getText().isEmpty()) {
            return;
        }
        emptyDirs.setText("Please wait. Processing ...");

        EmptyDirService.findEmptyDirsAsync(path.getText(), result ->{
            if (result.error().isEmpty()) {
                if (result.result().isEmpty()){
                    emptyDirs.setText("No empty directory found");
                } else {
                    emptyDirs.setText(result.result());
                }
            } else {
                emptyDirs.setText("");
                var alert = new Alert(Alert.AlertType.WARNING, result.error());
                alert.setTitle("A problem with a directory");
                alert.setHeaderText(null);
                alert.show();
            }
        });
    }
}
