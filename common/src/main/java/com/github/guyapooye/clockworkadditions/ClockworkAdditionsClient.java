package com.github.guyapooye.clockworkadditions;

import com.github.guyapooye.clockworkadditions.registries.PartialModelRegistry;

public class ClockworkAdditionsClient {
    public static void init() {
        PartialModelRegistry.register();
    }
}
