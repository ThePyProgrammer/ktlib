package com.thepyprogrammer.ktlib.datetime

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

fun dateTimeFormatterOf(pattern: String = "dd/MM/yyyy"): DateTimeFormatter =
    DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendPattern(pattern)
        .toFormatter()

fun dateFormatOf(pattern: String = "dd/MM/yyyy"): DateFormat = SimpleDateFormat(pattern)


fun LocalDateTime.format(pattern: String = "dd/MM/yyyy"): String = format(dateTimeFormatterOf(pattern))
fun LocalDate.format(pattern: String = "dd/MM/yyyy"): String = format(dateTimeFormatterOf(pattern))
fun LocalTime.format(pattern: String = "dd/MM/yyyy"): String = format(dateTimeFormatterOf(pattern))


fun LocalDateTime.isInYear(year: Int) = this.year == year

fun LocalDateTime.isInMonth(month: Int) = this.monthValue == (month % 12)
fun LocalDateTime.isInMonth(month: Month) = isInMonth(month.value)

fun LocalDateTime.isOnDayOfYear(dayOfYear: Int) = this.dayOfYear == dayOfYear

fun LocalDateTime.isOnDayOfMonth(dayOfMonth: Int) = this.dayOfMonth == dayOfMonth

fun LocalDateTime.isOnDayOfWeek(dayOfWeek: Int) = this.dayOfWeek.value == dayOfWeek
fun LocalDateTime.isOnDayOfWeek(dayOfWeek: DayOfWeek) = isOnDayOfWeek(dayOfWeek.value)

fun LocalDateTime.isAtHour(hour: Int) = this.hour == hour
fun LocalDateTime.isAtMinute(minute: Int) = this.minute == minute
fun LocalDateTime.isAtSecond(second: Int) = this.second == second
fun LocalDateTime.isAtNano(nano: Int) = this.nano == nano

fun LocalDateTime.isOnDate(date: LocalDate) =
    isInYear(date.year) && isInMonth(date.month)
            && isOnDayOfYear(dayOfYear) && isOnDayOfMonth(dayOfMonth) && isOnDayOfWeek(dayOfWeek)

fun LocalDateTime.isAtTime(time: LocalTime) =
    isAtHour(time.hour) && isAtMinute(time.minute) && isAtSecond(time.second) && isAtNano(time.nano)


fun LocalDate.toLocalDateTime(): LocalDateTime = LocalDateTime.of(year, month, dayOfMonth, 0, 0)
fun LocalTime.toLocalDateTime(): LocalDateTime = LocalDateTime.of(0L.toLocalDate(), this)

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochMilli(this), ZoneId.systemDefault()
)
fun Long.toLocalDate(): LocalDate = toLocalDateTime().toLocalDate()
fun Long.toLocalTime(): LocalTime = toLocalDateTime().toLocalTime()

fun LocalDateTime.toEpoch(): Long = atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
fun LocalDate.toEpoch(): Long = toLocalDateTime().toEpoch()
fun LocalTime.toEpoch(): Long = toLocalDateTime().toEpoch()
