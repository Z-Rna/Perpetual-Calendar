package com.example.calendar_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_days.*
import java.time.LocalDate

//About LocalDate: https://www.baeldung.com/kotlin/dates
class DaysActivity : AppCompatActivity() {
    private lateinit var holidayArray: Array<Any>
    private lateinit var dP1: DatePicker
    private lateinit var dP2: DatePicker
    private lateinit var back: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days)
        dP1 = findViewById(R.id.datePicker1)
        dP2 = findViewById(R.id.datePicker2)
        back = findViewById(R.id.buttonBack2)

        change()

        dP1.setOnDateChangedListener { _, _, _, _ ->
            change()
        }

        dP2.setOnDateChangedListener { _, _, _, _ ->
            change()
        }

        back.setOnClickListener {
            val intent = Intent(this@DaysActivity, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun change() {
        val dP1Day = dP1.dayOfMonth
        val dP1Month = dP1.month + 1
        val dP1Year = dP1.year
        val dP1Date = LocalDate.of(dP1Year, dP1Month, dP1Day)

        val dP2Day = dP2.dayOfMonth
        val dP2Month = dP2.month + 1
        val dP2Year = dP2.year
        val dP2Date = LocalDate.of(dP2Year, dP2Month, dP2Day)

        if(!dP1Date.isAfter(dP2Date)) {
            var date = dP1Date
            var everyDay = 0
            var workDay = 0
            var currentYear = dP1Year - 1

            while (!date.isEqual(dP2Date)) {
                if(currentYear != date.year) {
                    currentYear = date.year

                    val firstJanuary = LocalDate.of(currentYear, 1, 1)
                    val sixthJanuary = LocalDate.of(currentYear, 1, 1)

                    val firstMay = LocalDate.of(currentYear, 5, 1)
                    val thirdMay = LocalDate.of(currentYear, 5, 3)

                    val firstNovember = LocalDate.of(currentYear, 11, 1)
                    val eleventhNovember = LocalDate.of(currentYear, 11, 11)

                    val firstChristmasDay = LocalDate.of(currentYear, 12, 25)
                    val secondChristmasDay = LocalDate.of(currentYear, 12, 26)

                    val fifteenAugust = LocalDate.of(currentYear, 8, 15)

                    val easter = easter(currentYear)
                    val easterDate = LocalDate.of(currentYear, easter[1], easter[0])

                    val easterMonday = easterDate.plusDays(1)
                    val dateCorpusChristi = easterDate.plusDays(60)

                    holidayArray = arrayOf(firstJanuary, sixthJanuary, firstMay, thirdMay, firstNovember,
                            eleventhNovember, firstChristmasDay, secondChristmasDay, fifteenAugust, easterMonday, dateCorpusChristi)

                }
                everyDay++

                val dayOdWeek = date.dayOfWeek.value
                if(dayOdWeek < 6 && !holidayArray.contains(date)) {
                    workDay ++
                }
                date = date.plusDays(1)
            }
            ("Day count: $everyDay\nWork days: $workDay").also { textViewDays.text = it }
        }
        else {
            Toast.makeText(this, "The period begins after the end", Toast.LENGTH_LONG).show()
            ("Day count: -\nWork days: -").also { textViewDays.text = it }
        }
    }

    private fun easter(year: Int): Array<Int> {
        val a = year % 19
        val b = (year / 100)
        val c = year % 100
        val d = (b / 4)
        val e = b % 4
        val f = ((b + 8) / 25)
        val g = ((b -f + 1) / 3)
        val h = (19 * a + b - d - g + 15) % 30
        val i = (c / 4)
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = ((a + 11 * h + 22 * l) / 451)
        val p = (h + l - 7 * m + 114) % 31
        val day = p + 1
        val month = ((h + l - 7 * m + 114) / 31)
        return arrayOf(day, month)
    }


}