package poligonizer.filters;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class FilterCollector implements Iterable<Filter> {

    private final ArrayList<Filter> allFilters = new ArrayList<>();

    public void addFilter(Filter filter)
    {
        allFilters.add(filter);
    }

    public boolean isFiltered(int argbValue)
    {
        for (Filter filter : allFilters)
        {
            if(filter.isFiltered(argbValue))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<Filter> iterator() {
        return allFilters.iterator();
    }
}
