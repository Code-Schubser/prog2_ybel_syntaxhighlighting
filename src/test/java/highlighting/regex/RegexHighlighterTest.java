package highlighting.regex;

import static org.junit.jupiter.api.Assertions.*;

import highlighting.core.HighlightRegion;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegexHighlighterTest {

  // Test ob Text der zu keinem Token passt hinzugefuegt werden würde
  @Test
  public void testNoMatches() {
    RegexHighlighter highlighter = new RegexHighlighter();
    // Ein Beispiel das zu keinen Token passt
    List<HighlightRegion> regions = highlighter.computeRegions("beeispieel");
    assertTrue(regions.isEmpty());
  }

  //  Test ob hintereinander stehende Regionen passend hinzugefügt werden
  @Test
  public void testFollowingRegions() {
    RegexHighlighter highlighter = new RegexHighlighter();
    // Ein Beispiel wo zwei kanidaten direkt hintereinander stehen
    List<HighlightRegion> regions = highlighter.computeRegions("public class");

    // Beide sollten akzeptiert werden
    assertFalse(regions.isEmpty());
  }

  // Test ob Konfliktlösung funktioniert
  @Test
  public void testConflictRegions() {
    RegexHighlighter highlighter = new RegexHighlighter();
    // Keyword import steht mitten im einzeiligen Kommentar
    List<HighlightRegion> regions = highlighter.computeRegions("// Teste import von Text");
    // Es darf nur eine region hinzugefügt werden
    assertEquals(1, regions.size());
  }
}
