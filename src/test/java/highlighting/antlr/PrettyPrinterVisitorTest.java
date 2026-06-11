package highlighting.antlr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

/**
 * Generierter Test zur Darstellung der Ergebnisse :)
 */
public class PrettyPrinterVisitorTest {

  private String format(String rawCode, int indentWidth) {
    var charStream = CharStreams.fromString(rawCode);
    var lexer = new MiniJavaLexer(charStream);
    var tokens = new CommonTokenStream(lexer);
    var parser = new MiniJavaParser(tokens);
    var tree = parser.compilationUnit();

    var printer = new PrettyPrinterVisitor(indentWidth);
    printer.visit(tree);
    return printer.result();
  }

  @Test
  public void testEinfacheKlasseMit2Leerzeichen() {
    String raw = "package bsp1; class Auto { private int raeder; public void hupen() {} }";

    String result = format(raw, 2);

    System.out.println("=== TEST 1: EINFACHE KLASSE (Indent: 2) ===");
    System.out.println(result);

    // Prüfe die 2er-Einrückung der Klassenmitglieder
    assertTrue(
        result.contains("\n  private int"), "Das Feld sollte mit 2 Leerzeichen eingerückt sein.");
    assertTrue(
        result.contains("\n  public void"),
        "Die Methode sollte mit 2 Leerzeichen eingerückt sein.");
  }

  @Test
  public void testKontrollflussMit4Leerzeichen() {
    // Ohne Zahlen (x < y statt x < 10, und x == y statt x == 5)
    String raw =
        "package bsp2; class Rechner { public void loop(int x, int y) { while(x < y) { if(x == y) {"
            + " return; } } } }";

    String result = format(raw, 4);

    System.out.println("=== TEST 2: KONTROLLFLUSS (Indent: 4) ===");
    System.out.println(result);

    // Klasse (0) -> Methode (4) -> While (8) -> If (12)
    assertTrue(result.contains("\n    public void"), "Methode sollte 4 Leerzeichen haben.");
    assertTrue(result.contains("\n        while"), "While-Schleife sollte 8 Leerzeichen haben.");
    assertTrue(result.contains("\n            if"), "If-Anweisung sollte 12 Leerzeichen haben.");
  }

  @Test
  public void testVerschachtelteBloeckeMit8Leerzeichen() {
    // Ohne Zahlen (Zuweisung an Variablen statt Literale)
    String raw =
        "package bsp3; class Struktur { public void run(int x) { { int a = x; { int b = x; } } } }";

    String result = format(raw, 8);

    System.out.println("=== TEST 3: VERSCHACHTELTE BLÖCKE (Indent: 8) ===");
    System.out.println(result);

    // Methode (8) -> Äußerer Block (16) -> Variable a (24)
    assertTrue(result.contains("\n        public void"), "Methode sollte 8 Leerzeichen haben.");
    assertTrue(
        result.contains("\n                        int a"),
        "Variable a im verschachtelten Block sollte 24 Leerzeichen haben.");
  }
}
