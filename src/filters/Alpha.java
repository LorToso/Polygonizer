package filters;

public class Alpha extends Channel{

    private final int threshold;
    private final Operator operator;

    private Alpha(int threshold, Operator operator)
    {
        this.operator = operator;
        this.threshold = threshold;
    }

    public static Alpha equals(int threshold)
    {
        return new Alpha(threshold, Operator.EQUALS);
    }

    private int getAlphaChannel(int argbValue)
    {
        return ((argbValue & 0xFF000000) >> 24) & 0x000000FF;
    }

    @Override
    protected boolean isFiltered(int argbValue) {
        int channel = getAlphaChannel(argbValue);
        switch (operator)
        {
            case EQUALS:
                return channel == threshold;
            default:
                return false;
        }
    }
}
