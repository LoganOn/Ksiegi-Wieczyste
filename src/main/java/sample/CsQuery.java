package sample;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CsQuery {

  private Element table;
  private Elements rows;
  private Elements cols;

  public void setTable(Document document, int i) {
    this.table = document.select("table").get(i);
    rows = table.select("tr");
  }

  public void GetKWFields2(Document document) {
    setTable(document, 3);
    cols = rows.get(3).select("td");
    System.out.println(cols.get(2).text());
  }

  public void GetKWFields3(Document document) {
    setTable(document, 6);
    for (int j = 0; j < rows.size(); j++) {
      cols = rows.get(j).select("td");
      if (cols.get(0).text().equals("2. Numer działki")) {
        if (!cols.get(4).hasClass("csMark")) {
          System.out.println(cols.get(4).text());
        }
      }
    }
  }

  public void GetKWFields5(Document document) {
    setTable(document, 10);
    cols = rows.get(4).select("td");
    System.out.println(cols.get(4).text());
  }

  public void GetKWFields13(Document document) {
    setTable(document, 14);
    for (int j = 5; j < rows.size(); j++) {
      cols = rows.get(j).select("td");
      System.out.println(cols.get(4).text());
    }
  }

  public void GetKWFields14(Document document) {
    setTable(document, 6);
    for (int j = 0; j < rows.size(); j++) {
      cols = rows.get(j).select("td");
      if (cols.get(0).text().equals("7. Odłączenie")) {
        if (!cols.get(5).text().equals("/ /")) {
          System.out.println(rows.get(j - 6).select("td").get(4).text());
          System.out.println(cols.get(cols.size() - 1).text());
          System.out.println(rows.get(j + 1).select("td").get(4).text());
        }
      }
    }
  }
}
