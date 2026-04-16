package edu.phystech.hw2.analyzer;


import java.util.List;

public class NegativeTextAnalyzer extends KeywordAnalyzer {
    private static final List<String> NEGATIVE_SMILES = List.of(":(", "=(", ":|");

    public NegativeTextAnalyzer() {
        super(NEGATIVE_SMILES, Label.NEGATIVE);
    }

    @Override
    protected boolean containsKeyword(String text, String keyword) {
        if (":|".equals(keyword)) {
            int i = 0;
            while ((i = text.indexOf(":|", i)) >= 0) {
                if (i + 2 >= text.length() || text.charAt(i + 2) != '|') {
                    return true;
                }
                i++;
            }
            return false;
        }
        return super.containsKeyword(text, keyword);
    }
}
