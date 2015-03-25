package filters;

import java.util.ArrayList;

/**
 * Created by lorenzo on 25.03.15.
 */
public abstract class Filter {
    ArrayList<Filter> combinedFilters = new ArrayList<>();

    public abstract boolean filters(int argbValue);

    public Filter and(Filter combinedFilter)
    {
        combinedFilters.add(combinedFilter);
        return this;
    }
    public boolean isFiltered(int argbValue)
    {
        for(Filter filter : combinedFilters)
        {
            if(!filter.filters(argbValue))
                return false;
        }
        return true;
    }
}
