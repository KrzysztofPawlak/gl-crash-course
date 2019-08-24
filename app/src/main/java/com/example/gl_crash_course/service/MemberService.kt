package com.example.gl_crash_course.service

import android.content.Context
import com.example.gl_crash_course.memberslist.Member
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class MemberService(private val context: Context) {

    private fun getArrayOfMembers(): ArrayList<Member> {
        if (readJsonAsString() != null) {
            return fromJson(readJsonAsString().toString())
        }
        return arrayListOf()
    }

    private inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object : TypeToken<T>() {}.type)
    }

    suspend fun getSubset(offset: Int, limit: Int): ArrayList<Member> {
        val from = offset * limit
        val to = from + limit

        val delayMillis: Long = 2000

        delay(delayMillis)

        return ArrayList(getArrayOfMembers().subList(from, to))
    }

    fun getOne(idToFind: Int): Member {
        return getArrayOfMembers().single { member -> member.id == idToFind }
    }

    private fun readJsonAsString(): String? {
        var json: String? = null

        try {
            val inputStream: InputStream = context.assets.open("member.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }
}