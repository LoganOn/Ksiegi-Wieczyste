package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.DirectoryChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

  private Document document;

  private File fileTemp;

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

  public void search() {
    try (Stream<Path> walk = Files.walk(Paths.get(selectedDirectory.getAbsolutePath()))) {
      resultDirecotry = walk.filter(Files::isDirectory)
        .map(x -> x.toString()).collect(Collectors.toList());
      resultDirecotry.forEach(x -> {
        try (Stream<Path> walkFile = Files.walk(Paths.get(x))) {
          resultFile = walkFile.map(y -> y.toString())
            .filter(f -> f.endsWith(".html")).collect(Collectors.toList());
          read(resultFile);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void read(List<String> resultFile) throws IOException {
    if (resultFile.size() <= 6) {
      for (int i = 0; i < resultFile.size(); i++) {
        fileTemp = new File(resultFile.get(i));
        document = Jsoup.parse(fileTemp, "UTF-8");
        if (i == 1) {
          Element table = document.select("table").get(6);
          Elements rows = table.select("tr");
          Elements cols = null;
          for (int j = 0; j < rows.size(); j++) {
            cols = rows.get(j).select("td");
            // System.out.println(cols.get(0).toString());
            if (cols.get(0).text().equals("7. Odłączenie")) {
              if (!cols.get(5).text().equals("/ /")) {
                System.out.println(cols.get(5).text());
                Elements temp = rows.get(j + 1).select("td");
                System.out.println(rows.get(j + 1).select("td").get(4).text());
              }
            }
          }
        }
      }
    } else {
    }
  }
}

