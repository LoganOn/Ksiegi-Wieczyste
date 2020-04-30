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

  private String header = "Lp.;Numer KW;Numery działek;Pola powierzchni;Pola powierzchni;Położenie nieruchomości;Rodzaj nieruchomości;Informacje o mapach;Właściciele;Podstawy ustalenia danych w kol 8;Rodzaj ograniczeń praw rzeczowych;Uwagi;Inne-Uwagi do migracji;Przyłączenie z KW;Odłączenie z KW";

  @FXML
  public void initialize() {
    csQuery = new CsQuery();
  }

  @FXML
  public void onClickChoose() {
    labelText.setText("Wybierz folder z plikami");
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
    nameFile = "KW-" + formatter.format(date) +".csv";
    try {
      writer = new PrintWriter(nameFile, "CP1250");
      writer.println(header);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    search();
    labelText.setText("Plik o nazwie : " + nameFile + " wygenerowany");
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
          read(resultFile, x);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void read(List<String> resultFile, String direcotry) throws IOException {
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
          kw.setField4(csQuery.GetKWFields4(document));
          kw.setField5(csQuery.GetKWFields5(document));
          kw.setField6(csQuery.GetKWFields6(document));
          kw.setField7(csQuery.GetKWFields7(document));
          kw.setField8(csQuery.GetKWFields8(document));
          kw.setField13(csQuery.GetKWFields13(document));
          kw.setField15(csQuery.GetKWFields15(document));
          kw.setField14(csQuery.GetKWFields14(document));
        }
        if(i == 2){
          kw.setField11(csQuery.GetKWFields11(document));
        }
        if(i == 3){
          kw.setField9(csQuery.GetKWFields9(document));
          kw.setField10(csQuery.GetKWFields10(document));
        }
        if(i == 4){
          kw.setField12(csQuery.GetKWFields12(document));
        }
      }
    }
    if(kw.getField2() != null) {
      writer.print(kw.getField1() + ";");
      writer.print(kw.getField2() + ";");
      writer.print("'" + kw.getField3() + ";");
      writer.print(kw.getField4() + ";");
      writer.print(kw.getField5() + ";");
      writer.print(kw.getField6() + ";");
      writer.print(kw.getField7() + ";");
      writer.print(kw.getField8() + ";");
      writer.print(kw.getField9() + ";");
      writer.print(kw.getField10() + ";");
      writer.print(kw.getField11() + ";");
      writer.print(kw.getField12() + ";");
      writer.print(kw.getField13() + ";");
      writer.print(kw.getField14() + ";");
      writer.println(kw.getField15() + ";");
    }
    else
    {
      writer.println(direcotry);
    }
  }
}

