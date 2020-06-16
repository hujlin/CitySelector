package com.example.test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.utils.hideSoftInput
import com.example.test.widget.SlideBar
import com.github.promeg.pinyinhelper.Pinyin
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = CityAdapter()
        val data = testData()
        recyclerView.adapter = adapter
        adapter.setData(data)
        setSlideBarData(data)
        adapter.onItemClickListener = { pos, data ->
            Toast.makeText(this, "$pos===${data.cityName}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        recyclerView.addItemDecoration(CityItemDecoration(this))
        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val content = input.text.trim().toString()
                val data = if (content.isEmpty()) {
                    ivClear.visibility = View.GONE
                    testData()
                } else {
                    ivClear.visibility = View.VISIBLE
                    testData().filter { it.cityName.startsWith(content) || it.character.startsWith(content.toUpperCase()) }
                }
                Log.e("data", data.toString())
                adapter.setData(data)
                if (data.isEmpty()) {
                    tips.visibility = View.VISIBLE
                    slideView.visibility = View.GONE
                } else {
                    tips.visibility = View.GONE
                    slideView.visibility = View.VISIBLE
                    setSlideBarData(data)
                }
            }
        })

        recyclerView.setOnTouchListener { v, _ ->
            hideSoftInput(v)
            false
        }
        ivClear.setOnClickListener { input.setText("") }
    }

    private fun setSlideBarData(data: List<City>) {
        val characterList = data.map { it.character }.distinct()
        slideView.setData(characterList)
        slideView.onIndexChangedListener = SlideBar.OnIndexChangedListener(
                onUp = { tvCharacter.visibility = View.GONE },
                onIndexChanged = { letter ->
                    tvCharacter.visibility = View.VISIBLE
                    tvCharacter.text = letter
                    val index = data.indexOfFirst { it.character == letter }
                    (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index, 0)
                }
        )
    }

    private fun testData(): List<City> {
        val data = mutableListOf<City>()
        data.add(City(cityName = "北京"))
        data.add(City(cityName = "北京"))
        data.add(City(cityName = "北京"))
        data.add(City(cityName = "北京"))
        data.add(City(cityName = "北京"))
        data.add(City(cityName = "北京"))
        data.add(City(cityName = "北京"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "上海"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "天津"))
        data.add(City(cityName = "珠海"))
        data.add(City(cityName = "辽宁"))
        data.forEach {
            it.character = Pinyin.toPinyin(it.cityName, "")
        }
        data.sortBy { it.character }
        return data
    }


}