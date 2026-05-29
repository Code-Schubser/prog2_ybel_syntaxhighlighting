package highlighting.presets;

/*
default imports du JUnit6
 */
import highlighting.core.HighlightRegion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
Weitere notwendige imports
 */
import highlighting.regex.Token;

import java.lang.classfile.Annotation;
import java.util.List;

class MiniJavaTokensTest {

    // Test fuer einzeiligen Kommentar
    @Test
    void testLineComment() {

        // 1. Vorbereitung
        // Test eine Liste aller definierten Tokens zur verfuegung stellen
        List<Token> tokens = MiniJavaTokens.defaultTokens();
        // Einzeiligen Kommentar merken
        Token lineCommentToken = tokens.get(1);
        // Testcode festlegen
        String testCode = "// Das ist ein Kommentar";
        // TestCode fuer assertEquals in ein HighlightRegion Objekt konventieren
        HighlightRegion testRegion = new HighlightRegion(0, testCode.length(), MiniJavaColours.LINE_COMMENT_COLOUR);

        //2. Test durchfuehren
        // test() von Token durchfueren, ergebnisse werden in HighlightRegionListe festgehalten
        var regions = lineCommentToken.test(testCode);

        // Pruefen ob, ein Objekt drinne ist
        assertEquals(1, regions.size());
        assertEquals(regions.get(0), testRegion);
    }
    // Test fuer JavadocKommentar
    @Test
    void testJavadocComment() {

        // 1. Vorbereitung
        // Test eine Liste aller definierten Tokens zur verfuegung stellen
        List<Token> tokens = MiniJavaTokens.defaultTokens();
        // Javadoc merken
        Token JavadocCommentToken = tokens.get(2);
        // Testcode festlegen
        String testCode = "/** Das ist ein Kommentar */";
        // TestCode fuer assertEquals in ein HighlightRegion Objekt konventieren
        HighlightRegion testRegion = new HighlightRegion(0, testCode.length(), MiniJavaColours.JAVADOC_COMMENT_COLOUR);

        //2. Test durchfuehren
        // test() von Token durchfueren, ergebnisse werden in HighlightRegionListe festgehalten
        var regions = JavadocCommentToken.test(testCode);

        // Pruefen ob, ein Objekt drinne ist
        assertEquals(1, regions.size());
        // Vergleich der Objekte
        assertEquals(regions.get(0), testRegion);
    }
    // Test, falls innerhalb des Javadocs Kommentares sich Anführungszeichen befinden
    @Test
    void testJavadocCommentWithQuotes() {

        // 1. Vorbereitung
        // Test eine Liste aller definierten Tokens zur verfuegung stellen
        List<Token> tokens = MiniJavaTokens.defaultTokens();
        // Javadoc merken
        Token JavadocCommentToken = tokens.get(2);
        // Testcode festlegen
        String testCode = "/** \"ApplicationListener\" */";
        // TestCode fuer assertEquals in ein HighlightRegion Objekt konventieren
        HighlightRegion testRegion = new HighlightRegion(0, testCode.length(), MiniJavaColours.JAVADOC_COMMENT_COLOUR);

        //2. Test durchfuehren
        // test() von Token durchfueren, ergebnisse werden in HighlightRegionListe festgehalten
        var regions = JavadocCommentToken.test(testCode);

        // Pruefen ob, ein Objekt drinne ist
        assertEquals(1, regions.size());
        // Vergleich der Objekte
        assertEquals(regions.get(0), testRegion);
    }

    // Test fuer Keyword
    @Test
    void testKeywordCommentClass() {

        // 1. Vorbereitung
        // Test eine Liste aller definierten Tokens zur verfuegung stellen
        List<Token> tokens = MiniJavaTokens.defaultTokens();
        // Javadoc merken
        Token KeywordCommentToken = tokens.get(4);
        // Testcode festlegen
        String testCode = "class";
        // TestCode fuer assertEquals in ein HighlightRegion Objekt konventieren
        HighlightRegion testRegion = new HighlightRegion(0, testCode.length(), MiniJavaColours.KEYWORD_COLOUR);

        //2. Test durchfuehren
        // test() von Token durchfueren, ergebnisse werden in HighlightRegionListe festgehalten
        var regions = KeywordCommentToken.test(testCode);

        // Pruefen ob, ein Objekt drinne ist
        assertEquals(1, regions.size());
        // Vergleich der Objekte
        assertEquals(regions.get(0), testRegion);
    }
    // Test fuer Annotation
    @Test
    void testKAnnotationOverride() {

        // 1. Vorbereitung
        // Test eine Liste aller definierten Tokens zur verfuegung stellen
        List<Token> tokens = MiniJavaTokens.defaultTokens();
        // Javadoc merken
        Token AnnotationToken = tokens.get(5);
        // Testcode festlegen
        String testCode = "@Override";
        // TestCode fuer assertEquals in ein HighlightRegion Objekt konventieren
        HighlightRegion testRegion = new HighlightRegion(0, testCode.length(), MiniJavaColours.ANNOTATION_COLOUR);

        //2. Test durchfuehren
        // test() von Token durchfueren, ergebnisse werden in HighlightRegionListe festgehalten
        var regions = AnnotationToken.test(testCode);

        // Pruefen ob, ein Objekt drinne ist
        assertEquals(1, regions.size());
        // Vergleich der Objekte
        assertEquals(regions.get(0), testRegion);
    }

}
