package highlighting.antlr;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaColours;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;

// TODO Phase III — AntlrTokenCollector (token-based syntax highlighting).

// This highlighter uses the ANTLR-generated MiniJavaLexer to turn the input text into a token
// stream. {@code collectMatches(String)} is the only method you need to implement: extract tokens
// of interest and map them to {@code HighlightRegions} using the colours from {@code
// MiniJavaColours}. Sorting, filtering of invalid regions, and conflict handling are performed by
// the base class {@code SyntaxHighlighter} via the template method {@code computeRegions(...)}.
public class AntlrTokenCollector extends SyntaxHighlighter {

  // TODO (Phase III — implement this method): Use the token stream produced by the ANTLR-generated
  // {@code MiniJavaLexer} to collect highlight regions.
  //
  // Requirements / hints:
  // - Iterate over the lexer tokens (typically via {@code CommonTokenStream}); ignore the EOF
  // token.
  // - For each token type that should be coloured (e.g., keywords, string/char literals, comments),
  // create a {@code HighlightRegion} with the corresponding colour from {@code MiniJavaColours}.
  // - Use {@code Token#getStartIndex()} and {@code Token#getStopIndex()} (inclusive) to compute
  // {@code [start, end)} ranges: {@code start = startIndex, end = stopIndex + 1}.
  // - Do not sort, merge, or resolve overlaps here; return all candidates as you find them.
  // Normalisation and conflict resolution are handled later by the template method.
  // - Annotation highlighting: colour '@' and the immediately following IDENTIFIER token (if
  // present).
  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<HighlightRegion> regions = new ArrayList<>();

    // Text in einen CharStream (ANTLR) umwandeln
    CharStream charStream = CharStreams.fromString(text);
    // scanner kennt tokens
    MiniJavaLexer lexer = new MiniJavaLexer(charStream);
    // verwaltet die Tokens vom lexer
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    // ganzen text lesen
    tokenStream.fill();
    List<Token> tokens = tokenStream.getTokens();

    System.out.println(tokens.size() + " tokens gefunden");
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      int start = token.getStartIndex();
      int end = token.getStopIndex() + 1;
      int tokenType = token.getType();

      switch (tokenType) {
        case MiniJavaLexer.PACKAGE:
        case MiniJavaLexer.IMPORT:
        case MiniJavaLexer.CLASS:
        case MiniJavaLexer.PUBLIC:
        case MiniJavaLexer.PRIVATE:
        case MiniJavaLexer.FINAL:
        case MiniJavaLexer.RETURN:
        case MiniJavaLexer.NULL:
        case MiniJavaLexer.NEW:
        case MiniJavaLexer.IF:
        case MiniJavaLexer.ELSE:
        case MiniJavaLexer.WHILE:
        case MiniJavaLexer.EXTENDS:
        case MiniJavaLexer.IMPLEMENTS:
          // farbe übergeben
          regions.add(new HighlightRegion(start, end, MiniJavaColours.KEYWORD_COLOUR));
          break;
        case MiniJavaLexer.STRING_LITERAL:
          regions.add(new HighlightRegion(start, end, MiniJavaColours.STRING_LITERAL_COLOUR));
          break;
        case MiniJavaLexer.CHAR_LITERAL:
          regions.add(new HighlightRegion(start, end, MiniJavaColours.CHAR_LITERAL_COLOUR));
          break;
        case MiniJavaLexer.LINE_COMMENT:
          regions.add(new HighlightRegion(start, end, MiniJavaColours.LINE_COMMENT_COLOUR));
          break;
        case MiniJavaLexer.BLOCK_COMMENT:
          regions.add(new HighlightRegion(start, end, MiniJavaColours.BLOCK_COMMENT_COLOUR));
          break;
        case MiniJavaLexer.JAVADOC_COMMENT:
          regions.add(new HighlightRegion(start, end, MiniJavaColours.JAVADOC_COMMENT_COLOUR));
          break;
        case MiniJavaLexer.AT:
          if (i + 1 < tokens.size()) {
            Token nextToken = tokens.get(i + 1);
            if (nextToken.getType() == MiniJavaLexer.IDENTIFIER) {
              int annotationsEnd = nextToken.getStopIndex() + 1;
              regions.add(
                  new HighlightRegion(start, annotationsEnd, MiniJavaColours.ANNOTATION_COLOUR));
              // skip weiler zwei tokens kombiniert wurden
              i++;
            } else {
              regions.add(new HighlightRegion(start, end, MiniJavaColours.ANNOTATION_COLOUR));
            }
          } else {
            // Falls @ am Ende der Datei steht
            regions.add(new HighlightRegion(start, end, MiniJavaColours.ANNOTATION_COLOUR));
          }
          break;

        default:
          // andere Tokens ignorieren
          break;
      }
    }

    return regions;
  }
}
