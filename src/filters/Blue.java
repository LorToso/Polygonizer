package filters;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class Blue extends Channel {

    private Blue(int threshold, Operator operator)
    {
        this.operator = operator;
        this.threshold = threshold;
    }

    public static Blue equals(int threshold)
    {
        return new Blue(threshold, Operator.EQUALS);
    }
    public static Blue notEquals(int threshold)
    {
        return new Blue(threshold, Operator.NOT_EQUAL);
    }
    public static Blue greater(int threshold)
    {
        return new Blue(threshold, Operator.GREATER);
    }
    public static Blue less(int threshold)
    {
        return new Blue(threshold, Operator.LESS);
    }
    public static Blue greaterOrEqual(int threshold)
    {
        return new Blue(threshold, Operator.GREATER_OR_EQUAL);
    }
    public static Blue lessOrEqual(int threshold)
    {
        return new Blue(threshold, Operator.LESS_OR_EQUAL);
    }

    @Override
    protected int getChannel(int argbValue) {
        return (argbValue & 0x000000FF);
    }
}
