package com.example.finery;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Object MainActivity;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finery", appContext.getPackageName());

//        @Rule
//                public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
//
//        @Test
//                public void ensureListViewIsPresent() throws Exception {
//
//            MainActivity activity = rule.getActivity();
//
//            View viewById = activity.findViewById(R.id.btnAccountsettings);
//            assertThat(viewById, notNullValue());
//            assertThat(viewById, instanceOf(ListView.class));
//        }

    }
}