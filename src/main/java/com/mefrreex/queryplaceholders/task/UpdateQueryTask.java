package com.mefrreex.queryplaceholders.task;

import cn.nukkit.scheduler.Task;
import com.mefrreex.queryplaceholders.QueryPlaceholders;

public class UpdateQueryTask extends Task {

    private final QueryPlaceholders main;

    public UpdateQueryTask(QueryPlaceholders main) {
        this.main = main;
    }

    @Override
    public void onRun(int currentTick) {
        main.updateServers();
    }
    
}
