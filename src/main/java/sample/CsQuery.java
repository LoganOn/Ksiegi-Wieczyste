package sample;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CsQuery {

  private Element table;
  private Elements tables;
  private Elements rows;
  private Elements cols;

  private static int id = 1;

  public Boolean setTable(Document document, int i) {
    this.tables = document.select("table");
    if (tables.size() > i) {
      this.table = document.select("table").get(i);
      rows = table.select("tr");
      return true;
    } else
      return false;
  }

  public void GetKWFields1(KW kw){
    kw.setField1(String.valueOf(id));
    id ++;
  }
  public String GetKWFields2(Document document) {
    if (setTable(document, 3)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.size() < 2)
          continue;
        if (cols.get(1).text().equals("Numer księgi")) {
          //System.out.print("Nuker księgi : ");
         //System.out.println(cols.get(cols.size() - 1).text());
          return (cols.get(cols.size() - 1).text());
        }      }
    } else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    return null;
  }

  public String GetKWFields3(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 6)) {
      for (int j = 0; j < rows.size(); j++) {
        cols = rows.get(j).select("td");
        if (cols.get(0).text().equals("2. Numer działki")) {
          Elements temp = cols;
          int i = j;
          if (!cols.get(cols.size() - 1).hasClass("csMark")) {
            //System.out.print("1. Numer działki : ");
            //System.out.println(cols.get(cols.size() - 1).text());
            sb.append(cols.get(cols.size() - 1).text());
          } else {
            while (!temp.get(0).text().equals("3. Obręb ewidencyjny")) {
              if (!temp.get(temp.size() - 1).hasClass("csMark")) {
//                System.out.print("2. Numer działki : ");
//                System.out.println(temp.get(temp.size() - 1).text());
                sb.append(temp.get(temp.size() - 1).text());
              }
              temp = rows.get(i++).select("td");
            }
          }
        }
      }
      return String.valueOf(sb);
    } else
    //  System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    return null;
  }

  public String GetKWFields5(Document document) {
    if (setTable(document, 10)) {
      cols = rows.get(4).select("td");
//      System.out.print("Powierzchnia : ");
//      System.out.println(cols.get(cols.size() - 1).text());
      return cols.get(cols.size() - 1).text();
    } else
   //   System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    return null;
  }

  public void GetKWFields6(Document document) {
    if (setTable(document, 5)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (i > 4 && (!cols.get(1).text().equals("1. Numer porządkowy"))) {
          if (!cols.get(cols.size() - 1).hasClass("csMark") && !cols.get(cols.size() - 1).text().equals("---"))
            System.out.println(cols.get(cols.size() - 1).text());
        }
      }
      if (setTable(document, 6)) {
        for (int i = 0; i < rows.size(); i++){
          cols = rows.get(i).select("td");
          if(cols.get(0).text().equals("5. Ulica") && !cols.get(cols.size() -1).hasClass("csMark")  && !cols.get(cols.size() - 1).text().equals("---"))
            System.out.println(cols.get(cols.size() - 1).text());
        }
      }
    } else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }

  public void GetKWFields7(Document document){
    if (setTable(document, 6)) {
      for (int i = 0; i < rows.size(); i++){
        cols = rows.get(i).select("td");
        if(cols.get(0).text().equals("6. Sposób korzystania") && !cols.get(cols.size() -1).hasClass("csMark")  && !cols.get(cols.size() - 1).text().equals("---"))
          System.out.println(cols.get(cols.size() - 1).text());
      }
    }
   else
    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }

  public void GetKWFields8(Document document){
    if (setTable(document, 15)) {
      for (int i = 0; i < rows.size(); i++){
        cols = rows.get(i).select("td");
        if(cols.get(cols.size() -1 ).hasClass("csDane") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
          if(cols.get(cols.size() -4).text().equals("5. Numer karty akt")) {
            System.out.print("Numer karty akt : ");
          }
          else if(cols.get(cols.size() -4).text().equals("6. Numer księgi")) {
            System.out.print("Numer księgi : ");
          }
          System.out.println(cols.get(cols.size() - 1).text());
        }
      }
    }
    else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }

  public void GetKWFields9(Document document){
    if (setTable(document, 8)) {
      for (int i = 0; i < rows.size(); i++){
        cols = rows.get(i).select("td");
        if(cols.get(cols.size() -1 ).hasClass("csDane") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
            if (cols.get(0).text().equals("6. Imię ojca")) {
              System.out.print("Imię ojca : ");
            } else if (cols.get(0).text().equals("7. Imię matki")) {
              System.out.print("Imię matki : ");
            }
            System.out.println(cols.get(cols.size() - 1).text());
        }
        }
    }
    else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }
  public void GetKWFields10(Document document){
    if (setTable(document, 15)) {
      for (int i = 0; i < rows.size(); i++){
        cols = rows.get(i).select("td");
        if(cols.get(cols.size() -1 ).hasClass("csDane") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
          if (cols.get(cols.size() - 4).text().equals("9. Numer karty akt") || cols.get(cols.size() - 4).text().equals("6. Numer karty akt") || cols.get(cols.size() - 4).text().equals("5. Numer karty akt")) {
            System.out.print("Numer karty akt : ");
          } else if (cols.get(cols.size() - 4).text().equals("10. Numer księgi") || cols.get(cols.size() - 4).text().equals("7. Numer księgi") || cols.get(cols.size() - 4).text().equals("6. Numer księgi")) {
            System.out.print("Numer księgi : ");
          }
          System.out.println(cols.get(cols.size() - 1).text());
        }
      }
    }
    else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }
  public void GetKWFields11(Document document){
    if (setTable(document, 4)) {
      for (int i = 0; i < rows.size(); i++){
        cols = rows.get(i).select("td");
          if (cols.get(0).text().equals("4. Treść prawa")) {
            Elements temp = cols;
            int j = i;
              while (!temp.get(0).text().equals("6. Udział związany")) {
                if (!temp.get(temp.size() - 1).hasClass("csMark")) {
                  System.out.println(temp.get(temp.size() - 1).text());
                }
                temp = rows.get(++j).select("td");
              }
            }
          }
        }
    else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }

  public void GetKWFields12(Document document){
    if (setTable(document, 4)) {
      for (int i = 0; i < rows.size(); i++){
        cols = rows.get(i).select("td");
        if (cols.get(0).text().equals("1. Rodzaj wpisu")) {
          Elements temp = cols;
          int j = i;
          while (!temp.get(0).text().equals("3. Przedmiot wykonywania")) {
            if (!temp.get(temp.size() - 1).hasClass("csMark")) {
              System.out.println(temp.get(temp.size() - 1).text());
            }
            temp = rows.get(++j).select("td");
          }
        }
      }
    }
    else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }
  //BRAK WALIDACJI SPR
  public void GetKWFields13(Document document) {
    if (setTable(document, 14)) {
      for (int j = 0; j < rows.size(); j++) {
        if (j == 5) {
          cols = rows.get(j).select("td");
          System.out.print("Uwagi: ");
          System.out.println(cols.get(cols.size() - 1).text());
        }
      }
    } else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }

  public void GetKWFields14(Document document) {
    if (setTable(document, 6)) {
      for (int j = 0; j < rows.size(); j++) {
        cols = rows.get(j).select("td");
        if (cols.get(0).text().equals("8. Przyłączenie")) {
          if (!cols.get(cols.size() - 1).text().equals("/ /")) {
            System.out.print("Przyłączenie : ");
            System.out.println(cols.get(cols.size() - 1).text());
          }
        }
      }
    }
    else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

  public void GetKWFields15(Document document) {
    if (setTable(document, 6)) {
      String numberField = "";
      for (int j = 0; j < rows.size(); j++) {
        cols = rows.get(j).select("td");
        if (cols.get(0).text().equals("2. Numer działki")) {
          numberField = cols.get(cols.size() - 1).text();
        }
          if (cols.get(0).text().equals("7. Odłączenie")) {
            if (!cols.get(cols.size() - 1).text().equals("/ /")) {
              System.out.print("Odłączenie : ");
              System.out.println(numberField);
              System.out.println(rows.get(j + 1).select("td").get(4).text());
              System.out.println(cols.get(cols.size() - 1).text());
            }
          }
      }
    } else
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }
}
