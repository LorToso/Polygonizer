package filters;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public abstract class Channel {
    protected int threshold;
    protected Operator operator;

    protected boolean filterChannel(int value)
    {
        switch (operator)
        {
            case EQUALS:
                return value == threshold;
            case GREATER:
                return value > threshold;
            case LESS:
                return value < threshold;
            case GREATER_OR_EQUAL:
                return value >= threshold;
            case LESS_OR_EQUAL:
                return value <= threshold;
            default:
                return false;
        }
    }
    protected boolean isFiltered(int argbValue) {
        int channel = getChannel(argbValue);
        return filterChannel(channel);
    }
    protected abstract int getChannel(int argbValue);
}
