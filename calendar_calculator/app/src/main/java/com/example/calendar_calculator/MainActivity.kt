package com.example.calendar_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import java.time.LocalDate
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Year
import kotlin.properties.Delegates

//share data between Activities: https://www.youtube.com/watch?v=uNV_qLfc5Zw

// if Toast don't work: https://www.youtube.com/watch?v=ZK3_ib-g_no

class MainActivity : AppCompatActivity() {
    private var year by Delegates.notNull<Int>()
    private lateinit var date: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberPickerDate: NumberPicker = findViewById(R.id.numberPickerDate)
        numberPickerDate.minValue = 1900
        numberPickerDate.maxValue = 2200
        numberPickerDate.wrapSelectorWheel = true
        numberPickerDate.value = Year.now().value
        changeDate()
        numberPickerDate.setOnValueChangedListener { _, _, _ ->
            changeDate()
        }

        SyndaysActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, SundayActivity::class.java)
            intent.putExtra("year", year)
            intent.putExtra("easter", date.minusDays(7).toString())
            startActivity(intent)
        }

        daysActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, DaysActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeDate() {
        val textView: TextView = findViewById(R.id.textView)
        year = numberPickerDate.value
        val par = easter(year)

        date = LocalDate.of(year, par[1], par[0])

        val ashWednesdayDate = date.minusDays(46)

        val corpusChristiDate = date.plusDays(60)

        val christmasDay = LocalDate.of(year, 12, 25)
        val dayOfWeek = christmasDay.dayOfWeek.value
        val adventStart = 21 + dayOfWeek % 7
        val adventStartDay = christmasDay.minusDays(adventStart.toLong())

        ("Easter Day: " + date.toString() + "\nAsh Wednesday: " + ashWednesdayDate.toString() + "\nCorpus Christi: " + corpusChristiDate.toString() +
                "\nFirst day of Advent: " + adventStartDay.toString()).also { textView.text = it }

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