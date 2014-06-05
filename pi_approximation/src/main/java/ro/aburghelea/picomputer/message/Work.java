package ro.aburghelea.picomputer.message;

/**
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 6/3/2014
 */
public class Work {
    private final int start;
    private final int numberOfElements;

    public Work(int start, int numberOfElements) {
        this.start = start;
        this.numberOfElements = numberOfElements;
    }

    public int getStart() {
        return start;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }
}