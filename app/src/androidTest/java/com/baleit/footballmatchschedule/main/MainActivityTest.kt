package com.baleit.footballmatchschedule.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.baleit.footballmatchschedule.activity.MainActivity
import org.junit.Rule
import android.support.v7.widget.RecyclerView
import com.baleit.footballmatchschedule.R.id.*
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.IdlingRegistry
import com.baleit.footballmatchschedule.idling.EspressoIdlingResource
import org.junit.Before




@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource)
    }

    @Test
    fun testAppBehaviour() {
        onView(withId(rv_past))
                .check(matches(isDisplayed()))
        onView(withId(rv_past)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        onView(withId(rv_past)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(1500   /*Or any other time*/);
        pressBack()

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(next_match)).perform(click())

        onView(withId(rv_next))
                .check(matches(isDisplayed()))
        onView(withId(rv_next)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        onView(withId(rv_next)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(1500   /*Or any other time*/);
        pressBack()

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorit)).perform(click())

        Thread.sleep(1000   /*Or any other time*/);
        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(tentang_app)).perform(click())

        Thread.sleep(1000   /*Or any other time*/);
    }

}