package murt.shoppinglistapp.ui.utils

import android.content.Context
import android.text.format.DateUtils
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

/**
 * Piotr Murtowski on 21.02.2018.
 */
object DateTools {

    fun getReadableDate(context: Context, localDateTime: LocalDateTime): String{
        return DateUtils.getRelativeTimeSpanString(context,
            localDateTime.toEpochMiliseconds(ZoneOffset.UTC))
            .toString()
    }
}

fun LocalDateTime.getReadableDate(context: Context): String{
    return DateTools.getReadableDate(context, this)
}

fun LocalDateTime.toEpochMiliseconds(offset: ZoneOffset): Long{
    return (this.toEpochSecond(offset) * 1000)
}