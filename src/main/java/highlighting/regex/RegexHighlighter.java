package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;
import java.util.ArrayList;
import java.util.List;

// TODO: Implement a simple regex-based highlighting strategy. Unlike the scanning approach, this
// strategy applies each token independently to the entire input text and collects all resulting
// {@code HighlightRegion}s, even if they overlap. Conflicts are resolved in a separate step.

// TODO: Make this class extend {@code SyntaxHighlighter}, implement the abstract method {@code
// collectMatches}, and override {@code resolveConflicts} to handle overlapping regions produced by
// the naive regex-based strategy.
public class RegexHighlighter extends SyntaxHighlighter {

  // TODO: For each token, find all matches of its pattern in the input text, convert them into
  // {@code HighlightRegion}s, and combine all of these regions into a single list.
  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<HighlightRegion> allMatches = new ArrayList<>();

    for (Token element : MiniJavaTokens.defaultTokens()) {
      List<HighlightRegion> matchesFromToken = element.test(text);
      allMatches.addAll(matchesFromToken);
    }
    return allMatches;
  }

  // TODO: Resolve overlapping regions. Assume that {@code regions} has been normalised and sorted.
  // For any overlapping regions, keep the one that appears first in this list (which reflects the
  // token order) and discard all later overlapping regions. Longer regions that start at the same
  // position are preferred because of the sorting in {@code normalize}.
  @Override
  public List<HighlightRegion> resolveConflicts(List<HighlightRegion> regions) {
    // Wenn die Liste leer ist gibt es auch nichts zutun
    if (regions.isEmpty()) {
      return regions;
    }
    List<HighlightRegion> resolvedList = new ArrayList<>();

    // Unser erstes Region kann kein Konflikt auslösen und wird direkt übernommen
    HighlightRegion lastAccept = regions.get(0);
    resolvedList.add(lastAccept);

    // Alle folgenden müssen geprüft werden
    for (int i = 1; i < regions.size(); i++) {
      HighlightRegion current = regions.get(i);
      // Wenn das aktuelle Region innerhalb des zuletzt akzeptierten liegt, dann wird es geskipped
      if (current.start() < lastAccept.end()) {
        continue;
      }
      // Ansonsten gibt es keinen Konflikt und es wird akzeptiert
      else {
        resolvedList.add(current);
        lastAccept = current;
      }
    }
    return resolvedList;
  }
}
