package org.solovyev.android.keyboard;

import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 11/5/12
 * Time: 6:46 PM
 */
public class RepeatHelper {

    // in ms
    private static final int MIN_REPEAT_INTERVAL = 100;

    private static final int[] REPEAT_INTERVALS = new int[]{5 * MIN_REPEAT_INTERVAL, 4 * MIN_REPEAT_INTERVAL, 3 * MIN_REPEAT_INTERVAL, 2 * MIN_REPEAT_INTERVAL, MIN_REPEAT_INTERVAL, MIN_REPEAT_INTERVAL / 2, MIN_REPEAT_INTERVAL / 4};

    @Nullable
    private View repeatView;

    private int repeatInterval = REPEAT_INTERVALS[0];

    private long lastTime = 0;

    private int repeatCounter = 0;

    private boolean repeat = false;

    boolean prepare(@NotNull View v) {
        repeat = repeatView == v;
        if (!repeat) {
            lastTime = System.currentTimeMillis();
            repeatCounter = 0;
            repeatInterval = REPEAT_INTERVALS[repeatCounter];
            repeatView = v;
        }
        return repeat;
    }

    public boolean canBeRepeated() {
        final long currentTime = System.currentTimeMillis();

        return currentTime - lastTime > repeatInterval;
    }

    public void goFurther(@NotNull View v, boolean repeatAllowed) {
        if (repeat) {
            lastTime = System.currentTimeMillis();
            if (repeatAllowed) {
                if ( repeatCounter < REPEAT_INTERVALS.length ) {
                    repeatCounter++;
                    repeatInterval = REPEAT_INTERVALS [repeatCounter];
                }
            }
        }
    }

    public boolean canGoFurther() {
        return (repeat && canBeRepeated()) || !repeat;
    }
}
