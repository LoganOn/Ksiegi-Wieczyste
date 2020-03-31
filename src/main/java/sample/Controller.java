package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {

  @FXML
  private Label labelText;

  @FXML
  private TextField textFieldPath;

  @FXML
  private Button buttonGenerate;

  @FXML
  private Button buttonChooseFile;

  private File selectedDirectory;

  private List<String> resultDirecotry;

  private List<String> resultFile;

  @FXML
  public void initialize() {

  }

  @FXML
  public void onClickChoose() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Wybierz folder");
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
    selectedDirectory = directoryChooser.showDialog(null);
    textFieldPath.setText(selectedDirectory.getAbsolutePath());

  }

  @FXML
  public void onClickGenerate() {
   search();
  }

  public void search(){
    try (Stream<Path> walk = Files.walk(Paths.get(selectedDirectory.getAbsolutePath()))) {

      resultDirecotry = walk.filter(Files::isDirectory)
        .map(x -> x.toString()).collect(Collectors.toList());

      resultDirecotry.forEach(System.out::println);
      resultDirecotry.forEach(x -> {
        try (Stream<Path> walkFile = Files.walk(Paths.get(x))) {

          resultFile = walkFile.map(y -> y.toString())
            .filter(f -> f.endsWith(".xlsx")).collect(Collectors.toList());
          resultFile.forEach(System.out::println);
        }catch (IOException e) {
          e.printStackTrace();
        }
      });

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
