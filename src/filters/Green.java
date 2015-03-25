package filters;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class Green extends Channel {

    private Green(int threshold, Operator operator)
    {
        this.operator = operator;
        this.threshold = threshold;
    }

    public static Green equals(int threshold)
    {
        return new Green(threshold, Operator.EQUALS);
    }
    public static Green greater(int threshold)
    {
        return new Green(threshold, Operator.GREATER);
    }
    public static Green less(int threshold)
    {
        return new Green(threshold, Operator.LESS);
    }
    public static Green greaterOrEqual(int threshold)
    {
        return new Green(threshold, Operator.GREATER_OR_EQUAL);
    }
    public static Green lessOrEqual(int threshold)
    {
        return new Green(threshold, Operator.LESS_OR_EQUAL);
    }

    @Override
    protected int getChannel(int argbValue) {
        return ((argbValue & 0x0000FF00) >> 8) & 0x000000FF;
    }
}
