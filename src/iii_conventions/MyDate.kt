package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }

}


operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class MyDateIterator(val range: DateRange) : Iterator<MyDate> {
    var current = range.start

    override fun hasNext(): Boolean {
        return current <= range.endInclusive
    }

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return MyDateIterator(this)
    }
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

operator fun MyDate.plus(interval: TimeInterval) = this.addTimeIntervals(interval, 1)
operator fun MyDate.plus(interval: RepeatedTimeInterval) = this.addTimeIntervals(interval.timeInterval, interval.number)

operator fun TimeInterval.times(multiply : Int) = RepeatedTimeInterval(this, multiply)
