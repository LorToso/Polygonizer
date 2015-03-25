package filters;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class Red extends Channel {

    private final int threshold;
    private final Operator operator;

    private Red(int threshold, Operator operator)
    {
        this.operator = operator;
        this.threshold = threshold;
    }

    public static Red equals(int threshold)
    {
        return new Red(threshold, Operator.EQUALS);
    }

    private int getRedChannel(int argbValue)
    {
        return ((argbValue & 0x00FF0000) >> 16) & 0x000000FF;
    }

    @Override
    protected boolean isFiltered(int argbValue) {
        int channel = getRedChannel(argbValue);
        switch (operator)
        {
            case EQUALS:
                return channel == threshold;
            default:
                return false;
        }
    }
}
