package com.example.inzynierka.ui.tripPlans

import android.widget.DatePicker
import java.util.*

class TripDataControl {
    val maxTripDay = 30

    fun getStringDate(datePicker: DatePicker): String{
        //dni zaczynają się od 1
        val day = if(datePicker.dayOfMonth<10)
            "0" + datePicker.dayOfMonth.toString()
        else
            datePicker.dayOfMonth.toString()

        //miesiące zaczynają się od 0
        val month = if(datePicker.month<9)
            "0" + (datePicker.month + 1).toString()
        else
            (datePicker.month + 1).toString()

        return day + "/" + month + "/" + datePicker.year.toString()
    }

    fun getDay(date: String): Int?{
        val split = "/"
        val day = date.split(split)
        if (day.size!=3)
            return null
        return day[0].toInt()
    }

    fun getYear(date: String): Int?{
        val split = "/"
        val year = date.split(split)
        if (year.size!=3)
            return null
        return year[2].toInt()
    }

    fun getMonth(date: String): Int?{
        val split = "/"
        val month = date.split(split)
        if (month.size!=3)
            return null
        return month[1].toInt()-1
    }

    @Suppress("DEPRECATION")
    fun getCheckDate(startDatePicker: DatePicker?, endDatePicker: DatePicker?): Int{
        if (startDatePicker != null && endDatePicker != null) {
            val startYear = startDatePicker.year
            val startMonth = startDatePicker.month
            val startDay = startDatePicker.dayOfMonth
            val endYear = endDatePicker.year
            val endMonth = endDatePicker.month
            val endDay = endDatePicker.dayOfMonth

            val startDate = Date(startYear, startMonth, startDay)
            val endDate = Date(endYear, endMonth, endDay)


            val difference: Long = endDate.time - startDate.time
            val differenceDates = difference / (24 * 60 * 60 * 1000)

            if(startYear == endYear && startMonth == endMonth && startDay == endDay)
                return 0

            return differenceDates.toInt()
        }
        return 0
    }
}