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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  private CsQuery csQuery = null;

  private PrintWriter writer;

  private String nameFile;

  @FXML
  public void initialize() {
    csQuery = new CsQuery();
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
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
    nameFile = "React-Client-" + formatter.format(date) +".csv";
    try {
      writer = new PrintWriter(nameFile, "UTF-8");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    search();
    writer.close();
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
    KW kw = new KW();
    if (resultFile.size() == 6) {
      for (int i = 0; i < resultFile.size(); i++) {
        fileTemp = new File(resultFile.get(i));
        document = Jsoup.parse(fileTemp, "UTF-8");
        if(i == 0)
        {
          csQuery.GetKWFields1(kw);
          kw.setField2(csQuery.GetKWFields2(document));
        }
        if (i == 1) {
          kw.setField3(csQuery.GetKWFields3(document));
          kw.setField5(csQuery.GetKWFields5(document));
          //kw.setField6(csQuery.GetKWFields6(document));
          csQuery.GetKWFields6(document);
          csQuery.GetKWFields7(document);
          csQuery.GetKWFields8(document);
          csQuery.GetKWFields15(document);
          csQuery.GetKWFields14(document);
          csQuery.GetKWFields13(document);
        }
        if(i == 2){
          csQuery.GetKWFields11(document);
        }
        if(i == 3){
          csQuery.GetKWFields9(document);
          csQuery.GetKWFields10(document);
        }
        if(i == 4){
          csQuery.GetKWFields12(document);
        }
      }
      System.out.println("######################################################################");
    } else {
      System.out.println("**********************************************************************");
      System.out.println("Za krotki");
      System.out.println("**********************************************************************");
    }
    writer.print(kw.getField1() + ";");
    writer.print(kw.getField2() + ";");
    writer.print(kw.getField3() + ";");
    writer.println(kw.getField5() + ";");
  }

  public void write(String s){

  }
}

