package filters;

public class Alpha extends Channel{

    private Alpha(int threshold, Operator operator)
    {
        this.operator = operator;
        this.threshold = threshold;
    }

    public static Alpha equals(int threshold)
    {
        return new Alpha(threshold, Operator.EQUALS);
    }
    public static Alpha notEquals(int threshold)
    {
        return new Alpha(threshold, Operator.NOT_EQUAL);
    }
    public static Alpha greater(int threshold)
    {
        return new Alpha(threshold, Operator.GREATER);
    }
    public static Alpha less(int threshold)
    {
        return new Alpha(threshold, Operator.LESS);
    }
    public static Alpha greaterOrEqual(int threshold)
    {
        return new Alpha(threshold, Operator.GREATER_OR_EQUAL);
    }
    public static Alpha lessOrEqual(int threshold)
    {
        return new Alpha(threshold, Operator.LESS_OR_EQUAL);
    }

    protected int getChannel(int argbValue)
    {
        return ((argbValue & 0xFF000000) >> 24) & 0x000000FF;
    }

}
