package com.example.calendar_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sunday.*
import java.time.LocalDate

class SundayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunday)

        val intent = intent

        val year = intent.getIntExtra("year", -1)
        val easter = intent.getStringExtra("easter")
        if (year < 2020) {
            "No trading rules on Sundays are in force from 2020".also { textView2.text = it }
            Toast.makeText(this, "Choose other date", Toast.LENGTH_LONG).show()
        }
        else {
            val lastSundayJanuary = lastSunday(LocalDate.of(year, 1, 31))
            val lastSundayApril = lastSunday(LocalDate.of(year, 4, 30))
            val lastSundayJune = lastSunday(LocalDate.of(year, 6, 30))
            val lastSundayAugust = lastSunday(LocalDate.of(year, 8, 31))
            val lastSundayChristmas = lastSunday(LocalDate.of(year, 12, 25), true)
            val lastLastSundayChristmas = lastSundayChristmas.minusDays(7)

            (
                    "January: " + lastSundayJanuary.toString() +
                    "\n\nApril: " + lastSundayApril.toString() +
                    "\n\nJune: " + lastSundayJune +
                    "\n\nAugust: " + lastSundayAugust +
                    "\n\nBefore Easter: " + easter +
                    "\n\nDecember: " + lastSundayChristmas +
                    "\n\nDecember: " +  lastLastSundayChristmas
                    ).also { textView2.text = it }
        }
    }
    private fun lastSunday(date: LocalDate): LocalDate {
        val dayOfWeek = date.dayOfWeek.value
        val daysSinceSunday = dayOfWeek % 7
        return date.minusDays(daysSinceSunday.toLong())
    }

    private fun lastSunday(date: LocalDate, christmas: Boolean): LocalDate {
        val dayOfWeek = date.dayOfWeek.value
        val daysSinceSunday = dayOfWeek % 7
        if (daysSinceSunday == 0)
            return date.minusDays(7)
        return date.minusDays(daysSinceSunday.toLong())
    }


}