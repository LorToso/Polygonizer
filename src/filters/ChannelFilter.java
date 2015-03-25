package filters;

import java.util.ArrayList;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class ChannelFilter {

    private final ArrayList<Channel> allChannels = new ArrayList<>();

    public void addChannel(Channel channel)
    {
        allChannels.add(channel);
    }

    public boolean isFiltered(int argbValue)
    {
        for (Channel channel : allChannels)
        {
            if(!channel.isFiltered(argbValue))
                return false;
        }
        return true;
    }
}
