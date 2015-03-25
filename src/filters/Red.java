package filters;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class Red extends Channel {

    private Red(int threshold, Operator operator)
    {
        this.operator = operator;
        this.threshold = threshold;
    }

    public static Red equals(int threshold)
    {
        return new Red(threshold, Operator.EQUALS);
    }
    public static Red greater(int threshold)
    {
        return new Red(threshold, Operator.GREATER);
    }
    public static Red less(int threshold)
    {
        return new Red(threshold, Operator.LESS);
    }
    public static Red greaterOrEqual(int threshold)
    {
        return new Red(threshold, Operator.GREATER_OR_EQUAL);
    }
    public static Red lessOrEqual(int threshold)
    {
        return new Red(threshold, Operator.LESS_OR_EQUAL);
    }

    @Override
    protected int getChannel(int argbValue) {
        return ((argbValue & 0x00FF0000) >> 16) & 0x000000FF;
    }
}
