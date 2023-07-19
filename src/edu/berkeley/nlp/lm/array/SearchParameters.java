package edu.berkeley.nlp.lm.array;

public class SearchParameters {
    private final long key;
    private final long rangeStart;
    private final long rangeEnd;
    private final long startIndex;
    private final long emptyKey;
    private final boolean returnFirstEmptyIndex;

    public SearchParameters(long key, long rangeStart, long rangeEnd, long startIndex, long emptyKey, boolean returnFirstEmptyIndex) {
        this.key = key;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.startIndex = startIndex;
        this.emptyKey = emptyKey;
        this.returnFirstEmptyIndex = returnFirstEmptyIndex;
    }

    public long getKey() {
        return key;
    }

    public long getRangeStart() {
        return rangeStart;
    }

    public long getRangeEnd() {
        return rangeEnd;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public long getEmptyKey() {
        return emptyKey;
    }

    public boolean shouldReturnFirstEmptyIndex() {
        return returnFirstEmptyIndex;
    }
}
