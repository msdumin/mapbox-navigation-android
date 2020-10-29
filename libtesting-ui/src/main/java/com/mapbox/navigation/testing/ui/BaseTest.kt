package com.mapbox.navigation.testing.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.mapbox.navigation.testing.ui.http.MockWebServerRule
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.Rule

open class BaseTest<A : AppCompatActivity>(activityClass: Class<A>) {

    @get:Rule
    val activityRule = ActivityTestRule(activityClass)

    @get:Rule
    val mockLocationUpdatesRule = MockLocationUpdatesRule()

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    // Clear all app's SharedPreferences
    // This allows the test to run in isolation, without any cached resources.
    @get:Rule
    val clearPreferencesRule = ClearPreferencesRule()

    // Delete all tables from all the app's SQLite Databases.
    // Including the database that store Map SDK resources.
    // This allows the test to run in isolation, without any cached resources.
    @get:Rule
    val clearDatabaseRule = ClearDatabaseRule()

    // Delete all files in getFilesDir() and getCacheDir()
    // This allows the test to run in isolation, without any cached resources.
    @get:Rule
    val clearFilesRule = ClearFilesRule()

    protected val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    val activity: A
        get() = activityRule.activity

    val appName: String by lazy {
        val applicationInfo = activity.applicationInfo
        val stringId = applicationInfo.labelRes

        if (stringId == 0) {
            applicationInfo.nonLocalizedLabel.toString()
        } else {
            activity.getString(stringId)
        }
    }
}
