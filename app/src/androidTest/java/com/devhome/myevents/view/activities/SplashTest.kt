package com.devhome.myevents.view.activities

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devhome.myevents.R
import com.devhome.myevents.ui.MainActivity
import com.devhome.myevents.ui.Splash
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashTest {


    @Before
    fun setUp() {
        ActivityScenario.launch(Splash::class.java)
    }

    @Test
    fun logoText() {

        onView(withText(R.string.app_name))
            .check(matches(isDisplayed()))

    }

    @Test
    fun itStartsLoginActivityAfterDelay() {
        Intents.init()
        Thread.sleep(3000);
        Intents.intended(hasComponent(MainActivity::class.java.name))
        Intents.release()
    }


    @After
    fun tearDown() {
    }
}