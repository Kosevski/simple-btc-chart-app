package com.example.mk.wsahomeproject


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.mk.wsahomeproject.model.BlockChainResponse
import com.example.mk.wsahomeproject.model.CustomDataEntry
import com.example.mk.wsahomeproject.viewmodels.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var anyChartView : AnyChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpdateChartBtn()

    }

    private fun setUpdateChartBtn() {
           findViewById<Button>(R.id.btn_update_chart).setOnClickListener(View.OnClickListener {
               getDataForChart()
           })
    }

    override fun onStart() {
        super.onStart()
        getDataForChart()
    }

    private fun getDataForChart() {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = android.view.View.VISIBLE

        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getData()

        mainViewModel.responseData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                progressBar.visibility = android.view.View.GONE
                findViewById<AnyChartView>(R.id.chart).visibility = android.view.View.VISIBLE
                findViewById<RelativeLayout>(R.id.onErrorUI).visibility = android.view.View.GONE
                makeChart(it)
                Log.d("ViewmodelResponse", "onSuccess: " + it)
            } else {
                progressBar.visibility = android.view.View.GONE
                findViewById<AnyChartView>(R.id.chart).visibility = android.view.View.GONE
                findViewById<RelativeLayout>(R.id.onErrorUI).visibility = android.view.View.VISIBLE
                Log.d("Viewmodelresponse", "onCreate: " + it)
            }
        })

        mainViewModel.errorResponse.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                progressBar.visibility = android.view.View.GONE
                findViewById<AnyChartView>(R.id.chart).visibility = android.view.View.GONE
                findViewById<RelativeLayout>(R.id.onErrorUI).visibility = android.view.View.VISIBLE
                Log.d("ViewmodelResponse", "onError: " + it)
            }  else {
                progressBar.visibility = android.view.View.GONE
                findViewById<AnyChartView>(R.id.chart).visibility = android.view.View.GONE
                findViewById<RelativeLayout>(R.id.onErrorUI).visibility = android.view.View.VISIBLE
                Log.d("Viewmodelresponse", "onError: " + it)
            }
        })
    }


    private fun makeChart(it: BlockChainResponse) {

        anyChartView = findViewById(R.id.chart)
        val cartesian: Cartesian = AnyChart.line()

        cartesian.animation(true)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true)
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("Bitcoin Market Price (USD)")
        cartesian.yAxis(0).enabled(false)
        cartesian.xAxis(0).enabled(false)

        val seriesData: MutableList<DataEntry> = ArrayList()

        for (i in 0 until it.values!!.size) {
            Log.d("makeChart", "onResponse: date " + it.values!![i].x!!)
            Log.d("makeChart", "onResponse: price " + it.values!![i].y)
            val date =  Date( it.values!![i].x!!*1000).toString()

            seriesData.add(
                CustomDataEntry(
                    date,
                    it.values!![i].y
                )
            )
        }

        Log.d("datasize", "onResponse: seriesData " + seriesData.size)

        val set = Set.instantiate()
        set.data(seriesData)
        val seriesMapping: Mapping = set.mapAs("{value: 'value' }")

        val series: Line = cartesian.line(seriesMapping)
        series.name("Bitcoin")

        series.hovered().markers().enabled(true)
        series.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)

        series.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)


//        cartesian.legend().enabled(true)
//        cartesian.legend().fontSize(11.0)
//        cartesian.legend().padding(0.0)

        anyChartView.setChart(cartesian)

    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String? {

        val formatter = SimpleDateFormat(dateFormat)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

}