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

  public Boolean setTable(Document document) {
    this.tables = document.select("table");
    if (tables.size() > 10) {
      this.table = document.select("table").get(tables.size() - 3);
      rows = table.select("tr");
      return true;
    } else
      return false;
  }

  public void GetKWFields1(KW kw) {
    kw.setField1(String.valueOf(id));
    id++;
  }

  public String GetKWFields2(Document document) {
    if (setTable(document, 3)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.size() < 2)
          continue;
        if (cols.get(1).text().equals("Numer księgi")) {
          return (cols.get(cols.size() - 1).text());
        }
      }
    }
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
          if (!cols.get(cols.size() - 1).hasClass("csMark") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
            if (sb.length() > 0)
              sb.append(", ");
            sb.append(cols.get(cols.size() - 1).text());
          } else {
            while (!temp.get(0).text().equals("3. Obręb ewidencyjny")) {
              if (!temp.get(temp.size() - 1).hasClass("csMark") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
                if (sb.length() > 0)
                  sb.append(", ");
                sb.append(temp.get(temp.size() - 1).text());
              }
              temp = rows.get(i++).select("td");
            }
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields4(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 6)) {
      for (int j = 0; j < rows.size(); j++) {
        cols = rows.get(j).select("td");
        if (cols.get(0).text().equals("8. Przyłączenie")) {
          Elements temp = rows.get(j + 1).select("td");
          if (!temp.get(temp.size() - 1).text().equals("/ /") && !temp.get(temp.size() - 1).hasClass("csMark") && !temp.get(temp.size() - 1).text().equals("---")) {
            sb.append(temp.get(temp.size() - 1).text() + " ");
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields5(Document document) {
    if (setTable(document, 10)) {
      for (int j = 0; j < rows.size(); j++) {
        cols = rows.get(j).select("td");
        if (cols.get(cols.size() - 1).hasClass("csDane"))
          return cols.get(cols.size() - 1).text();
      }
      return null;
    } else
      return null;
  }

  public String GetKWFields6(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 5)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (i > 4 && (!cols.get(1).text().equals("1. Numer porządkowy"))) {
          if (!cols.get(cols.size() - 1).hasClass("csMark") && !cols.get(cols.size() - 1).text().equals("---")) {
            if (sb.length() > 0)
              sb.append(", ");
            sb.append(cols.get(cols.size() - 1).text());
          }
        }
      }
      if (setTable(document, 6)) {
        for (int i = 0; i < rows.size(); i++) {
          cols = rows.get(i).select("td");
          if (cols.get(0).text().equals("5. Ulica") && !cols.get(cols.size() - 1).hasClass("csMark") && !cols.get(cols.size() - 1).text().equals("---")) {
            if (sb.length() > 0)
              sb.append(", ");
            sb.append(cols.get(cols.size() - 1).text());
          }
        }
        if (setTable(document, 9)) {
          for (int i = 0; i < rows.size(); i++) {
            cols = rows.get(i).select("td");
            if (cols.get(0).text().equals("2. Ulica") && !cols.get(cols.size() - 1).hasClass("csMark") && !cols.get(cols.size() - 1).text().equals("---")) {
              if (sb.length() > 0)
                sb.append(", ");
              sb.append(cols.get(cols.size() - 1).text());
            }
          }
          return String.valueOf(sb).replace(";", ",");
        }
      }
    }
    return null;
  }

  public String GetKWFields7(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 6)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(0).text().equals("6. Sposób korzystania") && !cols.get(cols.size() - 1).text().equals("---")) {
          if (cols.get(cols.size() - 1).hasClass("csMark")) {
            Elements temp = rows.get(i + 1).select("td");
            if (temp.get(0).text().length() < 4) {
              if (sb.length() > 0)
                sb.append(", ");
              sb.append(temp.get(temp.size() - 1).text());
            }
          } else {
            if (sb.length() > 0)
              sb.append(", ");
            sb.append(cols.get(cols.size() - 1).text());
          }
        }
      }
    }
    if (setTable(document, 9)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(0).text().equals("5. Przeznaczenie lokalu") && !cols.get(cols.size() - 1).text().equals("---")) {
          if (cols.get(cols.size() - 1).hasClass("csMark")) {
            Elements temp = rows.get(i + 1).select("td");
            if (temp.get(0).text().length() < 4) {
              if (sb.length() > 0)
                sb.append(", ");
              sb.append(temp.get(temp.size() - 1).text());
            }
          } else {
            if (sb.length() > 0)
              sb.append(", ");
            sb.append(cols.get(cols.size() - 1).text());
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    }
    return null;
  }

  public String GetKWFields8(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 15)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(cols.size() - 1).hasClass("csDane") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
          if (cols.get(cols.size() - 4).text().equals("5. Numer karty akt")) {
            sb.append("Numer karty akt : ");
          } else if (cols.get(cols.size() - 4).text().equals("6. Numer księgi")) {
            sb.append("Numer księgi : ");
          }
          sb.append((cols.get(cols.size() - 1).text()) + ", ");
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields9(Document document) {
    StringBuilder sb = new StringBuilder();
    int j = 0;
    if (setTable(document, 8)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(cols.size() - 1).hasClass("csDane") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
          if (cols.get(cols.size() - 1).text().length() > 1) {
            if (cols.get(0).text().equals("6. Imię ojca")) {
              sb.append("Imię ojca : ");
            } else if (cols.get(0).text().equals("7. Imię matki")) {
              sb.append(", Imię matki : ");
            }
            sb.append((cols.get(cols.size() - 1).text()) + " ");
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields10(Document document) {
    StringBuilder sb = new StringBuilder();
    tables = document.select("table");
    table = document.select("table").get(tables.size() - 3);
    rows = table.select("tr");
    if (setTable(document)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(cols.size() - 1).hasClass("csDane") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
          if (cols.get(cols.size() - 4).text().equals("9. Numer karty akt") || cols.get(cols.size() - 4).text().equals("6. Numer karty akt") || cols.get(cols.size() - 4).text().equals("5. Numer karty akt")) {
            sb.append("Numer karty akt : ");
          } else if (cols.get(cols.size() - 4).text().equals("10. Numer księgi") || cols.get(cols.size() - 4).text().equals("7. Numer księgi") || cols.get(cols.size() - 4).text().equals("6. Numer księgi")) {
            sb.append("Numer księgi : ");
          }
          sb.append((cols.get(cols.size() - 1).text()) + ", ");
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields11(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 4)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(0).text().equals("4. Treść prawa")) {
          Elements temp = cols;
          int j = i;
          while (!temp.get(0).text().equals("6. Udział związany")) {
            if (!temp.get(temp.size() - 1).hasClass("csMark") && !temp.get(temp.size() - 1).text().equals("---") && !temp.get(temp.size() - 1).text().equals("/ /")) {
              sb.append((temp.get(temp.size() - 1).text()) + ", ");
            }
            temp = rows.get(++j).select("td");
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields12(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 4)) {
      for (int i = 0; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(0).text().equals("1. Rodzaj wpisu")) {
          Elements temp = cols;
          int j = i;
          while (!temp.get(0).text().equals("3. Przedmiot wykonywania")) {
            if (!temp.get(temp.size() - 1).hasClass("csMark")) {
              sb.append((temp.get(temp.size() - 1).text()) + ", ");
            }
            temp = rows.get(++j).select("td");
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  //nie wiem czy moze sie pojawic nowa uwaga pod wyszarzona
  public String GetKWFields13(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 14)) {
      for (int j = 0; j < rows.size(); j++) {
        if (j == 5) {
          cols = rows.get(j).select("td");
          if (cols.get(cols.size() - 1).hasClass("csDane") && !cols.get(cols.size() - 1).text().equals("---") && !cols.get(cols.size() - 1).text().equals("/ /")) {
            if (sb.length() == 0)
              sb.append("Uwagi : ");
            sb.append(cols.get(cols.size() - 1).text() + ", ");
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields14(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 6)) {
      for (int j = 0; j < rows.size(); j++) {
        cols = rows.get(j).select("td");
        if (cols.get(0).text().equals("8. Przyłączenie")) {
          if (!cols.get(cols.size() - 1).text().equals("/ /")) {
            sb.append("Przyłączenie : " + cols.get(cols.size() - 1).text() + ", ");
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }

  public String GetKWFields15(Document document) {
    StringBuilder sb = new StringBuilder();
    if (setTable(document, 6)) {
      String numberField = "";
      for (int j = 0; j < rows.size(); j++) {
        cols = rows.get(j).select("td");
        if (cols.get(0).text().equals("2. Numer działki")) {
          numberField = cols.get(cols.size() - 1).text();
        }
        if (cols.get(0).text().equals("7. Odłączenie")) {
          if (!cols.get(cols.size() - 1).text().equals("/ /")) {
            sb.append("Odłączenie : " + numberField + ", ");
            sb.append(rows.get(j + 1).select("td").get(4).text() + ", ");
            sb.append(cols.get(cols.size() - 1).text() + ", ");
          }
        }
      }
      return String.valueOf(sb).replace(";", ",");
    } else
      return null;
  }
}
