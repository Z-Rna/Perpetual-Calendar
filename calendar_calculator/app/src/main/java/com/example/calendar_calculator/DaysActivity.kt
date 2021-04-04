package com.example.calendar_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_days.*
import java.time.LocalDate

//About LocalDate: https://www.baeldung.com/kotlin/dates
class DaysActivity : AppCompatActivity() {
    private lateinit var holidayArray: Array<Any>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days)

        AcceptButton.setOnClickListener {
            val DP1_day = datePicker1.dayOfMonth
            val DP1_month = datePicker1.month + 1
            val DP1_year = datePicker1.year
            val DP1_date = LocalDate.of(DP1_year, DP1_month, DP1_day)

            val DP2_day = datePicker2.dayOfMonth
            val DP2_month = datePicker2.month + 1
            val DP2_year = datePicker2.year
            val DP2_date = LocalDate.of(DP2_year, DP2_month, DP2_day)

            if(!DP1_date.isAfter(DP2_date)) {
                var date = DP1_date
                var everyDay = 0
                var workDay = 0
                var currentYear = DP1_year - 1

                while (!date.isEqual(DP2_date)) {
                    if(!currentYear.equals(date.year)) {
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
            }

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