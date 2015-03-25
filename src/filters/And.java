package filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This incredibly useful class was created by lorenzo on 25.03.15.
 */
public class And implements Filter {
    List<Filter> allFilters;

    public And(Filter...filters)
    {
        allFilters = new ArrayList<>();

        Collections.addAll(allFilters, filters);
    }
    public And(List<Filter> allFilters)
    {
        this.allFilters = allFilters;
    }

    @Override
    public boolean isFiltered(int argbValue) {
        for(Filter f : allFilters)
        {
            if(!f.isFiltered(argbValue))
                return false;
        }
        return true;
    }
}
